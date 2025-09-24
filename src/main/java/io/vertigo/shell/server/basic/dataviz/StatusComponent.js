class StatusComponent extends Component {
    constructor({ title, types }) {
        super();
        this.title = title || 'Status';
        this.types = types || [];
        this.divId = `status-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}" class="status-container">
                    <h3 class="status-title">${this.title}</h3>
                    <div class="status-indicators"></div>
                </div>`;
    }

    activate() {
        const indicatorsContainer = document.querySelector(`#${this.divId} .status-indicators`);
        if (!indicatorsContainer) {
            throw new Error(`Status indicators container not found for ID: ${this.divId}`);
        }

        const colorMap = {
            SUCCESS: 'green',
            ERROR: 'red',
            WARNING: 'orange',
            INFO: 'blue',
            NEUTRAL: 'gray'
        };

        this.types.forEach(type => {
            const indicator = document.createElement('div');
            indicator.className = 'status-indicator';
            indicator.style.backgroundColor = colorMap[type] || 'gray';
            indicatorsContainer.appendChild(indicator);
        });
    }
}