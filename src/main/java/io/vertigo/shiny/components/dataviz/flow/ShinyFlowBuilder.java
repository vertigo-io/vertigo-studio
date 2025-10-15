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
package io.vertigo.shiny.components.dataviz.flow;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinyFlowBuilder implements Builder<ShinyFlow> {
	private final List<ShinyFlowNode> _nodes = new ArrayList<>();
	private final List<ShinyFlowEdge> _edges = new ArrayList<>();

	public ShinyFlowBuilder withNode(ShinyFlowNode node) {
		_nodes.add(node);
		return this;
	}

	public ShinyFlowBuilder withEdge(ShinyFlowEdge edge) {
		_edges.add(edge);
		return this;
	}

	public ShinyFlow build() {
		return new ShinyFlow(_nodes, _edges);
	}
}
