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