import VShinyBoardComponent from './data/board/v-shiny-board-component.vue';
import VShinyContainerComponent from './core/v-shiny-container-component.vue';
import VShinyErrorComponent from './core/v-shiny-error-component.vue';
import VShinyCardComponent from './data/v-shiny-card-component.vue';
import VShinyChipComponent from './text/v-shiny-chip-component.vue';
import VShinyFormComponent from './form/v-shiny-form-component.vue';
import VShinyJsonComponent from './data/v-shiny-json-component.vue';
import VShinyListComponent from './data/v-shiny-list-component.vue';
import VShinyOrganizationComponent from './dataviz/v-shiny-organization-component.vue';
import VShinyTableComponent from './data/v-shiny-table-component.vue';
import VShinyTimelineComponent from './dataviz/v-shiny-timeline-component.vue';
import VShinyTreeComponent from './data/v-shiny-tree-component.vue';
import VShinyAreaChartComponent from './dataviz/v-shiny-area-chart-component.vue';
import VShinyBarChartComponent from './dataviz/v-shiny-bar-chart-component.vue';
import VShinyDonutChartComponent from './dataviz/v-shiny-donut-chart-component.vue';
import VShinyGeoMapComponent from './dataviz/v-shiny-geo-map-component.vue';
import VShinyLineChartComponent from './dataviz/v-shiny-line-chart-component.vue';
import VShinyMindMapComponent from './dataviz/v-shiny-mindmap-component.vue';
import VShinyPieChartComponent from './dataviz/v-shiny-pie-chart-component.vue';
import VShinyRadarChartComponent from './dataviz/v-shiny-radar-chart-component.vue';
import VShinyRatingComponent from './text/v-shiny-rating-component.vue';
import VShinySankeyComponent from './dataviz/v-shiny-sankey-component.vue';
import VShinySparkLineComponent from './text/v-shiny-spark-line-component.vue';
import VShinyStatusComponent from './text/v-shiny-status-component.vue';
import VShinyFlowComponent from './dataviz/v-shiny-flow-component.vue';
import VShinyAlertComponent from './core/v-shiny-alert-component.vue';
import VShinySliderComponent from './form/v-shiny-slider-component.vue';
import VShinyProgressBarComponent from './live/v-shiny-progress-bar-component.vue';
import VShinyPdfComponent from './media/v-shiny-pdf.vue';
import VShinyImageComponent from './media/v-shiny-image-component.vue';
import VShinyRssComponent from './media/v-shiny-rss-component.vue';
import VShinyVideoComponent from './media/v-shiny-video-component.vue';
import VShinyYoutubeComponent from './media/v-shiny-youtube-component.vue';
import VShinyFigletComponent from './text/v-shiny-figlet-component.vue';
import VShinyParagraphComponent from './text/v-shiny-paragraph-component.vue';
import VShinyTextPathComponent from './text/v-shiny-text-path-component.vue';
import VShinyTitleComponent from './text/v-shiny-title-component.vue';

// Table Cell Components
import VShinyStringCell from './data/table/cell/VShinyStringCell.vue';
import VShinyChipCell from './data/table/cell/VShinyChipCell.vue';
import VShinyIconCell from './data/table/cell/VShinyIconCell.vue';
import VShinyButtonCell from './data/table/cell/VShinyButtonCell.vue';
import VShinyAvatarCell from './data/table/cell/VShinyAvatarCell.vue';
import VShinyProgressBarCell from './data/table/cell/VShinyProgressBarCell.vue';
import VShinyBadgeCell from './data/table/cell/VShinyBadgeCell.vue';
import VShinyRatingCell from './data/table/cell/VShinyRatingCell.vue';

import { ShinyModel } from '../models/ShinyModel';

export class ShinyRegistry {
  private _componentMap: Record<string, any> = {};

  constructor() {
    this.register('ShinyBoard', VShinyBoardComponent);
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
    this.register('ShinyMindMap', VShinyMindMapComponent);
    this.register('ShinyAlert', VShinyAlertComponent);
    this.register('ShinySlider', VShinySliderComponent);
    this.register('ShinyPdfComponent', VShinyPdfComponent);
    this.register('ShinyImage', VShinyImageComponent);
    this.register('ShinyRss', VShinyRssComponent);
    this.register('ShinyVideo', VShinyVideoComponent);
    this.register('ShinyYoutube', VShinyYoutubeComponent);
    this.register('ShinyGeoMap', VShinyGeoMapComponent);
    this.register('ShinyCard', VShinyCardComponent);
    this.register('ShinyFiglet', VShinyFigletComponent);
    this.register('ShinyParagraph', VShinyParagraphComponent);
    this.register('ShinyTextPath', VShinyTextPathComponent);
    this.register('ShinyTitle', VShinyTitleComponent);

    // Register Table Cell Components
    this.register('ShinyStringCell', VShinyStringCell);
    this.register('ShinyChipCell', VShinyChipCell);
    this.register('ShinyIconCell', VShinyIconCell);
    this.register('ShinyButtonCell', VShinyButtonCell);
    this.register('ShinyAvatarCell', VShinyAvatarCell);
    this.register('ShinyProgressBarCell', VShinyProgressBarCell);
    this.register('ShinyBadgeCell', VShinyBadgeCell);
    this.register('ShinyRatingCell', VShinyRatingCell);
  }
 
  register(shinyType: string, componentType: any): void {
    this._componentMap[shinyType] = componentType;
  }

  resolve(shinyType: string): any {
    const component = this._componentMap[shinyType];
    return component;
  }
}
