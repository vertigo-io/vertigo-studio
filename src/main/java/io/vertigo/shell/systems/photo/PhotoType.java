package io.vertigo.shell.systems.photo;

public enum PhotoType {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp");

    private final String extension;

    PhotoType(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static boolean isPhoto(final String fileName) {
        final String lowerCaseFileName = fileName.toLowerCase();
        for (final PhotoType photoType : values()) {
            if (lowerCaseFileName.endsWith("." + photoType.getExtension())) {
                return true;
            }
        }
        return false;
    }
}
