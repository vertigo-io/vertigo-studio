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