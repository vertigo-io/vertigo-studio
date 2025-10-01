class ShoelaceJsonComponent extends Component {
    constructor({ title, json }) {
        super();
        this.title = title || 'Shoelace JSON';
        this.json = json || {};
        this.detailsId = `shoelace-json-${Math.random().toString(36).substr(2, 9)}`;
    }

    _syntaxHighlight(json) {
        if (typeof json != 'string') {
            json = JSON.stringify(json, null, 2);
        }
        // Basic highlighting for demonstration
        return json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
                   .replace(/"(\w+)":/g, '<span style="color: var(--sl-color-primary-500);">"$1"</span>:')
                   .replace(/"([^"]*)"/g, '<span style="color: var(--sl-color-warning-500);">"$1"</span>')
                   .replace(/\b(true|false)\b/g, '<span style="color: var(--sl-color-success-500);">$&</span>')
                   .replace(/\b(null)\b/g, '<span style="color: var(--sl-color-danger-500);">$&</span>')
                   .replace(/\b(\d+(\.\d+)?)\b/g, '<span style="color: var(--sl-color-neutral-500);">$&</span>');
    }

    toHtml() {
        const highlightedJson = this._syntaxHighlight(this.json);
        return `<sl-details summary="${this.title}" style="margin-bottom: 1em;">
                    <pre style="background-color: var(--sl-color-neutral-100); padding: 1em; border-radius: var(--sl-border-radius-medium); overflow-x: auto;">${highlightedJson}</pre>
                </sl-details>`;
    }
}