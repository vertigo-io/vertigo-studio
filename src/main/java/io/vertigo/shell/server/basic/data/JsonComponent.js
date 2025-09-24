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