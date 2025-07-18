package io.vertigo.studio.shell;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.definition.Definition;
import io.vertigo.studio.tools.VertigoStudioMda;

import java.net.MalformedURLException;
import java.nio.file.Paths;

@Parameters(commandNames = "list-modules", commandDescription = "Lists all modules (packages) found in the model.")
public final class ListModulesCommand implements Runnable {

    @Parameter(description = "The configuration file (e.g., studio-config.yaml)", required = true)
    private String configFile;

    @Override
    public void run() {
        try {
            final String configUrl = Paths.get(configFile).toUri().toURL().toExternalForm();
            final Notebook notebook = VertigoStudioMda.createNotebook(configUrl);

            System.out.println("Found modules:");
            notebook.getAll(Definition.class)
                    .stream()
                    .map(Definition::getPackageName)
                    .distinct()
                    .sorted()
                    .forEach(System.out::println);

        } catch (final MalformedURLException e) {
            throw WrappedException.wrap(e);
        }
    }
}
