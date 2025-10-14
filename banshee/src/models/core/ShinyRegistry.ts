import VShinyContainerComponent from '../../components/core/v-shiny-container-component.vue';
import VShinyErrorComponent from '../../components/core/v-shiny-error-component.vue';
import VShinyCardComponent from '../../components/data/v-shiny-card-component.vue';
import VShinyChipComponent from '../../components/data/v-shiny-chip-component.vue';
import VShinyFormComponent from '../../components/data/v-shiny-form-component.vue';
import VShinyJsonComponent from '../../components/data/v-shiny-json-component.vue';
import VShinyListComponent from '../../components/data/v-shiny-list-component.vue';
import VShinyOrganizationComponent from '../../components/data/v-shiny-organization-component.vue';
import VShinyTableComponent from '../../components/data/v-shiny-table-component.vue';
import VShinyTimelineComponent from '../../components/data/v-shiny-timeline-component.vue';
import VShinyTreeComponent from '../../components/data/v-shiny-tree-component.vue';
import VShinyAreaChartComponent from '../../components/dataviz/v-shiny-area-chart-component.vue';
import VShinyBarChartComponent from '../../components/dataviz/v-shiny-bar-chart-component.vue';
import VShinyDonutChartComponent from '../../components/dataviz/v-shiny-donut-chart-component.vue';
import VShinyGeoMapComponent from '../../components/dataviz/v-shiny-geo-map-component.vue';
import VShinyLineChartComponent from '../../components/dataviz/v-shiny-line-chart-component.vue';
import VShinyPieChartComponent from '../../components/dataviz/v-shiny-pie-chart-component.vue';
import VShinyRadarChartComponent from '../../components/dataviz/v-shiny-radar-chart-component.vue';
import VShinyRadar2Component from '../../components/dataviz/v-shiny-radar2-component.vue';
import VShinyRatingComponent from '../../components/dataviz/v-shiny-rating-component.vue';
import VShinySankeyComponent from '../../components/dataviz/v-shiny-sankey-component.vue';
import VShinySparkLineComponent from '../../components/dataviz/v-shiny-spark-line-component.vue';
import VShinyStatusComponent from '../../components/dataviz/v-shiny-status-component.vue';
import VShinyFlowComponent from '../../components/dataviz/v-shiny-flow-component.vue';
import VShinyAlertComponent from '../../components/feedback/v-shiny-alert-component.vue';
import VShinySliderComponent from '../../components/input/v-shiny-slider-component.vue';
import VShinyProgressBarComponent from '../../components/live/v-shiny-progress-bar-component.vue';
import VShinyPdfComponent from '../../components/media/v-shiny-pdf-component.vue';
import VShinyPhotoComponent from '../../components/media/v-shiny-photo-component.vue';
import VShinyRssComponent from '../../components/media/v-shiny-rss-component.vue';
import VShinyVideoComponent from '../../components/media/v-shiny-video-component.vue';
import VShinyYoutubeComponent from '../../components/media/v-shiny-youtube-component.vue';
import VShinyFigletComponent from '../../components/text/v-shiny-figlet-component.vue';
import VShinyParagraphComponent from '../../components/text/v-shiny-paragraph-component.vue';
import VShinyTextPathComponent from '../../components/text/v-shiny-text-path-component.vue';
import VShinyTitleComponent from '../../components/text/v-shiny-title-component.vue';

export class ShinyRegistry {
  private componentMap: Record<string, any> = {
    // ---core
    ShinyContainer: VShinyContainerComponent,
    ShinyError: VShinyErrorComponent,
    ShinyForm: VShinyFormComponent,
    // ---data
    ShinyJson: VShinyJsonComponent,
    ShinyList: VShinyListComponent,
    ShinyTable: VShinyTableComponent,
    ShinyTree: VShinyTreeComponent,
    ShinyTimeline: VShinyTimelineComponent,
    ShinyChip: VShinyChipComponent,
    ShinyOrganization: VShinyOrganizationComponent,
    // ---dataviz
    ShinyBarChart: VShinyBarChartComponent,
    ShinyRadarChart: VShinyRadarChartComponent,
    ShinyPieChart: VShinyPieChartComponent,
    ShinyDonutChart: VShinyDonutChartComponent,
    ShinyAreaChart: VShinyAreaChartComponent,
    ShinyLineChart: VShinyLineChartComponent,

    ShinyProgressBar: VShinyProgressBarComponent,
    ShinyRating: VShinyRatingComponent,
    ShinySparkline: VShinySparkLineComponent,
    ShinyStatus: VShinyStatusComponent,
    ShinyFlow: VShinyFlowComponent,
    // ---feedback
    ShinyAlert: VShinyAlertComponent,
    // ---input
    ShinySlider: VShinySliderComponent,
    // ---media
    ShinyPdfComponent: VShinyPdfComponent,
    ShinyPhoto: VShinyPhotoComponent,
    ShinyRss: VShinyRssComponent,
    ShinyVideo: VShinyVideoComponent,
    ShinyYoutube: VShinyYoutubeComponent,
    ShinyGeoMap: VShinyGeoMapComponent,
    ShinyCard: VShinyCardComponent,
    // ---text
    ShinyFiglet: VShinyFigletComponent,
    ShinyParagraph: VShinyParagraphComponent,
    ShinyTextPath: VShinyTextPathComponent,
    ShinyTitle: VShinyTitleComponent,
  };

  resolveComponent(componentType: string): any {
    const component = this.componentMap[componentType];
    if (!component) {
      console.warn(`Component not found for type: ${componentType}`);
    }
    return component || 'div'; // Fallback to a div or a generic error component
  }
}
