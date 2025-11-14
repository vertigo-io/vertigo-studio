package io.vertigo.shiny.models.input.codeeditor;

public final class ShinyCodeEditorBuilder {
	private String _language;
	private String _content;

	public ShinyCodeEditorBuilder withLanguage(final String language) {
		_language = language;
		return this;
	}

	public ShinyCodeEditorBuilder withContent(final String content) {
		_content = content;
		return this;
	}

	public ShinyCodeEditor build() {
		return new ShinyCodeEditor(_language, _content);
	}
}
