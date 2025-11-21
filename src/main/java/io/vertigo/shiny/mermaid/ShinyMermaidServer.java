package io.vertigo.shiny.mermaid;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;

// This is a simplified webSErver. A real one would use a dedicated library.

public final class ShinyMermaidServer {
	private final Shiny shiny;
	private final int port;
	private HttpServer httpServer;
	private String currentMermaidDiagram = "";

	public ShinyMermaidServer(@Nonnull final Shiny shiny, final int port) {
		Assertion.check().isNotNull(shiny);
		Assertion.check().isTrue(port > 0 && port < 65536, "Port must be valid");
		//---
		this.shiny = shiny;
		this.port = port;
	}

	public void start() throws IOException {
		httpServer = HttpServer.create(new InetSocketAddress(port), 0);
		httpServer.createContext("/", new HtmlHandler());
		// For a real WebSocket, we'd have a separate context/handler for WebSocket upgrades
		// For now, we'll just serve the HTML and assume the JS will try to connect.
		httpServer.setExecutor(Executors.newFixedThreadPool(1)); // Single thread for simplicity
		httpServer.start();
		shiny.getWriter().println("ShinyMermaidServer started on http://localhost:" + port);
		shiny.getWriter().println("Open your browser to view the diagram.");
	}

	public void stop() {
		if (httpServer != null) {
			httpServer.stop(0); // Stop immediately
			shiny.getWriter().println("ShinyMermaidServer stopped.");
		}
	}

	public void updateDiagram(@Nonnull final String mermaidDiagram) {
		Assertion.check().isNotBlank(mermaidDiagram);
		//---
		this.currentMermaidDiagram = mermaidDiagram;
	}

	private class HtmlHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			final String response = getHtmlPage();
			exchange.sendResponseHeaders(200, response.length());
			try (final OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes());
			}
		}
	}

	private String getHtmlPage() {
		// Basic HTML page with Mermaid.js integration
		return String.format("""
				<!DOCTYPE html>
				  <html>
				    <head>
					  <title>Mermaid DB Model</title>
				      <script src="https://cdn.jsdelivr.net/npm/mermaid@10.9.0/dist/mermaid.min.js"></script>
				      <style>
						    body { font-family: sans-serif; margin: 20px; }
						    .mermaid { border: 1px solid #ccc; padding: 10px; border-radius: 5px; }
				      </style>
				    </head>
				    <body>
				      <h1>Database Model Diagram</h1>
				      <div class="mermaid">
				%s
				      </div>
					  <script>mermaid.initialize({ startOnLoad: true });</script>
				    </body>
				  </html>
				""", currentMermaidDiagram);
	}
}
