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
    constructor({ title, url }) {
        super();
        this.title = title || 'Spotify Content';
        this.url = url; // e.g., spotify:track:3n3Ppam7aqaR3ple0eGzCJ
    }

    toHtml() {
        return `
            <div class="spotify-container">
                <iframe 
                    src="${this.url}"
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

class RssComponent extends Component {
    constructor({ title, items }) {
        super();
        this.title = title || 'RSS Feed';
        this.items = items || [];
    }

    toHtml() {
        if (this.items.length === 0) {
            return '<div>No items in the feed.</div>';
        }

        let listHtml = '<ul>';
        this.items.forEach(item => {
            // Parse markdown link: [Title](URL)
            const match = item.match(/\[(.*?)\]\((.*?)\)/);
            if (match && match.length === 3) {
                const itemTitle = match[1];
                const itemUrl = match[2];
                listHtml += `<li><a href="${itemUrl}" target="_blank" rel="noopener noreferrer">${itemTitle}</a></li>`;
            } else {
                listHtml += `<li>${item}</li>`; // Fallback for non-markdown items
            }
        });
        listHtml += '</ul>';
        return listHtml;
    }
}