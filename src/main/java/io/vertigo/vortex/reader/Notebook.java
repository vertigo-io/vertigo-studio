package io.vertigo.vortex.reader;

import java.io.File;
import java.util.List;

public record Notebook(List<File> libraries, List<File> modules) {

}
