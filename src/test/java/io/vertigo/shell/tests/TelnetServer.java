package io.vertigo.shell.tests;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import io.vertigo.shell.Shell;

public final class TelnetServer {
	private static final int PORT = 23; // Port Telnet par défaut

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Serveur Telnet démarré sur le port " + PORT);

			while (true) {
				// Attendre une connexion client
				try (Socket clientSocket = serverSocket.accept()) {
					System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress().getHostAddress());

					// Rediriger System.out vers le client Telnet
					PrintStream telnetOut = new PrintStream(clientSocket.getOutputStream(), true);
					System.setOut(telnetOut);

					// Rediriger System.in depuis le client Telnet
					InputStream telnetIn = clientSocket.getInputStream();
					System.setIn(telnetIn);

					// Envoyer un message de bienvenue
					System.out.println("Bienvenue sur le serveur Telnet simple !");
					System.out.println("Tapez 'quit' pour quitter.");

					// Envoyer un message de bienvenue via System.out
					System.out.println("Bienvenue sur le serveur Telnet !");
					System.out.println("Tapez vos messages, 'quit' pour quitter.");

					// Utiliser un BufferedReader pour lire depuis System.in
					//					final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					//					String inputLine;

					new Shell().run();
					//					// Boucle pour lire les entrées et écrire les sorties
					//					while ((inputLine = reader.readLine()) != null) {
					//						System.out.println("Echo : " + inputLine); // Écho via System.out
					//						if ("quit".equalsIgnoreCase(inputLine.trim())) {
					//							System.out.println("Connexion terminée.");
					//							break;
					//						}
					//					}

					// Restaurer System.in et System.out avant de fermer
					System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
					System.setIn(new FileInputStream(FileDescriptor.in));
				} catch (IOException e) {
					System.err.println("Erreur avec le client : " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur du serveur : " + e.getMessage());
		}
	}
}
