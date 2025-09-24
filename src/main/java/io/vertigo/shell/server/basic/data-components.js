/* All components must inherit Component */
class TableComponent extends Component {
	constructor({ title, header, rows }) {
		super();
		this.title = title || 'Table';
		this.header = header || [];
		this.rows = rows || [];
	}

	toHtml() {
		const headerHtml = this.header.map(h => `<th>${h}</th>`).join('');
		const rowsHtml = this.rows.map(row =>
			`<tr>${row.map(cell => `<td>${cell}</td>`).join('')}</tr>`
		).join('');

		return `<table><thead><tr>${headerHtml}</tr></thead><tbody>${rowsHtml}</tbody></table>`;
	}
}

class ListComponent extends Component {
	static TYPES = {
		ORDERED: 'ORDERED',
		UNORDERED: 'UNORDERED',
		DASHED: 'DASHED'
	};

	constructor({ title, type, items }) {
		super();
		this.title = title || '';
		this.type = type || ListComponent.TYPES.UNORDERED;
		this.items = (items || []).map(item => {
			if (typeof item === 'object' && item !== null) {
				return new ListComponent(item);
			}
			return item;
		});
	}

	toHtml() {
		let tag;
		let className = '';
		switch (this.type) {
			case ListComponent.TYPES.ORDERED:
				tag = 'ol';
				break;
			case ListComponent.TYPES.UNORDERED:
				tag = 'ul';
				break;
			case ListComponent.TYPES.DASHED:
				tag = 'ul';
				className = 'dashed-list';
				break;
			default:
				throw new Error(`Unknown list type: ${this.type}`);
		}

		const itemsHtml = this.items.map(item => {
			if (item instanceof ListComponent) {
				return `<li><span class="nested-title">${item.title}</span>${item.toHtml()}</li>`;
			}
			return `<li>${item}</li>`;
		}).join('');

		return `<${tag} class="${className}">${itemsHtml}</${tag}>`;
	}
}

class JsonComponent extends Component {
	constructor({ title, json }) {
		super();
		this.title = title || 'JSON';
		this.json = json || {};
	}

	_syntaxHighlight(json) {
		if (typeof json != 'string') {
			json = JSON.stringify(json, null, 2);
		}
		return json.replace(/("(\u[a-zA-Z0-9]{4}|\\.[^u]|[^\\"])*"(?:\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
			let escapedMatch = match.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
			if (/^"/.test(match)) {
				return /:$/.test(match) ? `<span class="json-key">${escapedMatch}</span>` : `<span class="json-string">${escapedMatch}</span>`;
			} else if (/true|false/.test(match)) {
				return `<span class="json-boolean">${escapedMatch}</span>`;
			} else if (/null/.test(match)) {
				return `<span class="json-null">${escapedMatch}</span>`;
			} else {
				return `<span class="json-number">${escapedMatch}</span>`;
			}
		});
	}

	toHtml() {
		const highlightedJson = this._syntaxHighlight(this.json);
		return `<div class="json-container">${highlightedJson}</div>`;
	}
}

class TreeComponent extends Component {
    constructor({ title, rootNode }) {
        super();
        this.title = title || 'Tree';
        this.rootNode = rootNode || { 
			label: 'Root', 
			nodes: [], 
			icon: 'NONE' };
    }

    toHtml() {
        // Recursively generate HTML for the tree
        const generateNodeHtml = (node) => {
            const iconHtml = node.icon && node.icon !== 'NONE' ? `<i data-lucide="${node.icon}" class="tree-icon"></i>` : '';
            const childrenHtml = node.nodes && node.nodes.length > 0
                ? `<ul class="tree-nodes">${node.nodes.map(child => `<li>${generateNodeHtml(child)}</li>`).join('')}</ul>`
                : '';
            return `
                <div class="tree-node">
                    ${iconHtml}
                    <span class="node-label">${node.label}</span>
                    ${childrenHtml}
                </div>
            `;
        };

        return `
            <div class="tree-container">
                <ul class="tree-nodes">
                    <li>${generateNodeHtml(this.rootNode)}</li>
                </ul>
            </div>
        `;
    }

    activate() {
        // Initialize Lucide icons for the tree
        lucide.createIcons({ nameAttr: 'data-lucide' });
    }
}
