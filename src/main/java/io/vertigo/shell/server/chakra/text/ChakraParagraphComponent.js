class ChakraParagraphComponent extends Component {
    constructor({ text }) {
        super();
        this.text = text || 'Chakra Paragraph';
    }

    toHtml() {
        // Chakra-inspired styling for dark theme
        return `<p class="chakra-paragraph" style="color: #A0AEC0; line-height: 1.5;">${this.text}</p>`;
    }
}