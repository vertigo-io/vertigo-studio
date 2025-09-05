package io.vertigo.shell.systems.file;

import io.vertigo.core.lang.Assertion;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

public class FileContext {
    private final Path rootPath;
    private Path currentPath;

    public FileContext(Path rootPath) {
        Assertion.check().isNotNull(rootPath);
        Assertion.check().isTrue(Files.isDirectory(rootPath), "Root path must be a directory");
        this.rootPath = rootPath.toAbsolutePath().normalize();
        this.currentPath = Paths.get(""); // Relative to root
    }

    public Path getRootPath() {
        return rootPath;
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public Path getCurrentAbsolutePath() {
        return rootPath.resolve(currentPath).normalize();
    }

    public void changeDirectory(String path) throws IOException {
        Path newResolvedPath;
        if (path.equals(".")) {
            newResolvedPath = currentPath; // Stay in current directory
        } else if (path.equals("..")) {
            newResolvedPath = currentPath.getParent(); // Go up one level
            if (newResolvedPath == null) { // Already at root
                newResolvedPath = Paths.get("");
            }
        } else if (Paths.get(path).isAbsolute()) {
            // If absolute path, check if it's within rootPath
            Path absoluteInputPath = Paths.get(path).normalize();
            if (!absoluteInputPath.startsWith(rootPath)) {
                throw new IOException("Cannot change to directory outside root: " + path);
            }
            newResolvedPath = rootPath.relativize(absoluteInputPath); // Get path relative to root
        } else {
            // Relative path
            newResolvedPath = currentPath.resolve(path).normalize();
        }

        // Ensure the new path is still within the rootPath
        Path absoluteNewPath = rootPath.resolve(newResolvedPath).normalize();
        if (!absoluteNewPath.startsWith(rootPath)) {
            throw new IOException("Cannot change to directory outside root: " + path);
        }
        if (!Files.isDirectory(absoluteNewPath)) {
            throw new IOException("Not a directory: " + path);
        }

        this.currentPath = newResolvedPath;
    }

    public Path resolvePath(String path) {
        return getCurrentAbsolutePath().resolve(path).normalize();
    }
}
