package io.vertigo.shell.systems.photo.commands;

import picocli.CommandLine.Command;
import io.vertigo.shell.systems.photo.commands.analyze.PhotoAnalyzeCommand;
import io.vertigo.shell.systems.photo.commands.list.PhotoListCommand;
import io.vertigo.shell.systems.photo.commands.load.PhotoLoadCommand;
import io.vertigo.shell.systems.photo.commands.set.PhotoSetCommand;

@Command(name = "photo", mixinStandardHelpOptions = true, description = "Photo commands", subcommands = {
        PhotoSetCommand.class,
        PhotoLoadCommand.class,
        PhotoAnalyzeCommand.class,
        PhotoListCommand.class
})
public class PhotoCommands {
    // This is a container for the photo commands
}
