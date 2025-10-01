class ShoelaceVideoComponent extends Component {
    constructor({ title, videoId }) {
        super();
        this.title = title || 'Shoelace Video';
        this.videoId = videoId || '';
    }

    toHtml() {
        if (!this.videoId) {
            return `<sl-card style="margin-bottom: 1em;">No video ID provided.</sl-card>`;
        }
        return `<sl-card style="margin-bottom: 1em;">
                    <div slot="header">${this.title}</div>
                    <iframe width="100%" height="315" src="https://www.youtube.com/embed/${this.videoId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </sl-card>`;
    }
}