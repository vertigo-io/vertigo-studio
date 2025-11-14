package io.vertigo.shiny.models.input.codeeditor;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyCodeEditor(String language, String content) implements ShinyBlock {
}
