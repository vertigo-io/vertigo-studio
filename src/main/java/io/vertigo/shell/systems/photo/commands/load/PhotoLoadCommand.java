package io.vertigo.shell.systems.photo.commands.load;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.photo.PhotoContext;
import io.vertigo.shell.systems.photo.PhotoInfo;
import io.vertigo.shell.systems.photo.PhotoType;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBar;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load photos from the directory defined in the context.")
public final class PhotoLoadCommand implements ShellCommand {

	@Override
	public void run() {
		final Path rootPath = PhotoContext.getRootPath();

		if (rootPath == null) {
			System.out.println("Error: rootPath is not set. Use 'photo set rootPath <directory>' first.");
			return;
		}

		PhotoContext.clearPhotos();
		System.out.println("Phase 1: Scanning directory: " + rootPath);

		final List<Path> photoPaths;
		try (Stream<Path> paths = Files.walk(rootPath)) {
			photoPaths = paths
					.filter(Files::isRegularFile)
					.filter(path -> PhotoType.isPhoto(path.getFileName().toString()))
					.collect(Collectors.toList());
		} catch (final IOException e) {
			System.out.println("Error walking directory: " + e.getMessage());
			return;
		}

		System.out.println("Found " + photoPaths.size() + " photos.");
		System.out.println("Phase 2: Processing photos...");

		int processedCount = 0;
		try (final ShinyProgressBar progressBar = Shiny.progressBar()
				//.title("Processing photos")
				.total(photoPaths.size()).start()) {
			for (final Path path : photoPaths) {
				final PhotoInfo photoInfo = extract(path);
				if (photoInfo != null) {
					PhotoContext.addPhoto(photoInfo);
				}
				processedCount++;
				progressBar.liveUpdate(processedCount);
			}
		}
		System.out.println("Done.");
	}

	private PhotoInfo extract(final Path path) {
		try {
			final long size = Files.size(path);
			final String md5Hash = calculateMd5(path);
			return new PhotoInfo(path, size, Collections.emptyMap(), md5Hash);
		} catch (IOException | NoSuchAlgorithmException e) {
			System.out.println("\nError processing file " + path + ": " + e.getMessage());
			return null;
		}
	}

	private String calculateMd5(final Path path) throws IOException, NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("MD5");
		try (InputStream is = Files.newInputStream(path);
				DigestInputStream dis = new DigestInputStream(is, md)) {
			// read the file to update the digest
			while (dis.read() != -1)
				;
		}
		final byte[] digest = md.digest();
		return Base64.getEncoder().encodeToString(digest);
	}
}
