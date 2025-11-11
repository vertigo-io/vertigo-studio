package io.vertigo.shell.systems.java.commands.show;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.java.JavaContext;
import io.vertigo.shell.systems.java.JavaModel.JavaClass;
import io.vertigo.shell.systems.java.JavaModel.JavaImport;
import io.vertigo.shell.systems.java.JavaModel.JavaPackage;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyTreeBuilder;
import picocli.CommandLine.Command;

@Command(name = "model", description = "Show the java model")
public final class JavaShowModelCommand implements ShellCommand {
	@Override
	public ShinyModel build() {
		return listAll();
	}

	private ShinyModel listAll() {
		//To avoid java keyword
		//Class => clazz
		//package => jpackage
		final ShinyTreeBuilder tree = Shiny.tree().withLabel("model");
		for (final JavaPackage jpackage : JavaContext.model().packages()) {
			parse(jpackage, tree);
		}
		return tree.build();
	}

	private void parse(JavaPackage jpackage, ShinyTreeBuilder parent) {
		final var packageTreeBuilder = parent.addTree("package : " + jpackage.name());
		final var classesTreeBuilder = packageTreeBuilder.addTree("classes (" + jpackage.classes().size() + ")");
		for (final JavaClass clazz : jpackage.classes()) {
			final var classTreeBuilder = classesTreeBuilder.addTree(clazz.name());
			classTreeBuilder.addLeaf(clazz.imports().toString());
			for (JavaImport javaImport : clazz.imports()) {
				if (!javaImport.name().startsWith("java.")) {
					classTreeBuilder.addLeaf(javaImport.name());
				}
			}
			//				final ShinyTreeNode columnsNode = tableNode.addChild("columns");
			//				for (final JdbcColumn column : table.columns()) {
			//
			//					String info = column.name() + " " + ShinyColors.GREEN.bright() + column.typeName() + ShinyColors.RESET;
			//
			//					if (column.isPrimaryKey()) {
			//						info = ShinyColors.UNDERLINE + info + ShinyColors.RESET;
			//					}
			//					columnsNode.addChild(info);
			//				}
		}
		//		//recursively
		//		for (final JavaPackage subPackage : jpackage.packages()) {
		//			parse(subPackage, packageNode);
		//		}

	}
}
