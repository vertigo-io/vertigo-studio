package io.vertigo.studio.shell.labs;

import java.util.Scanner;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Greets the user.")
public class HelloCommand implements Runnable {

	@Parameter(names = { "--name", "-n" }, description = "Nom de la personne")
	private String name;

	public void run() {
		if (name == null || name.isEmpty()) {
			System.out.print("Quel est votre nom ? ");
			try (Scanner scanner = new Scanner(System.in)) {
				name = scanner.nextLine();
			}
		}
		System.out.println("Bonjour, " + name + " !");
	}
}
