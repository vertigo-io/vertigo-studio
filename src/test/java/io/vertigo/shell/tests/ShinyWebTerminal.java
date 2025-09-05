package io.vertigo.shell.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Month;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.data.table.ShinyBorder;
import io.vertigo.shiny.data.tree.ShinyIcon;
import io.vertigo.shiny.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.text.figlet.ShinyFigletFonts;

public class ShinyWebTerminal extends WebSocketServer {

	// Contenu HTML intégré directement dans le code
	private static final String HTML_CONTENT = """
			<!DOCTYPE html>
			<html lang="fr">
			<head>
			    <meta charset="UTF-8">
			    <title>Terminal Web</title>
			    <link href="https://unpkg.com/xterm@5.3.0/css/xterm.css" rel="stylesheet" />
			    <style>
			        body { margin: 0; padding: 10px; background-color: #000; }
			        #terminal { width: 100%; height: 600px; }
			    </style>
			</head>
			<body>
			    <div id="terminal"></div>
			    <script src="https://unpkg.com/xterm@5.3.0/lib/xterm.js"></script>
			    <script>
			        // Initialiser xterm.js
			        const term = new Terminal({
			            cursorBlink: true,
			            theme: { background: '#000', foreground: '#fff' }
			        });
			        term.open(document.getElementById('terminal'));

			        // Connexion WebSocket
			        const ws = new WebSocket('ws://localhost:8080');
			        ws.onopen = () => {
			            term.write('Connexion établie...\\r\\n');
			        };
			        ws.onmessage = (event) => {
			            term.write(event.data + '\\r\\n');
			        };
			        ws.onclose = () => {
			            term.write('\\r\\n\\u001B[31mDéconnecté du serveur.\\u001B[0m\\r\\n');
			        };
			        ws.onerror = (error) => {
			            term.write('\\r\\n\\u001B[31mErreur WebSocket: ' + error + '\\u001B[0m\\r\\n');
			        };

			        // Envoyer les entrées de l'utilisateur au serveur
			        let input = '';
			        term.onKey(({ key, domEvent }) => {
			            if (domEvent.key === 'Enter') {
			                ws.send(input);
			                term.write('\\r\\n');
			                input = '';
			            } else if (domEvent.key === 'Backspace') {
			                if (input.length > 0) {
			                    input = input.slice(0, -1);
			                    term.write('\\b \\b');
			                }
			            } else {
			                input += key;
			                term.write(key);
			            }
			        });
			    </script>
			</body>
			</html>
			""";

	public ShinyWebTerminal(int wsPort) {
		super(new InetSocketAddress(wsPort));
	}

	public static void main(String[] args) throws IOException {
		int wsPort = 8080; // Port WebSocket
		int httpPort = 8000; // Port HTTP pour servir le contenu HTML

		// Démarrer le serveur WebSocket
		ShinyWebTerminal wsServer = new ShinyWebTerminal(wsPort);
		wsServer.setReuseAddr(true);
		wsServer.start();
		System.out.println("Serveur WebSocket démarré sur le port " + wsPort);

		// Démarrer un serveur HTTP simple pour servir le contenu HTML
		try (ServerSocket httpServer = new ServerSocket(httpPort)) {
			System.out.println("Serveur HTTP démarré sur le port " + httpPort);
			while (true) {
				try (Socket client = httpServer.accept()) {
					handleHttpRequest(client);
				} catch (Exception e) {
					System.err.println("Erreur HTTP : " + e.getMessage());
				}
			}
		}
	}

	private static void handleHttpRequest(Socket client) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		OutputStream out = client.getOutputStream();

		// Lire la requête HTTP
		String line = in.readLine();
		if (line != null && line.startsWith("GET / ")) {
			// Servir le contenu HTML intégré
			String response = "HTTP/1.1 200 OK\r\n" +
					"Content-Type: text/html\r\n" +
					"Content-Length: " + HTML_CONTENT.length() + "\r\n" +
					"\r\n" +
					HTML_CONTENT;
			out.write(response.getBytes("UTF-8"));
		} else {
			// Réponse 404 pour les autres requêtes
			String response = "HTTP/1.1 404 Not Found\r\n" +
					"Content-Length: 0\r\n" +
					"\r\n";
			out.write(response.getBytes("UTF-8"));
		}
		out.flush();
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		conn.send("\u001B[32mBienvenue sur le serveur WebSocket avec Shiny !\u001B[0m");
		conn.send("Tapez 'exit' pour quitter.");
		conn.send("Commandes disponibles: 'f' pour figlet, 't' pour table, 'help' pour l'aide");
		conn.send("\u001B[34mPrompt > \u001B[0m");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Client déconnecté : " + conn.getRemoteSocketAddress());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		String line = message.trim();
		StringWriter stringWriter = new StringWriter();
		PrintWriter out = new PrintWriter(stringWriter, true);
		Shiny.printWriter(out);

		if ("exit".equalsIgnoreCase(line)) {
			out.println("\u001B[31mDéconnexion...\u001B[0m");
			conn.send(stringWriter.toString());
			conn.close();
			return;
		}

