<template>
  <div class="shiny-geo-map-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Geo Map' }}</h3>
    <div :id="mapId" class="shiny-map-canvas"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

declare const L: any; // Declare L (Leaflet) to avoid TypeScript errors

export default defineComponent({
  name: 'VShinyGeoMapComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      mapId: `map-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const mapContainer = document.getElementById(this.mapId);
    if (!mapContainer) {
      console.error(`Map container not found for ID: ${this.mapId}`);
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
      tileSize: 256,
    }).addTo(map);

    if (this.data.geoPoints) {
      this.data.geoPoints.forEach((geoPoint: any) => {
        const marker = L.marker([geoPoint.latitude, geoPoint.longitude]).addTo(map);
        if (geoPoint.label) {
          marker.bindPopup(geoPoint.label).openPopup();
        } else if (this.data.title) {
          marker.bindPopup(this.data.title).openPopup();
        }
      });
    }
  },
});
</script>

<style scoped>
.shiny-geo-map-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
  text-align: center;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 15px;
}

.shiny-map-canvas {
  height: 400px; /* Adjust map height as needed */
  width: 100%;
  border-radius: 8px;
}
</style>