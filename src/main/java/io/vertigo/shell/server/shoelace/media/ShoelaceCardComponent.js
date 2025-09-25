class ShoelaceCardComponent extends Component {
    constructor({ title, description, imageUrl }) {
        super();
        this.title = title || 'Shoelace Card';
        this.description = description || '';
        this.imageUrl = imageUrl || '';
    }

    toHtml() {
        return `<sl-card style="margin-bottom: 1em;">
                    ${this.imageUrl ? `<img slot="image" src="${this.imageUrl}" alt="${this.title}" />` : ''}
                    <strong>${this.title}</strong><br />
                    ${this.description}
                </sl-card>`;
    }
}