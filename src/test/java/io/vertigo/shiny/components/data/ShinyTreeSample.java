package io.vertigo.shiny.components.data;

import io.vertigo.shiny.models.data.tree.ShinyTree;
import io.vertigo.shiny.models.data.tree.ShinyTreeBuilder;

public class ShinyTreeSample {
	public static void main(String[] args) {
		print(0, sample1());
		System.out.println("---");
		print(0, sample2());
		System.out.println("---");
		print(0, sample3());
	}

	private static ShinyTree sample1() {
		return new ShinyTreeBuilder()
				.withLabel("this is the root / sample 1")
				.addLeaf("1")
				.addLeaf("2")
				.addTree("3")
				.addLeaf("3.a")
				.addLeaf("3.b")
				.addTree("3.c")
				.addLeaf("3.c.1")
				.addLeaf("3.c.2")
				.up()
				.addTree("3.d")
				.addLeaf("3.d.1")
				.addLeaf("3.d.2")
				.addLeaf("3.d.3")
				.up()
				.addLeaf("3.e")
				.up()
				.addLeaf("4")
				.build();
	}

	private static ShinyTree sample2() {
		return new ShinyTreeBuilder()
				.withLabel("this is the root / sample 2")
				.addLeaf("1")
				.addLeaf("2")
				.addTree("3")
				.addLeaf("3.a")
				.addLeaf("3.b")
				.addTree("3.c")
				.addLeaf("3.c.1")
				.addLeaf("3.c.2")
				.up()
				.addTree("3.d")
				.addLeaf("3.d.1")
				.addLeaf("3.d.2")
				.addLeaf("3.d.3")
				.root()
				.build();
	}

	private static ShinyTree sample3() {
		return new ShinyTreeBuilder()
				.withLabel("this is the root / sample 3")
				.addLeaf("1")
				.addLeaf("2")
				.addTree(
						new ShinyTreeBuilder().withLabel("3")
								.addLeaf("3.a")
								.addLeaf("3.b")
								.addTree("3.c")
								.addLeaf("3.c.1")
								.addLeaf("3.c.2")
								.root()
								.build())
				.addTree("3.d")
				.addLeaf("3.d.1")
				.addLeaf("3.d.2")
				.addLeaf("3.d.3")
				.root()
				.build();
	}

	private static void print(int idx, ShinyTree tree) {
		if (tree.isLeaf()) {
			System.out.println(" ".repeat(idx) + " - " + tree.label());
		} else {
			System.out.println(" ".repeat(idx) + " + " + tree.label());
			var subidx = idx + 1;
			for (var t : tree.children()) {
				print(subidx, t);
			}
		}
	}

}
