/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.core.plugins.resource.url.URLResourceResolverPlugin;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.mda.MdaResult;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.NotebookConfig;
import io.vertigo.studio.source.SourceManager;

/**
 * Génération des fichiers Java et SQL à patrir de fichiers template freemarker.
 *
 * @author mlaroche, pchretien
 */
public final class VertigoStudioMda {

	private static final Logger STUDIO_LOGGER = LogManager.getLogger(VertigoStudioMda.class);

	private enum StudioTarget {
		clean, generate, watch, clean_watch;
	}

	public static void main(final String[] args) {
		Assertion.check()
				.isTrue(args.length == 2, "expected the target (clean, generate, watch or clean_watch) and the studio json config");
		//--
		final StudioTarget studioTarget = StudioTarget.valueOf(args[0]);
		final String studioProjectConfigJson = args[1];
		doMain(studioTarget, studioProjectConfigJson);
	}

	private static void doMain(final StudioTarget studioTarget, final String studioProjectConfigJson) {
		final NotebookConfig notebookConfig = loadStudioProjectConfig(studioProjectConfigJson);
		//---
		switch (studioTarget) {
			case clean:
				clean(notebookConfig);
				break;
			case generate:
				generate(notebookConfig);
				break;
			case watch:
				watch(notebookConfig, false);
				break;
			case clean_watch:
				watch(notebookConfig, true);
				break;
			default:
				break;
		}

	}

	private static void clean(final NotebookConfig notebookConfig) {
		try (final AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);
			//-----
			final MdaConfig mdaConfig = notebookConfig.getMdaConfig();
			mdaManager.clean(mdaConfig);
		}
	}

	private static void generate(final NotebookConfig notebookConfig) {
		try (final AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final SourceManager sourceManager = studioApp.getComponentSpace().resolve(SourceManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);
			//-----
			final MdaConfig mdaConfig = notebookConfig.getMdaConfig();
			mdaManager.clean(mdaConfig);
			final Notebook notebook = sourceManager.read(notebookConfig.getMetamodelResources());
			final MdaResult mdaResult = mdaManager.generate(notebook, mdaConfig);
			mdaResult.displayResultMessage(System.out);
		}
	}

	private static void watch(final NotebookConfig notebookConfig, final boolean withClean) {
		try (final AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final SourceManager sourceManager = studioApp.getComponentSpace().resolve(SourceManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);
			final ResourceManager resourceManager = studioApp.getComponentSpace().resolve(ResourceManager.class);
			//-----
			final MdaConfig mdaConfig = notebookConfig.getMdaConfig();
			final List<Path> pathsToWatch = listPathToWatch(notebookConfig, resourceManager);
			STUDIO_LOGGER.info("Monitored file for generation are {}", pathsToWatch);

			final Set<Path> directoriesToWatch = pathsToWatch
					.stream()
					.map(Path::getParent)
					.collect(Collectors.toSet());

			final Debouncer debouncer = new Debouncer();

			try (final FileSystem fs = FileSystems.getDefault()) {
				try (final WatchService watcher = fs.newWatchService()) {
					try {
						directoriesToWatch.forEach(directory -> {
							try {
								directory.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
							} catch (final IOException e) {
								throw WrappedException.wrap(e);
							}
						});
						while (true) {
							final WatchKey key = watcher.take(); // waits
							for (final WatchEvent<?> event : key.pollEvents()) {
								if (pathsToWatch.contains(Path.of(key.watchable().toString(), event.context().toString()))) {
									debouncer.debounce(() -> {
										STUDIO_LOGGER.info("Regeneration started");
										try {
											if (withClean) {
												STUDIO_LOGGER.info("Start cleaning");
												mdaManager.clean(mdaConfig);
												STUDIO_LOGGER.info("Done cleaning");
											}
											final Notebook notebook = sourceManager.read(notebookConfig.getMetamodelResources());
											final MdaResult mdaResult = mdaManager.generate(notebook, mdaConfig);
											STUDIO_LOGGER.info("Regeneration completed. {} created files, {} updated files, {} identical files and {} issues in {} ms",
													mdaResult.getCreatedFiles(), mdaResult.getUpdatedFiles(), mdaResult.getIdenticalFiles(), mdaResult.getErrorFiles(), mdaResult.getDurationMillis());
										} catch (final Exception e) {
											STUDIO_LOGGER.error("Error regenerating : ", e);
										}

									}, 1);
								}
							}
							key.reset();
						}
					} catch (final InterruptedException e) {
						throw WrappedException.wrap(e);
					}

				}
			} catch (final IOException e) {
				throw WrappedException.wrap(e);
			}

		}
	}

	private static List<Path> listPathToWatch(final NotebookConfig notebookConfig, final ResourceManager resourceManager) {
		final List<Path> pathsToWatch = notebookConfig.getMetamodelResources()
				.stream()
				.map(source -> resourceManager.resolve(source.getPath()))
				.filter(url -> {
					try {
						return "file".equals(url.toURI().getScheme());
					} catch (final URISyntaxException e) {
						throw WrappedException.wrap(e);
					}
				})
				.map(url -> {
					try {
						return Path.of(url.toURI());
					} catch (final URISyntaxException e) {
						throw WrappedException.wrap(e);
					}
				})
				.flatMap(sourcePath -> {
					if (sourcePath.toString().endsWith(".kpr")) {
						return Stream.concat(Stream.of(sourcePath), getKspFiles(sourcePath).stream());
					}
					return Stream.of(sourcePath);
				})
				.collect(Collectors.toList());
		return pathsToWatch;
	}

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(URLResourceResolverPlugin.class)
						.addPlugin(LocalResourceResolverPlugin.class)
						.build())
				.addModule(new CommonsFeatures().build())
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withMda()
						.withVertigoMda()
						.build())
				.build();

	}

	private static NotebookConfig loadStudioProjectConfig(final String configFileUrl) {
		try {
			return StudioConfigYamlParser.parseYaml(new URL(configFileUrl));
		} catch (IOException | URISyntaxException e) {
			throw WrappedException.wrap(e);
		}
	}

	private static List<Path> getKspFiles(final Path kprUrl) {
		try {
			return doGetKspFiles(kprUrl);
		} catch (final Exception e) {
			throw WrappedException.wrap(e, "Echec de lecture du fichier KPR {0}", kprUrl);
		}
	}

	private static List<Path> doGetKspFiles(final Path krpUrl) throws Exception {
		return Files.readAllLines(krpUrl)
				.stream()
				.flatMap(fileName -> {
					if (fileName.length() > 0) {
						// voir http://commons.apache.org/vfs/filesystems.html
						// Protocol : vfszip pour jboss par exemple
						final Path url = Path.of(krpUrl.getParent().toString(), fileName);
						if (fileName.endsWith(".kpr")) {
							// kpr
							return getKspFiles(url).stream();
						} else if (fileName.endsWith(".ksp")) {
							// ksp
							return Stream.of(url);
						} else {
							throw new VSystemException("Type de fichier inconnu : {0}", fileName);
						}
					}
					return Stream.empty();
				})
				.collect(Collectors.toList());
	}

	static class Debouncer {

		private final ScheduledExecutorService scheduledExecutorService;
		private Future previousFuture;

		public Debouncer() {
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			previousFuture = null;
		}

		public synchronized void debounce(final Runnable runnable, final int delay) {
			if (previousFuture != null) {
				previousFuture.cancel(false);
			}
			previousFuture = scheduledExecutorService.schedule(runnable, delay, TimeUnit.SECONDS);
		}
	}

}
