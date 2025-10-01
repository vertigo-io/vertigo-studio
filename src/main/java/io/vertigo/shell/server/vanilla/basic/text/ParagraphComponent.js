class ParagraphComponent extends Component {
    constructor({ title, text }) {
        super();
        this.title = title || 'Paragraph';
        this.text = text || '';
    }

    toHtml() {
        return `<p class="paragraph-text">${this.text}</p>`;
    }
}