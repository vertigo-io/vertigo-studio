class YouTubeComponent extends Component {
    constructor({ title, videoId }) {
        super();
        this.title = title || 'YouTube Video';
        this.videoId = videoId;
    }

    toHtml() {
        if (!this.videoId) {
            return `<div class="youtube-container">No video ID provided.</div>`;
        }
        return `
            <div class="youtube-container">
                <iframe 
                    class="youtube-player"
                    src="https://www.youtube.com/embed/${this.videoId}" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                    allowfullscreen>
                </iframe>
            </div>
        `;
    }
}