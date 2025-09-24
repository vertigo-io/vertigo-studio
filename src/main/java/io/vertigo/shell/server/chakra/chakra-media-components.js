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

class ChakraVideoComponent extends Component {
    constructor({ title, videoId }) {
        super();
        this.title = title || 'Chakra Video';
        this.videoId = videoId || '';
    }

    toHtml() {
        return `<div class="chakra-video-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-video-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <iframe width="100%" height="315" src="https://www.youtube.com/embed/${this.videoId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </div>`;
    }
}