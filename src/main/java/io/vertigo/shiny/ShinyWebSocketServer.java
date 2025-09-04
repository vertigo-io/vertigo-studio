package io.vertigo.shiny;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

class P5JsWebSocketServer extends WebSocketServer {
	private static final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());

	public P5JsWebSocketServer(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connections.add(conn);
		System.out.println("Client connecté: " + conn.getRemoteSocketAddress());
		// Envoyer une commande initiale pour piloter l'affichage p5.js
		conn.send("""
				{"action": "drawCircle", "x": 200, "y": 200, "size": 50}
				""");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		connections.remove(conn);
		System.out.println("Client déconnecté: " + conn.getRemoteSocketAddress());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("Input reçu: " + message);

		// Traiter l'input (ex: position souris)
		if (message.contains("click")) {
			int x = extractInt(message, "x");
			int y = extractInt(message, "y");
			// Exemple: Inverser coordonnées et renvoyer
			String response = String.format("""
					{
					"action": "drawRect",
					"x": %s,
					"y": %s,
					"width": 50,
					"height": 50
					}
					""", x, y);
			System.out.println(response);
			broadcast(response);
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("Erreur: " + ex.getMessage());
	}

	@Override
	public void onStart() {
		System.out.println("Serveur WebSocket démarré sur ws://localhost:8081");
	}

	public void broadcast(String message) {
		synchronized (connections) {
			for (WebSocket conn : connections) {
				if (conn.isOpen()) {
					conn.send(message);
				}
			}
		}
	}

	private int extractInt(String message, String key) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		return json.has(key) ? json.get(key).getAsInt() : 0;
	}
}

// Serveur HTTP pour générer la page p5.js
class SimpleHttpServer {
	public static void start(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Serveur HTTP démarré sur http://localhost:" + port);
			while (true) {
				try (Socket clientSocket = serverSocket.accept();
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
					// Générer la page HTML avec p5.js
					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: text/html; charset=UTF-8");
					out.println();
					out.println("""
							<!DOCTYPE html>
							<html>
							<head>
							    <script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/1.9.4/p5.js"></script>
							</head>
							<body>
							    <script>
							        let socket;
							        let canvasWidth = 400;
							        let canvasHeight = 400;

							        function setup() {
							            createCanvas(canvasWidth, canvasHeight);
							            background(220);

							            // Connexion au serveur WebSocket
							            socket = new WebSocket('ws://localhost:8081');

							            socket.onopen = function() {
							                console.log('Connecté au serveur Java');
							            };

							            socket.onmessage = function(event) {
							                let data = JSON.parse(event.data);
							                if (data.action === 'drawCircle') {
							                    fill(255, 0, 0);
							                    circle(data.x, data.y, data.size);
							                } else if (data.action === 'drawRect') {
							                    fill(0, 0, 255);
							                    rect(data.x, data.y, data.width, data.height);
							                }
							            };

							            socket.onclose = function() {
							                console.log('Déconnecté');
							            };
							        }

							        function mouseClicked() {
							            let inputData = {
							                type: 'click',
							                x: mouseX,
							                y: mouseY
							            };
							            socket.send(JSON.stringify(inputData));
							        }

							        function mouseDragged() {
							            let inputData = {
							                type: 'mouse',
							                x: mouseX,
							                y: mouseY
							            };
							            socket.send(JSON.stringify(inputData));
							        }

							        function keyPressed() {
							            let inputData = {
							                type: 'key',
							                key: key
							            };
							            socket.send(JSON.stringify(inputData));
							        }
							    </script>
							</body>
							</html>""");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Classe principale pour démarrer les serveurs
public class ShinyWebSocketServer {
	public static void main(String[] args) {
		// Démarrer le serveur WebSocket sur le port 8081
		P5JsWebSocketServer wsServer = new P5JsWebSocketServer(8081);
		wsServer.start();

		// Démarrer le serveur HTTP sur le port 8080
		new Thread(() -> SimpleHttpServer.start(8080)).start();
	}
}
