class PhotoComponent extends Component {
    constructor({ title, url, alt }) {
        super();
        this.title = title || 'Photo';
        this.url = url;
        this.alt = alt || 'Image';
    }

    toHtml() {
        if (!this.url) {
            return `<div class="photo-container">No image URL provided.</div>`;
        }
        return `
            <div class="photo-container">
                <img src="${this.url}" alt="${this.alt}" class="photo-image">
            </div>
        `;
    }
}