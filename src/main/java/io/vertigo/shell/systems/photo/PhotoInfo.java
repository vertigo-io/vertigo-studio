package io.vertigo.shell.systems.photo;

import java.nio.file.Path;
import java.util.Map;

public record PhotoInfo(Path path, long size, Map<String, String> metadata, String md5Hash) {
    // All fields, constructor, getters, equals, hashCode, and toString are implicitly defined.
}
