package io.vertigo.shell.systems.photo.algos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

final class Photo {
	private final File file;
	private final BufferedImage image;
	//	private final String format;

	private Photo(File file) throws IOException {
		this.file = file;
		this.image = ImageIO.read(file);
		//	this.format = getFormatFromFile(file);
	}

	//	private static String getFormatFromFile(File file) {
	//		String name = file.getName();
	//		int lastDot = name.lastIndexOf('.');
	//		if (lastDot == -1 || lastDot == name.length() - 1) {
	//			return "jpg"; // Format par défaut
	//		}
	//		return name.substring(lastDot + 1).toLowerCase();
	//	}

	File file() {
		return file;
	}

	//	String format() {
	//		return format;
	//	}

	BufferedImage image() {
		return image;
	}

	static Photo of(String path) throws IOException {
		return new Photo(new File(path));
	}
}
