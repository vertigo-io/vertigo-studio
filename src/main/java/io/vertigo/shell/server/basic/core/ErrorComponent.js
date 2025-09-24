class ErrorComponent extends Component {
    constructor({ text }) {
        super();
        this.title = "Error";
        this.text = text || 'An error occurred';
    }

    toHtml() {
        return `<div class="error-message" style="color: orange;">${this.text}</div>`;
    }
}