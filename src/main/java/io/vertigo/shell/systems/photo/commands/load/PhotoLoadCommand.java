package io.vertigo.shell.systems.photo.commands.load;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shell.systems.photo.PhotoContext;
import io.vertigo.shell.systems.photo.PhotoExifInfo;
import io.vertigo.shell.systems.photo.PhotoInfo;
import io.vertigo.shell.systems.photo.PhotoType;
import io.vertigo.shell.systems.photo.PhotoVar;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.live.progressbar.ShinyProgressBar;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load photos from the directory defined in the context.")
public final class PhotoLoadCommand implements ShellCommand {

	@Override
	public void run() {
		final ShinyWriter writer = Shiny.writer();

		final Path rootPath = Path.of(Env.get(PhotoVar.ROOT_PATH));

		if (rootPath == null) {
			writer.println("Error: rootPath is not set. Use 'photo set rootPath <directory>' first.");
			return;
		}

		PhotoContext.clearPhotos();
		writer.println("Phase 1: Scanning directory: " + rootPath);

		final List<Path> photoPaths;
		try (Stream<Path> paths = Files.walk(rootPath)) {
			photoPaths = paths
					.filter(Files::isRegularFile)
					.filter(path -> PhotoType.isPhoto(path.getFileName().toString()))
					.collect(Collectors.toList());
		} catch (final IOException e) {
			writer.println("Error walking directory: " + e.getMessage());
			return;
		}

		writer.println("Found " + photoPaths.size() + " photos.");
		writer.println("Phase 2: Processing photos...");

		int processedCount = 0;
		try (final ShinyProgressBar progressBar = Shiny.progressBar()
				//.title("Processing photos")
				.withTotal(photoPaths.size()).build().start()) {
			for (final Path path : photoPaths) {
				final PhotoInfo photoInfo = extract(writer, path);
				if (photoInfo != null) {
					PhotoContext.addPhoto(photoInfo);
				}
				processedCount++;
				progressBar.liveUpdate(processedCount);
			}
		}
		writer.println("Done.");
	}

	private PhotoInfo extract(final ShinyWriter writer, final Path path) {
		try {
			final long size = Files.size(path);
			final String md5Hash = calculateMd5(path);
			final PhotoExifInfo exifInfo = extractExifInfo(path);
			return new PhotoInfo(path, size, exifInfo, md5Hash);
		} catch (IOException | NoSuchAlgorithmException e) {
			writer.println("\nError processing file " + path + ": " + e.getMessage());
			return null;
		}
	}

	private static PhotoExifInfo extractExifInfo(final Path path) {
		final Map<String, String> tags = new HashMap<>();
		try {
			final Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
			for (final Directory directory : metadata.getDirectories()) {
				for (final Tag tag : directory.getTags()) {
					tags.put(tag.getTagName(), tag.getDescription());
				}
			}
		} catch (final Exception e) {
			// Ignore exceptions
		}
		return new PhotoExifInfo(tags);
	}

	private static String calculateMd5(final Path path) throws IOException, NoSuchAlgorithmException {
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
