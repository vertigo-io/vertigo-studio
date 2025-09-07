package io.vertigo.shell.systems.photo.algos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public final class PhotoHash {

	/**
	 * Calculates the pHash (Perceptual Hash) of an image from a file.
	 * @param photo Image file
	 * @return Hash as a binary string (64 bits)
	 * @throws IOException if the file cannot be read
	 */
	public static String calculatePHash(Photo photo) throws IOException {
		// Load the image
		final BufferedImage image = photo.image();

		// Resize to 32x32 pixels
		final BufferedImage resized = resizeImage(image, 32, 32);

		// Convert to grayscale
		final double[][] grayscale = toGrayscale(resized);

		// Apply DCT
		final double[][] dct = applyDCT(grayscale);

		// Extract the 8x8 low-frequency coefficients
		final double[] lowFreq = new double[64];
		int index = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				lowFreq[index++] = dct[i][j];
			}
		}

		// Calculate the median of coefficients
		final double median = calculateMedian(lowFreq);

		// Generate the binary hash
		final StringBuilder hash = new StringBuilder();
		for (double val : lowFreq) {
			hash.append(val > median ? "1" : "0");
		}

		return hash.toString();
	}

	/**
	 * Calculates the dHash (Difference Hash) of an image from a file.
	 * @param photo Image file
	 * @return Hash as a binary string (64 bits)
	 * @throws IOException if the file cannot be read
	 */
	public static String calculateDHash(Photo photo) throws IOException {
		// Load the image
		final BufferedImage image = photo.image();

		// Resize to 9x8 pixels
		final BufferedImage resized = resizeImage(image, 9, 8);

		// Convert to grayscale
		final double[][] grayscale = toGrayscale(resized);

		// Generate the hash by comparing neighboring pixels
		final StringBuilder hash = new StringBuilder();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				hash.append(grayscale[y][x] > grayscale[y][x + 1] ? "1" : "0");
			}
		}

		return hash.toString();
	}

	/**
	 * Calculates the aHash (Average Hash) of an image from a file.
	 * @param photo Image file
	 * @return Hash as a binary string (64 bits)
	 * @throws IOException if the file cannot be read
	 */
	public static String calculateAHash(Photo photo) throws IOException {
		// Load the image
		final BufferedImage image = photo.image();

		// Resize to 8x8 pixels
		final BufferedImage resized = resizeImage(image, 8, 8);

		// Convert to grayscale
		final double[][] grayscale = toGrayscale(resized);

		// Calculate the average intensity
		double sum = 0.0;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				sum += grayscale[y][x];
			}
		}
		final double mean = sum / (8 * 8);

		// Generate the hash by comparing each pixel to the average
		final StringBuilder hash = new StringBuilder();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				hash.append(grayscale[y][x] > mean ? "1" : "0");
			}
		}

		return hash.toString();
	}

	/**
	 * Calculates the MD5 hash of a file.
	 * @param photo Image file
	 * @return MD5 hash as a hexadecimal string
	 * @throws IOException if the file cannot be read
	 * @throws NoSuchAlgorithmException if the MD5 algorithm is not available
	 */
	public static String calculateMD5Hash(Photo photo) throws IOException, NoSuchAlgorithmException {
		final byte[] fileBytes = Files.readAllBytes(photo.file().toPath());
		final MessageDigest md = MessageDigest.getInstance("MD5");
		final byte[] hashBytes = md.digest(fileBytes);

		// Convert to hexadecimal string
		final StringBuilder hexString = new StringBuilder();
		for (byte b : hashBytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * Resizes an image to the specified size.
	 */
	private static BufferedImage resizeImage(BufferedImage original, int width, int height) {
		final BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		resized.getGraphics().drawImage(original.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return resized;
	}

	/**
	 * Converts an image to grayscale (luminance matrix).
	 */
	private static double[][] toGrayscale(BufferedImage image) {
		final int width = image.getWidth();
		final int height = image.getHeight();
		final double[][] grayscale = new double[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = image.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = rgb & 0xFF;
				grayscale[y][x] = 0.299 * r + 0.587 * g + 0.114 * b; // Luminance formula
			}
		}
		return grayscale;
	}

	/**
	 * Applies the Discrete Cosine Transform (DCT) to a matrix.
	 */
	private static double[][] applyDCT(double[][] input) {
		final int n = input.length;
		final double[][] output = new double[n][n];

		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				double sum = 0.0;
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						sum += input[x][y] * Math.cos((2 * x + 1) * u * Math.PI / (2.0 * n))
								* Math.cos((2 * y + 1) * v * Math.PI / (2.0 * n));
					}
				}
				double cu = (u == 0) ? 1.0 / Math.sqrt(2) : 1.0;
				double cv = (v == 0) ? 1.0 / Math.sqrt(2) : 1.0;
				output[u][v] = sum * 2 / n * cu * cv;
			}
		}
		return output;
	}

	/**
	 * Calculates the median of an array of values.
	 */
	private static double calculateMedian(double[] values) {
		final double[] sorted = values.clone();
		java.util.Arrays.sort(sorted);
		final int mid = sorted.length / 2;
		return (sorted.length % 2 == 0) ? (sorted[mid - 1] + sorted[mid]) / 2.0 : sorted[mid];
	}

	/**
	 * Calculates the Hamming distance between two hashes.
	 * @param hash1 First hash
	 * @param hash2 Second hash
	 * @return Hamming distance (number of different bits)
	 */
	public static int hammingDistance(String hash1, String hash2) {
		if (hash1.length() != hash2.length()) {
			throw new IllegalArgumentException("Hashes must have the same length");
		}
		int distance = 0;
		for (int i = 0; i < hash1.length(); i++) {
			if (hash1.charAt(i) != hash2.charAt(i)) {
				distance++;
			}
		}
		return distance;
	}

	/**
	 * Usage example.
	 */
	public static void main(String[] args) {
		long start = Instant.now().toEpochMilli();
		try {
			final Photo photo1 = Photo.of("C:/Dev/vortex/samples/image1.jpg");
			final Photo photo2 = Photo.of("C:/Dev/vortex/samples/image2.jpg");

			// Calculate pHash
			String pHash1 = calculatePHash(photo1);
			String pHash2 = calculatePHash(photo2);
			System.out.println("pHash image1: " + pHash1);
			System.out.println("pHash image2: " + pHash2);
			System.out.println("pHash distance: " + hammingDistance(pHash1, pHash2));

			// Calculate dHash
			String dHash1 = calculateDHash(photo1);
			String dHash2 = calculateDHash(photo2);
			System.out.println("dHash image1: " + dHash1);
			System.out.println("dHash image2: " + dHash2);
			System.out.println("dHash distance: " + hammingDistance(dHash1, dHash2));

			// Calculate aHash
			String aHash1 = calculateAHash(photo1);
			String aHash2 = calculateAHash(photo2);
			System.out.println("aHash image1: " + aHash1);
			System.out.println("aHash image2: " + aHash2);
			System.out.println("aHash distance: " + hammingDistance(aHash1, aHash2));

			// Calculate MD5
			String md5Hash1 = calculateMD5Hash(photo1);
			String md5Hash2 = calculateMD5Hash(photo2);
			System.out.println("MD5 image1: " + md5Hash1);
			System.out.println("MD5 image2: " + md5Hash2);
			System.out.println("Identical images (MD5): " + md5Hash1.equals(md5Hash2));

		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(">exec in  >" + (Instant.now().toEpochMilli() - start) + "  millis");
	}
}
