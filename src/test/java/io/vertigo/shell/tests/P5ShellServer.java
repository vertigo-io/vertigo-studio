package io.vertigo.shell.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public final class P5ShellServer extends WebSocketServer {
	private WebSocket currentWebSocket;
	private final BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
	private final PipedInputStream pipedInputStream = new PipedInputStream();
	private final PipedPrintStream pipedPrintStream = new PipedPrintStream();

	public P5ShellServer(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
		this.currentWebSocket = conn;
		conn.send("Connected to Java Shell. Type 'exit' to quit.");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + " Code: " + code + " Reason: " + reason);
		if (this.currentWebSocket == conn) {
			this.currentWebSocket = null;
		}
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		try {
			inputQueue.put(message + "\n"); // Add newline to simulate console input
			pipedInputStream.append(message + "\n");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Failed to put message into queue: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Failed to append message to piped input stream: " + e.getMessage());
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("WebSocket error: " + ex.getMessage());
		if (conn != null) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	@Override
	public void onStart() {
		System.out.println("P5ShellServer started on port " + getPort());
		setConnectionLostTimeout(0); // Disable timeout for testing
	}

	public InputStream getInputStream() {
		return pipedInputStream;
	}

	public PrintStream getPrintStream() {
		return pipedPrintStream;
	}

	// Custom InputStream to feed data from WebSocket to Java application
	private static class PipedInputStream extends InputStream {
		private final BlockingQueue<Byte> buffer = new LinkedBlockingQueue<>();

		@Override
		public int read() throws IOException {
			try {
				return buffer.take();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new IOException("Interrupted while reading from buffer", e);
			}
		}

		public void append(String text) throws IOException {
			for (byte b : text.getBytes(StandardCharsets.UTF_8)) {
				try {
					buffer.put(b);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new IOException("Interrupted while appending to buffer", e);
				}
			}
		}
	}

	// Custom PrintStream to send data from Java application to WebSocket
	private class PipedPrintStream extends PrintStream {
		public PipedPrintStream() {
			super(new NullOutputStream()); // Use a dummy output stream
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			if (currentWebSocket != null && currentWebSocket.isOpen()) {
				currentWebSocket.send(new String(buf, off, len, StandardCharsets.UTF_8));
			} else {
				System.out.write(buf, off, len); // Fallback to System.out if no client
			}
		}

		@Override
		public void write(int b) {
			write(new byte[] { (byte) b }, 0, 1);
		}
	}

	private static class NullOutputStream extends java.io.OutputStream {
		@Override
		public void write(int b) throws IOException {
			// Do nothing
		}
	}
}
