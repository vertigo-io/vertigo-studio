package io.vertigo.banshee.servers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import io.vertigo.core.lang.Builder;
import io.vertigo.core.lang.VSystemException;

public final class BansheeWebServerBuilder implements Builder<HttpServer> {
	private int _port;
	private String _rootDir;

	public BansheeWebServerBuilder(int port, String rootDir) throws IOException {
		this._port = port;
		this._rootDir = rootDir;
	}

	public HttpServer build() {
		final HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(_port), 0);
		} catch (IOException e) {
			throw new VSystemException(e, "Unable to create an HttpServer");

		}
		System.out.println("Web server started on port " + _port);
		System.out.println("Serving files from: " + new File(_rootDir).getAbsolutePath());

		server.createContext("/", new FileHandler(_rootDir));
		server.setExecutor(null);
		return server;
	}

	static class FileHandler implements HttpHandler {
		private final String rootDir;

		public FileHandler(String rootDir) {
			this.rootDir = rootDir;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String requestedPath = exchange.getRequestURI().getPath();

			if (requestedPath.equals("/")) {
				requestedPath = "/index.html";
			}

			final Path filePath = Paths.get(rootDir, requestedPath.substring(1)).toAbsolutePath();
			final File file = filePath.toFile();

			if (file.exists() && !file.isDirectory()) {
				String contentType = "application/octet-stream";
				if (requestedPath.endsWith(".html")) {
					contentType = "text/html; charset=utf-8";
					// Add Permissions-Policy header for HTML files
					exchange.getResponseHeaders().set("Permissions-Policy", "encrypted-media=*");
				} else if (requestedPath.endsWith(".css")) {
					contentType = "text/css; charset=utf-8";
				} else if (requestedPath.endsWith(".ts")) {
					contentType = "application/javascript; charset=utf-8";
				} else if (requestedPath.endsWith(".js")) {
					contentType = "application/javascript; charset=utf-8";
				}

				exchange.getResponseHeaders().set("Content-Type", contentType);
				exchange.sendResponseHeaders(200, file.length());
				try (OutputStream os = exchange.getResponseBody()) {
					Files.copy(filePath, os);
				}
			} else {
				String response = "404 (Not Found): " + requestedPath;
				exchange.sendResponseHeaders(404, response.length());
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(response.getBytes());
				}
			}
		}
	}
}
