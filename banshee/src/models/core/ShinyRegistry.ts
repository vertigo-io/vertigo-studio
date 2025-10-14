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
import { ShinyComponent } from '../ShinyComponent';

export class ShinyRegistry {
  private _componentMap: Record<string, any> = {};

  constructor() {
    this.register('ShinyContainer', VShinyContainerComponent);
    this.register('ShinyError', VShinyErrorComponent);
    this.register('ShinyForm', VShinyFormComponent);
    this.register('ShinyJson', VShinyJsonComponent);
    this.register('ShinyList', VShinyListComponent);
    this.register('ShinyTable', VShinyTableComponent);
    this.register('ShinyTree', VShinyTreeComponent);
    this.register('ShinyTimeline', VShinyTimelineComponent);
    this.register('ShinyChip', VShinyChipComponent);
    this.register('ShinyOrganization', VShinyOrganizationComponent);
    this.register('ShinyBarChart', VShinyBarChartComponent);
    this.register('ShinyRadarChart', VShinyRadarChartComponent);
    this.register('ShinyPieChart', VShinyPieChartComponent);
    this.register('ShinyDonutChart', VShinyDonutChartComponent);
    this.register('ShinyAreaChart', VShinyAreaChartComponent);
    this.register('ShinyLineChart', VShinyLineChartComponent);
    this.register('ShinyProgressBar', VShinyProgressBarComponent);
    this.register('ShinyRating', VShinyRatingComponent);
    this.register('ShinySparkline', VShinySparkLineComponent);
    this.register('ShinyStatus', VShinyStatusComponent);
    this.register('ShinyFlow', VShinyFlowComponent);
    this.register('ShinyAlert', VShinyAlertComponent);
    this.register('ShinySlider', VShinySliderComponent);
    this.register('ShinyPdfComponent', VShinyPdfComponent);
    this.register('ShinyPhoto', VShinyPhotoComponent);
    this.register('ShinyRss', VShinyRssComponent);
    this.register('ShinyVideo', VShinyVideoComponent);
    this.register('ShinyYoutube', VShinyYoutubeComponent);
    this.register('ShinyGeoMap', VShinyGeoMapComponent);
    this.register('ShinyCard', VShinyCardComponent);
    this.register('ShinyFiglet', VShinyFigletComponent);
    this.register('ShinyParagraph', VShinyParagraphComponent);
    this.register('ShinyTextPath', VShinyTextPathComponent);
    this.register('ShinyTitle', VShinyTitleComponent);
  }
 
  register(modelType: string, componentType: any): void {
    this._componentMap[modelType] = componentType;
  }

  resolve(modelType: string): any {
    const component = this._componentMap[modelType];
    return component;
  }
}
