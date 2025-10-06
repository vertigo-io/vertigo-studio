package io.vertigo.shiny.components.dataviz.sankey;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinySankeyBuilder implements Builder<ShinySankey> {
	private String title;
	private final List<ShinySankeyLink> data = new ArrayList<>();

	public ShinySankeyBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public ShinySankeyBuilder addLink(final String from, final String to, final double flow) {
		this.data.add(new ShinySankeyLink(from, to, flow));
		return this;
	}

	public ShinySankeyBuilder addLinks(final List<ShinySankeyLink> links) {
		this.data.addAll(links);
		return this;
	}

	@Override
	public ShinySankey build() {
		return new ShinySankey(title, data);
	}
}