<template>
  <div class="shiny-container">
    <template v-for="(component, index) in data.components" :key="index">
      <component :is="resolveComponent(component.type)" :data="component"></component>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ShinyContainer } from '../../models/core/container/ShinyContainer';
import { ShinyComponent } from '../../models/ShinyComponent';

// Core
import VShinyErrorComponent from '../core/v-shiny-error-component.vue';
// Data
import VShinyCardComponent from '../data/v-shiny-card-component.vue';
import VShinyChipComponent from '../data/v-shiny-chip-component.vue';
import VShinyFormComponent from '../data/v-shiny-form-component.vue';
import VShinyJsonComponent from '../data/v-shiny-json-component.vue';
import VShinyListComponent from '../data/v-shiny-list-component.vue';
import VShinyOrganizationComponent from '../data/v-shiny-organization-component.vue';
import VShinyTableComponent from '../data/v-shiny-table-component.vue';
import VShinyTimelineComponent from '../data/v-shiny-timeline-component.vue';
import VShinyTreeComponent from '../data/v-shiny-tree-component.vue';
// Dataviz
import VShinyAreaChartComponent from '../dataviz/v-shiny-area-chart-component.vue';
import VShinyBarChartComponent from '../dataviz/v-shiny-bar-chart-component.vue';
import VShinyDonutChartComponent from '../dataviz/v-shiny-donut-chart-component.vue';
import VShinyGeoMapComponent from '../dataviz/v-shiny-geo-map-component.vue';
import VShinyLineChartComponent from '../dataviz/v-shiny-line-chart-component.vue';
import VShinyPieChartComponent from '../dataviz/v-shiny-pie-chart-component.vue';
import VShinyRadarChartComponent from '../dataviz/v-shiny-radar-chart-component.vue';
import VShinyRatingComponent from '../dataviz/v-shiny-rating-component.vue';
import VShinySankeyComponent from '../dataviz/v-shiny-sankey-component.vue';
import VShinySparkLineComponent from '../dataviz/v-shiny-spark-line-component.vue';
import VShinyStatusComponent from '../dataviz/v-shiny-status-component.vue';
// Feedback
import VShinyAlertComponent from '../feedback/v-shiny-alert-component.vue';
// Input
import VShinySliderComponent from '../input/v-shiny-slider-component.vue';
// Live
import VShinyProgressBarComponent from '../live/v-shiny-progress-bar-component.vue';
// Media
import VShinyPdfComponent from '../media/v-shiny-pdf-component.vue';
import VShinyPhotoComponent from '../media/v-shiny-photo-component.vue';
import VShinyRssComponent from '../media/v-shiny-rss-component.vue';
import VShinyVideoComponent from '../media/v-shiny-video-component.vue';
import VShinyYoutubeComponent from '../media/v-shiny-youtube-component.vue';
// Text
import VShinyFigletComponent from '../text/v-shiny-figlet-component.vue';
import VShinyParagraphComponent from '../text/v-shiny-paragraph-component.vue';
import VShinyTextPathComponent from '../text/v-shiny-text-path-component.vue';
import VShinyTitleComponent from '../text/v-shiny-title-component.vue';

const props = defineProps<{
  data: ShinyContainer
}>()

const componentMap: Record<string, any> = {
  // Core
  ShinyError: VShinyErrorComponent,
  // Data
  ShinyCardComponent: VShinyCardComponent,
  ShinyChip: VShinyChipComponent,
  ShinyForm: VShinyFormComponent,
  ShinyJson: VShinyJsonComponent,
  ShinyList: VShinyListComponent,
  ShinyOrganization: VShinyOrganizationComponent,
  ShinyTable: VShinyTableComponent,
  ShinyTimeline: VShinyTimelineComponent,
  ShinyTree: VShinyTreeComponent,
  // Dataviz
  ShinyAreaChart: VShinyAreaChartComponent,
  ShinyBarChart: VShinyBarChartComponent,
  ShinyDonutChart: VShinyDonutChartComponent,
  ShinyGeoMap: VShinyGeoMapComponent,
  ShinyLineChart: VShinyLineChartComponent,
  ShinyPieChart: VShinyPieChartComponent,
  ShinyRadarChart: VShinyRadarChartComponent,
  ShinyRadar2: VShinyRadar2Component,
  ShinyRating: VShinyRatingComponent,
  ShinySankey: VShinySankeyComponent,
  ShinySparkline: VShinySparkLineComponent,
  ShinyStatus: VShinyStatusComponent,
  // Feedback
  ShinyAlert: VShinyAlertComponent,
  // Input
  ShinySlider: VShinySliderComponent,
  // Live
  ShinyProgressBar: VShinyProgressBarComponent,
  // Media
  ShinyPdfComponent: VShinyPdfComponent,
  ShinyPhoto: VShinyPhotoComponent,
  ShinyRss: VShinyRssComponent,
  ShinyVideo: VShinyVideoComponent,
  ShinyYoutube: VShinyYoutubeComponent,
  // Text
  ShinyFiglet: VShinyFigletComponent,
  ShinyParagraph: VShinyParagraphComponent,
  ShinyTextPath: VShinyTextPathComponent,
  ShinyTitle: VShinyTitleComponent,
};

const resolveComponent = (componentType: string) => {
  const component = componentMap[componentType];
  if (!component) {
    console.warn(`Component not found for type: ${componentType}`);
  }
  return component || 'div'; // Fallback to a div or a generic error component
};
</script>

<style scoped>
.shiny-container {
  border: 1px solid var(--assistant-accent);
  padding: 10px;
  margin: 10px 0;
  border-radius: 8px;
  background-color: var(--general-surface);
}

.container-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}

.container-content {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.placeholder-component {
  border: 1px dashed var(--general-text);
  padding: 10px;
  background-color: var(--general-bg);
  color: var(--general-text);
  min-width: 150px;
  text-align: center;
}
</style>