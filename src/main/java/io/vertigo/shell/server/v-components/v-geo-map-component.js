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

        let initialLat = 0;
        let initialLng = 0;
        if (this.data.geoPoints && this.data.geoPoints.length > 0) {
            initialLat = this.data.geoPoints[0].latitude;
            initialLng = this.data.geoPoints[0].longitude;
        }

        const map = L.map(this.mapId).setView([initialLat, initialLng], 12);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 18,
            tileSize: 256
        }).addTo(map);

        if (this.data.geoPoints) {
            this.data.geoPoints.forEach(geoPoint => {
                const marker = L.marker([geoPoint.latitude, geoPoint.longitude]).addTo(map);
                if (geoPoint.label) {
                    marker.bindPopup(geoPoint.label).openPopup();
                } else if (this.data.title) {
                    marker.bindPopup(this.data.title).openPopup();
                }
            });
        }
    }
});