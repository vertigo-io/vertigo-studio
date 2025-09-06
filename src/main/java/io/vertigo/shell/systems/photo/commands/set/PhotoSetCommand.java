package io.vertigo.shell.systems.photo.commands.set;

import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.photo.PhotoContext;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "set", description = "Set a value in the photo context.")
public final class PhotoSetCommand implements ShellCommand {

    @Parameters(index = "0", description = "The variable to set (e.g., rootPath).")
    private String variable;

    @Parameters(index = "1", description = "The value to set.")
    private String value;

    @Override
    public void run() {
        if ("rootPath".equalsIgnoreCase(variable)) {
            final Path rootPath = Path.of(value);
            if (!Files.isDirectory(rootPath)) {
                System.out.println("Error: " + rootPath + " is not a directory.");
                return;
            }
            PhotoContext.setRootPath(rootPath);
            System.out.println("Photo rootPath set to: " + rootPath);
        } else {
            System.out.println("Unknown variable: " + variable);
        }
    }
}
