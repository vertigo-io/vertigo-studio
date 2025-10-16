<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Geo Map' }}</div>
    <div :id="mapId" class="map-canvas"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { ShinyGeoMap } from '../../models/media/geomap/ShinyGeoMap';

declare const L: any; // Declare L (Leaflet) to avoid TypeScript errors

const props = defineProps<{
  data: ShinyGeoMap
}>()

const mapId = `map-${Math.random().toString(36).substr(2, 9)}`;

onMounted(() => {
  const mapContainer = document.getElementById(mapId);
  if (!mapContainer) {
    console.error(`Map container not found for ID: ${mapId}`);
    return;
  }

  let initialLat = 0;
  let initialLng = 0;
  if (props.data.geoPoints && props.data.geoPoints.length > 0) {
    initialLat = props.data.geoPoints[0].latitude;
    initialLng = props.data.geoPoints[0].longitude;
  }

  const map = L.map(mapId).setView([initialLat, initialLng], 12);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    maxZoom: 18,
    tileSize: 256,
  }).addTo(map);

  if (props.data.geoPoints) {
    props.data.geoPoints.forEach((geoPoint: any) => {
      const marker = L.marker([geoPoint.latitude, geoPoint.longitude]).addTo(map);
      if (geoPoint.label) {
        marker.bindPopup(geoPoint.label).openPopup();
      } else if (props.data.title) {
        marker.bindPopup(props.data.title).openPopup();
      }
    });
  }
});
</script>

<style scoped>

.map-canvas {
	width: 100%;
	height: 300px; /* Default height for the map */
	display: block;
}
</style>