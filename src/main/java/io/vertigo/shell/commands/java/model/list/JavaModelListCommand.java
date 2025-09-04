package io.vertigo.shell.commands.java.model.list;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.java.model.JavaContext;
import io.vertigo.shell.commands.java.model.JavaModel.JavaClass;
import io.vertigo.shell.commands.java.model.JavaModel.JavaPackage;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.data.tree.ShinyTree;
import io.vertigo.shell.shiny.data.tree.ShinyTreeNode;
import picocli.CommandLine.Command;

@Command(name = "list", description = "Display elements of the model")
public final class JavaModelListCommand implements ShellCommand {
	//	@Option(names = { "--all", "-a" }, description = "select all objects")
	//	private boolean all;
	//
	//	@Option(names = { "--tableName", "-T" }, description = "select a table")
	//	private String tableName;
	//
	//	@Option(names = { "--tables", "-t" }, description = "select all tables")
	//	private boolean tables;

	@Override
	public void run() {
		//		if (all) {
		listAll();
		//		}
		//		if (tableName != null) {
		//			describeTable();
		//		}
		//		if (tables) {
		//			listTables();
		//		}
	}

	private void listAll() {
		//To avoid java keyword
		//Class => clazz
		//package => jpackage
		final ShinyTree tree = Shiny.tree("model");
		for (final JavaPackage jpackage : JavaContext.model().packages()) {
			parse(jpackage, tree.getRoot());
		}
		tree.print();
	}

	private void parse(JavaPackage jpackage, ShinyTreeNode parent) {
		final ShinyTreeNode packageNode = parent.addChild("package : " + jpackage.name());
		final ShinyTreeNode ClassesNode = packageNode.addChild("classes (" + jpackage.classes().size() + ")");
		for (final JavaClass clazz : jpackage.classes()) {
			/*final ShinyTreeNode classNode = */ClassesNode.addChild(clazz.name());
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
