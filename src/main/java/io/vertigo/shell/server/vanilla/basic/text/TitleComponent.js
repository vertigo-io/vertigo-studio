class TitleComponent extends Component {
    constructor({ title, level, text }) {
        super();
        this.title = title || 'Title';
        this.level = level || 2; // Default to h2
        this.text = text || '';
    }

    toHtml() {
        const tag = `h${Math.min(Math.max(this.level, 1), 6)}`; // Ensure h1 to h6
        return `<${tag} class="title-text">${this.text}</${tag}>`;
    }
}