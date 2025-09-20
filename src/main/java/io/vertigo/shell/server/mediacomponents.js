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

class SpotifyComponent extends Component {
    constructor({ title, uri }) {
        super();
        this.title = title || 'Spotify Content';
        this.uri = uri; // e.g., spotify:track:3n3Ppam7aqaR3ple0eGzCJ
    }

    toHtml() {
        if (!this.uri) {
            return `<div class="spotify-container">No Spotify URI provided.</div>`;
        }
        // Convert Spotify URI to embed URL
        const embedUrl = this.uri.replace('spotify:', 'https://open.spotify.com/embed/').replace(':', '/');
        return `
            <div class="spotify-container">
                <iframe 
                    src="${embedUrl}"
                    width="100%" 
                    height="380" 
                    frameborder="0" 
                    allowtransparency="true" 
                    allow="encrypted-media">
                </iframe>
            </div>
        `;
    }
}

class PhotoComponent extends Component {
    constructor({ title, url, alt }) {
        super();
        this.title = title || 'Photo';
        this.url = url;
        this.alt = alt || 'Image';
    }

    toHtml() {
        if (!this.url) {
            return `<div class="photo-container">No image URL provided.</div>`;
        }
        return `
            <div class="photo-container">
                <img src="${this.url}" alt="${this.alt}" class="photo-image">
            </div>
        `;
    }
}