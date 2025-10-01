class TextPathComponent extends Component {
    constructor({ path, separator }) {
        super();
        this.path = path || '';
        this.separator = separator || '/';
        this.divId = `textpath-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}" class="text-path"></div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        if (!container) {
            throw new Error(`Container not found for ID: ${this.divId}`);
        }

        const rootColor = 'green';
        const nodeColor = 'yellow';
        const leafColor = '#87CEEB'; // skyblue
        const separatorColor = 'red';

        const parts = this.path.split(this.separator);
        parts.forEach((part, index) => {
            const partSpan = document.createElement('span');
            partSpan.textContent = part;

            if (index === 0) {
                partSpan.style.color = rootColor;
            } else if (index === parts.length - 1) {
                partSpan.style.color = leafColor;
            }
            container.appendChild(partSpan);

            if (index < parts.length - 1) {
                const separatorSpan = document.createElement('span');
                separatorSpan.textContent = this.separator;
                separatorSpan.style.color = separatorColor;
                container.appendChild(separatorSpan);
            }
        });
    }
}