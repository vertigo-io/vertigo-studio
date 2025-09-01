package io.vertigo.shell.shiny;

public class ShinyTitleTest {

	public static void main(final String[] args) {
		testTitles();
	}

	private static void testTitles() {
		Shiny.title("Level 1 Title").level(1).print();
		Shiny.title("Level 2 Title").level(2).print();
		Shiny.title("Level 3 Title").level(3).print();
		Shiny.title("Default Level Title").print(); // Default, should be level 1
	}
}
