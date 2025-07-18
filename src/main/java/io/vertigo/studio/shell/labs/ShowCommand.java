package io.vertigo.studio.shell;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.definition.Definition;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.tools.VertigoStudioMda;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Parameters(commandNames = "show", commandDescription = "Shows detailed information for a specific definition.")
public final class ShowCommand implements Runnable {

    @Parameter(description = "The name of the definition to show.", required = true)
    private List<String> definitionName;

    @Parameter(names = "--config", description = "The configuration file (e.g., studio-config.yaml)", required = true)
    private String configFile;

    @Override
    public void run() {
        try {
            final String configUrl = Paths.get(configFile).toUri().toURL().toExternalForm();
            final Notebook notebook = VertigoStudioMda.createNotebook(configUrl);
            final String defName = definitionName.get(0);

            final Definition definition = notebook.find(defName, Definition.class)
                    .orElseThrow(() -> new IllegalArgumentException("Definition '" + defName + "' not found."));

            printDetails(definition, notebook);

        } catch (final MalformedURLException e) {
            throw WrappedException.wrap(e);
        }
    }

    private void printDetails(final Definition definition, final Notebook notebook) {
        System.out.println("Definition: " + definition.getName());
        System.out.println("---------------------");
        System.out.println("  Type: " + definition.getMetaDefinition().getShortName());
        System.out.println("  Package: " + definition.getPackageName());

        if (definition instanceof DtSketch) {
            final DtSketch dt = (DtSketch) definition;
            System.out.println("  Stereotype: " + dt.getStereotype());
            System.out.println("  Fields:");
            dt.getFields().forEach(f -> System.out.printf("    - %s: %s %s%n", f.getName(), f.getSmartTypeDefinition().getName(), f.isId() ? "(ID)" : ""));
        }

        // Find usages
        final List<Definition> usages = notebook.getAll(Definition.class).stream()
                .filter(d -> d.getReferredDefinitions().contains(definition))
                .collect(Collectors.toList());

        if (!usages.isEmpty()) {
            System.out.println("  Used by:");
            usages.forEach(u -> System.out.printf("    - [%s] %s%n", u.getMetaDefinition().getShortName(), u.getName()));
        }
    }
}
