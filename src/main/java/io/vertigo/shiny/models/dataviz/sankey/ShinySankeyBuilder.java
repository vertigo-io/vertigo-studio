package io.vertigo.shiny.models.dataviz.sankey;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinySankeyBuilder implements Builder<ShinySankey> {
	private String _title;
	private final List<ShinySankeyLink> _data = new ArrayList<>();

	public ShinySankeyBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinySankeyBuilder addLink(final String from, final String to, final double flow) {
		this._data.add(new ShinySankeyLink(from, to, flow));
		return this;
	}

	public ShinySankeyBuilder addLinks(final List<ShinySankeyLink> links) {
		this._data.addAll(links);
		return this;
	}

	@Override
	public ShinySankey build() {
		return new ShinySankey(_title, _data);
	}
}