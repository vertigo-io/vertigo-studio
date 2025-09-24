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
/*
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
}*/

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
        console.log('RssComponent initialized with items:', this.items);
    }
/*  Structure of an item
	title,
	link,
	description,
	image,
	pubDate,
	author*/
	
    toHtml() {
        if (this.items.length === 0) {
            console.warn('No items provided for RSS feed');
            return '<div>No items in the feed.</div>';
        }

        let feedHtml = '<div class="rss-feed">';
        this.items.forEach(item => {
            let parsedItem = item;
            if (typeof item === 'string') {
                console.log('Parsing string item:', item);
                const match = item.match(/\[(.*?)\]\((.*?)\)/);
                if (match && match.length === 3) {
                    parsedItem = {
                        title: match[1],
                        link: match[2],
                        description: '',
                        image: '',
                        pubDate: '',
                        author: ''
                    };
                } else {
                    parsedItem = {
                        title: item,
                        link: '',
                        description: '',
                        image: '',
                        pubDate: '',
                        author: ''
                    };
                }
            }

            const { title = 'Untitled', link = '', description = '', image = '', pubDate = '', author = '' } = parsedItem;

            // Valider et formater la date
            let formattedDate = '';
            if (pubDate) {
                try {
                    const date = new Date(pubDate);
                    if (!isNaN(date.getTime())) {
                        formattedDate = date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });
                    } else {
                        console.warn('Invalid pubDate:', pubDate);
                    }
                } catch (e) {
                    console.error('Error parsing pubDate:', pubDate, e);
                }
            }

            // Valider l'image
            if (image) {
                console.log('Image URL:', image);
            } else {
                console.warn('No image provided for item:', title);
            }

            const summary = description.length > 150 
			? description.substring(0, 150) + '...' 
			: description;

            feedHtml += `
                <div class="rss-card">
                    ${image ? `<div class="rss-image"><img src="${image}" alt="${title}" loading="lazy" onerror="this.style.display='none';"></div>` : '<div class="rss-image-placeholder"></div>'}
                    <div class="rss-content">
                        <h3 class="rss-headline"><a href="${link}" target="_blank" rel="noopener noreferrer">${title}</a></h3>
                        <p class="rss-summary">${summary}</p>
                    </div>
                </div>
            `;
        });
        feedHtml += '</div>';
        return feedHtml;
    }
}

class SankeyComponent extends Component {
  constructor({ title, data }) {
    super();
    this.title = title || 'Sankey Flow';
    this.data = data || []; // format attendu: [{ from, to, flow }]
    this.canvasId = `sankey-${Math.random().toString(36).substr(2, 9)}`;
    this.chart = null;
  }

  toHtml() {
    return `<canvas id="${this.canvasId}" class="sankey-canvas"></canvas>`;
  }

  activate() {
    const target = document.getElementById(this.canvasId);
    if (!target) throw new Error(`Sankey canvas not found: ${this.canvasId}`);

    // Détruire l'ancien graphe si besoin
    if (this.chart) this.chart.destroy();

    this.chart = new Chart(target, {
      type: 'sankey',
      data: {
        datasets: [{
          data: this.data,
          colorFrom: (c) => 'rgba(75, 192, 192, 0.8)',
          colorTo: (c) => 'rgba(153, 102, 255, 0.8)',
          colorMode: 'gradient'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: this.title
          },
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: (context) => {
                const { raw } = context;
                return `${raw.from} -> ${raw.to}: ${raw.flow}`;
              }
            }
          }
        }
      }
    });
  }
}

class GeoMapComponent extends Component {
    constructor({ title, latitude, longitude, label }) {
        super();
        this.title = title || 'Map';
        this.latitude = latitude || 0;
        this.longitude = longitude || 0;
        this.label = label || '';
        this.mapId = `map-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.mapId}" class="map-canvas"></div>`;
    }

    activate() {
        const mapContainer = document.getElementById(this.mapId);
        if (!mapContainer) {
            throw new Error(`Map container not found for ID: ${this.mapId}`);
        }

        // Initialize Leaflet map
        const map = L.map(this.mapId).setView([this.latitude, this.longitude], 12);

        // Add OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 18,
            tileSize: 256
        }).addTo(map);

        // Add marker for POI
        const marker = L.marker([this.latitude, this.longitude]).addTo(map);
        if (this.label) {
            marker.bindPopup(this.label).openPopup();
        }
    }
}