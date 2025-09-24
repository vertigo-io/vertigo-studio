class ChakraCardComponent extends Component {
    constructor({ title, description, imageUrl }) {
        super();
        this.title = title || 'Chakra Card';
        this.description = description || '';
        this.imageUrl = imageUrl || '';
    }

    toHtml() {
        return `<div class="chakra-card" style="background-color: #2D3748; border-radius: 8px; overflow: hidden; color: #CBD5E0; max-width: 300px;">
                    ${this.imageUrl ? `<img src="${this.imageUrl}" alt="${this.title}" style="width: 100%; height: auto;">` : ''}
                    <div class="chakra-card-body" style="padding: 15px;">
                        <h4 class="chakra-card-title" style="font-weight: bold; margin-bottom: 10px;">${this.title}</h4>
                        <p class="chakra-card-description">${this.description}</p>
                    </div>
                </div>`;
    }
}