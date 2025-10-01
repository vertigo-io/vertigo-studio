class ChakraTitleComponent extends Component {
    constructor({ title, level }) {
        super();
        this.title = title || 'Chakra Title';
        this.level = level || 1; // Default to h1 for Chakra titles
    }

    toHtml() {
        const tag = `h${Math.min(Math.max(this.level, 1), 6)}`;
        // Chakra-inspired styling for dark theme
        return `<${tag} class="chakra-title" style="color: #CBD5E0; font-weight: bold; margin-bottom: 0.5em;">${this.title}</${tag}>`;
    }
}