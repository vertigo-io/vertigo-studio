package io.vertigo.shiny.models.data.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTreeBuilder implements Builder<ShinyTree> {
	private UUID _id;
	private String _label;
	private final List<ShinyTree> _children = new ArrayList<>();
	private final ShinyTreeBuilder _parent;

	private ShinyTreeBuilder(ShinyTreeBuilder parent) {
		this._parent = parent;
	}

	public ShinyTreeBuilder() {
		//root has no parent
		this(null);
	}

	public ShinyTreeBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyTreeBuilder withLabel(String label) {
		this._label = label;
		return this;
	}

	public ShinyTreeBuilder addLeaf(String childLabel) {
		this._children.add(new ShinyTree(childLabel));
		return this;
	}

	public ShinyTreeBuilder addAllTrees(List<ShinyTree> subTrees) {
		Assertion.check().isNotNull(subTrees);
		//---
		_children.addAll(subTrees);
		return this;
	}

	public ShinyTreeBuilder addTree(ShinyTree subTree) {
		Assertion.check().isNotNull(subTree);
		//---
		_children.add(subTree);
		return this;
	}

	public ShinyTreeBuilder addTree(String label) {
		return new ShinyTreeBuilder(this).withLabel(label);
	}

	public ShinyTreeBuilder up() {
		if (_parent == null) {
			throw new IllegalStateException("Unable to go up ; you have reached the top oh the tree");
		}
		_parent.addTree(this.internalBuild());
		return _parent;
	}

	public ShinyTreeBuilder root() {
		if (_parent == null) {
			return this;
		}
		return up().root(); // Récursif : remonte d'un niveau et continue
	}

	private ShinyTree internalBuild() {
		Assertion.check().isNotNull(_parent, "internalBuild must be called only for sub trees");
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyTree(_id, _label, _children);
	}

	public ShinyTree build() {
		Assertion.check().isNull(_parent, "build must be called on the root");
		//---
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyTree(_id, _label, _children);
	}

}
