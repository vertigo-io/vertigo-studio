package io.vertigo.shiny.models.dataviz.sankey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinySankeyBuilder implements Builder<ShinySankey> {
	private UUID _id;
	private String _title;
	private final List<ShinySankeyLink> _data = new ArrayList<>();

	public ShinySankeyBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

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
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinySankey(_id, _title, _data);
	}
}