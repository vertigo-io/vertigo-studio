package io.vertigo.shell.systems.photo.commands.analyze;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.photo.PhotoInfo;
import io.vertigo.shell.systems.photo.PhotoContext;
import picocli.CommandLine.Command;

@Command(name = "analyze", description = "Analyze photos and find duplicates.")
public final class PhotoAnalyzeCommand implements ShellCommand {

    @Override
    public void run() {
        final List<PhotoInfo> photos = PhotoContext.getPhotos();

        if (photos.isEmpty()) {
            System.out.println("No photos loaded. Use 'load' command first.");
            return;
        }

        final Map<String, List<PhotoInfo>> duplicates = photos.stream()
                .collect(Collectors.groupingBy(PhotoInfo::md5Hash))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (duplicates.isEmpty()) {
            System.out.println("No duplicates found.");
        } else {
            System.out.println("Found " + duplicates.size() + " sets of duplicates:");
            duplicates.forEach((hash, duplicatePhotos) -> {
                System.out.println("Hash: " + hash);
                duplicatePhotos.forEach(photo -> System.out.println("  - " + photo.path()));
            });
        }
    }
}
