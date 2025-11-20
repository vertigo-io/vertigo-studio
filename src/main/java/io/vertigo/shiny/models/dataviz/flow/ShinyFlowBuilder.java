/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.shiny.models.dataviz.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.dataviz.flow.ShinyFlowNode.Position;

public final class ShinyFlowBuilder implements Builder<ShinyFlow> {

	private UUID _id;
	private final List<ShinyFlowNode> _nodes = new ArrayList<>();
	private final List<ShinyFlowEdge> _edges = new ArrayList<>();

	public ShinyFlowBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyFlowBuilder withNode(final String id, final String label, final int x, final int y, final NodeType nodeType) { // Added NodeType
		_nodes.add(new ShinyFlowNode(id, label, new Position(x, y), nodeType));
		return this;
	}

	// Removed the old withNode methods as they are now replaced by the one with NodeType
	// public ShinyFlowBuilder withNode(final String id, final String label, final int x, final int y) {
	// 	_nodes.add(new ShinyFlowNode(id, label, new Position(x, y), null));
	// 	return this;
	// }

	// public ShinyFlowBuilder withNode(final String id, final String label, final int x, final int int y, final String type) {
	// 	_nodes.add(new ShinyFlowNode(id, label, new Position(x, y), type));
	// 	return this;
	// }

	public ShinyFlowBuilder withEdge(final String id, final String source, final String target) {
		_edges.add(new ShinyFlowEdge(id, source, target, null));
		return this;
	}

	public ShinyFlowBuilder withEdge(final String id, final String source, final String target, final String label) {
		_edges.add(new ShinyFlowEdge(id, source, target, label));
		return this;
	}

	@Override
	public ShinyFlow build() {
		Assertion.check()
				.isNotNull(_nodes)
				.isNotNull(_edges);
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyFlow(_id, _nodes, _edges);
	}
}
