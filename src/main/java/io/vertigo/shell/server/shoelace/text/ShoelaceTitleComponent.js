class ShoelaceTitleComponent extends Component {
    constructor({ title, level }) {
        super();
        this.title = title || 'Shoelace Title';
        this.level = level || 1; // Default to h1
    }

    toHtml() {
        const tag = `h${Math.min(Math.max(this.level, 1), 6)}`;
        return `<sl-card style="margin-bottom: 1em;"><${tag}>${this.title}</${tag}></sl-card>`;
    }
}