		switch (line) {
			case "f":
				Shiny.figlet()
						.text("Hello")
						.font(ShinyFigletFonts.BIG) // Use BIG font
						.color(ShinyColors.BLUE)
						.print();
				break;
			case "t":
				System.out.println(ShinyColors.BLUE_BRIGHT + "--- European Union Countries - Population ---" + ShinyColors.RESET);
				final List<String[]> euCountries = List.of(
						new String[] { "Austria", "9000000" },
						new String[] { "Belgium", "11700000" },
						new String[] { "Bulgaria", "6400000" },
						new String[] { "Croatia", "3900000" },
						new String[] { "Cyprus", "920000" },
						new String[] { "Czech Republic", "10900000" },
						new String[] { "Denmark", "5900000" },
						new String[] { "Estonia", "1300000" },
						new String[] { "Finland", "5600000" },
						new String[] { "France", "68000000" },
						new String[] { "Germany", "84000000" },
						new String[] { "Greece", "10200000" },
						new String[] { "Hungary", "9600000" },
						new String[] { "Ireland", "5300000" },
						new String[] { "Italy", "59000000" },
						new String[] { "Latvia", "1800000" },
						new String[] { "Lithuania", "2800000" },
						new String[] { "Luxembourg", "660000" },
						new String[] { "Malta", "520000" },
						new String[] { "Netherlands", "17800000" },
						new String[] { "Poland", "37600000" },
						new String[] { "Portugal", "10300000" },
						new String[] { "Romania", "19000000" },
						new String[] { "Slovakia", "5400000" },
						new String[] { "Slovenia", "2100000" },
						new String[] { "Spain", "48000000" },
						new String[] { "Sweden", "10600000" });

				Shiny.table()
						.title("European Union Countries - Population")
						.header("Country", "Population")
						.rows(euCountries)
						.print();
				System.out.println();
				break;
			case "p":
				out.println();
				testFastProgressBar();
				break;
			case "s":
				System.out.println(ShinyColors.BLUE_BRIGHT + "--- Stock Price Sparkline ---" + ShinyColors.RESET);
				Shiny.sparkline()
						.title("Stock Price")
						.data(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
						.color(ShinyColors.BLUE)
						.print();
				System.out.println();
				break;
			case "c":
				System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with ASCII Border (February 2025) ---" + ShinyColors.RESET);
				Shiny.calendar()
						.year(2025)
						.month(Month.FEBRUARY.getValue())
						.border(ShinyBorder.Ascii) // Set ASCII border
						.print();
				System.out.println();
				break;
			case "tr":
				out.println();
				testIconShowcase();
				break;
			//			case "g":
			//				out.println();
			//				ShinyGaugeTest.main(null);
			//				break;
			case "help":
			case "h":
				out.println("\u001B[36mCommandes disponibles:\u001B[0m");
				out.println("  f     - Afficher un texte Figlet");
				out.println("  t     - Afficher un tableau");
				out.println("  help  - Afficher cette aide");
				out.println("  exit  - Quitter");
				break;
			default:
				if (!line.isEmpty()) {
					out.println("\u001B[33mCommande inconnue: '" + line + "'. Tapez 'help' pour voir les commandes disponibles.\u001B[0m");
				}
				break;
		}

		conn.send(stringWriter.toString());
		conn.send("\u001B[34mPrompt > \u001B[0m");
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("Erreur WebSocket : " + ex.getMessage());
		if (conn != null) {
			conn.send("\u001B[31mErreur serveur : " + ex.getMessage() + "\u001B[0m");
		}
	}

	@Override
	public void onStart() {
		System.out.println("Serveur WebSocket démarré avec succès");
	}

	private static void testFastProgressBar() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Fast Progress Bar ---" + ShinyColors.RESET);
		final ShinyProgressBar progressBar = Shiny.progressBar().total(500);
		System.out.println();
		for (int i = 0; i < 500; i++) {
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				Thread.sleep(5); // Faster simulation
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		progressBar.finish();
		System.out.println();
	}

	private static void testIconShowcase() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Icon Showcase ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Icons");
		tree.getRoot()
				.addChild("Status Icons", ShinyIcon.FOLDER_OPEN)
				.addChild("Success", ShinyIcon.SUCCESS).up()
				.addChild("Error", ShinyIcon.ERROR).up()
				.addChild("Warning", ShinyIcon.WARNING).up()
				.addChild("Info", ShinyIcon.INFO).up()
				.addChild("Question", ShinyIcon.QUESTION).up();
		tree.getRoot()
				.addChild("Time & Arrows", ShinyIcon.FOLDER_OPEN)
				.addChild("Clock", ShinyIcon.CLOCK).up()
				.addChild("Up", ShinyIcon.ARROW_UP).up()
				.addChild("Down", ShinyIcon.ARROW_DOWN).up()
				.addChild("Left", ShinyIcon.ARROW_LEFT).up()
				.addChild("Right", ShinyIcon.ARROW_RIGHT).up();
		tree.getRoot()
				.addChild("Misc Icons", ShinyIcon.FOLDER_OPEN)
				.addChild("Star", ShinyIcon.STAR).up()
				.addChild("Heart", ShinyIcon.HEART).up()
				.addChild("Smiley", ShinyIcon.SMILEY).up();
		tree.print();
		System.out.println();
	}

}
