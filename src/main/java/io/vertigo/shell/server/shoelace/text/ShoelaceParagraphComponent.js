class ShoelaceParagraphComponent extends Component {
    constructor({ text }) {
        super();
        this.text = text || 'Shoelace Paragraph';
    }

    toHtml() {
        return `<sl-card style="margin-bottom: 1em;"><p>${this.text}</p></sl-card>`;
    }
}