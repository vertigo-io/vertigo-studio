Vue.component('v-geo-map-component', {
    props: ['data'],
    template: '<div :id="mapId" class="map-canvas"></div>',
    data() {
        return {
            mapId: `map-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const mapContainer = document.getElementById(this.mapId);
        if (!mapContainer) {
            return;
        }
        const map = L.map(this.mapId).setView([this.data.latitude, this.data.longitude], 12);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 18,
            tileSize: 256
        }).addTo(map);
        const marker = L.marker([this.data.latitude, this.data.longitude]).addTo(map);
        if (this.data.label) {
            marker.bindPopup(this.data.label).openPopup();
        }
    }
});