package io.vertigo.shell.systems.photo.commands.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.photo.PhotoContext;
import io.vertigo.shell.systems.photo.PhotoInfo;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "list", description = "List loaded photos.")
public final class PhotoListCommand implements ShellCommand {

	@Override
	public void run() {
		final List<PhotoInfo> photos = PhotoContext.getPhotos();

		if (photos.isEmpty()) {
			System.out.println("No photos loaded. Use 'load' command first.");
			return;
		}

		final Set<String> duplicateHashes = photos.stream()
				.collect(Collectors.groupingBy(PhotoInfo::md5Hash, Collectors.counting()))
				.entrySet().stream()
				.filter(entry -> entry.getValue() > 1)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());

		final List<String[]> rows = new ArrayList<>();
		for (final PhotoInfo photo : photos) {
			final boolean isDuplicate = duplicateHashes.contains(photo.md5Hash());

			final String[] row = {
					isDuplicate
							? ShinyColors.RED_BRIGHT.fg(photo.path().toString())
							: photo.path().toString(),
					photo.size() + "",
                    photo.exifInfo().tags().getOrDefault("Date/Time", ""),
                    photo.exifInfo().tags().getOrDefault("Image Width", ""),
                    photo.exifInfo().tags().getOrDefault("Image Height", ""),
					photo.md5Hash() + ""
			};
			rows.add(row);
		}

		Shiny.table()
            .title("Photos")
            .noDataFound("No photos found.")
            .header("Path", "Size", "Date/Time", "Width", "Height", "MD5 Hash")
            .rows(rows)
            .print();
	}
}
