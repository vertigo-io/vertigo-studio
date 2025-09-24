class ChakraListComponent extends Component {
    constructor({ title, items }) {
        super();
        this.title = title || 'Chakra List';
        this.items = items || [];
    }

    toHtml() {
        const itemsHtml = this.items.map(item => `
            <li style="margin-bottom: 0.5em; color: #CBD5E0;">
                ${item}
            </li>
        `).join('');

        return `<div style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <ul style="list-style-type: none; padding: 0;">
                        ${itemsHtml}
                    </ul>
                </div>`;
    }
}