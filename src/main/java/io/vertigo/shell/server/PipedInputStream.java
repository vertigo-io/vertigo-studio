package io.vertigo.shell.server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class PipedInputStream extends InputStream {
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
