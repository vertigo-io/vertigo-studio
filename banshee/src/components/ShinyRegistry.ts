import VShinyBoardComponent from './data/board/v-shiny-board-component.vue';
import VShinyContainerComponent from './core/v-shiny-container-component.vue';
import VShinyErrorComponent from './feedback/v-shiny-error-component.vue';
import VShinyCardComponent from './data/v-shiny-card-component.vue';
import VShinyChipComponent from './text/v-shiny-chip-component.vue';
import VShinyFormComponent from './input/form/v-shiny-form-component.vue';
import VShinyNotificationComponent from './feedback/v-shiny-notification-component.vue';
import VShinyModalComponent from './feedback/v-shiny-modal-component.vue';
import VShinyJsonComponent from './data/v-shiny-json-component.vue';
import VShinyListComponent from './data/v-shiny-list-component.vue';
import VShinyOrganizationComponent from './dataviz/v-shiny-organization-component.vue';
import VShinyTableComponent from './data/v-shiny-table-component.vue';
import VShinyTimelineComponent from './dataviz/v-shiny-timeline-component.vue';
import VShinyTreeComponent from './data/v-shiny-tree-component.vue';
import VShinyDataGridComponent from './data/v-shiny-datagrid-component.vue';
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
import VShinyAlertComponent from './feedback/v-shiny-alert-component.vue';
import VShinySliderComponent from './input/form/v-shiny-slider-component.vue';
import VShinyMultiSelectionComponent from './input/multiselection/v-shiny-multiselection-component.vue';
import VShinyInputTextComponent from './input/text/v-shiny-inputtext-component.vue';
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
import VSpeechToTextButton from './input/VSpeechToTextButton.vue';
import VShinyDatePickerComponent from './input/v-shiny-datepicker-component.vue';
import VShinyFileUploadComponent from './input/v-shiny-fileupload-component.vue';
import VShinyCodeEditorComponent from './input/v-shiny-codeeditor-component.vue';
import VShinyAutocompleteComponent from './input/v-shiny-autocomplete-component.vue';
import VShinyRangeSliderComponent from './input/v-shiny-rangeslider-component.vue';
import VShinyToggleComponent from './input/toggle/v-shiny-toggle-component.vue';
import VShinyGridComponent from './layout/v-shiny-grid-component.vue';
import VShinyTabsComponent from './layout/v-shiny-tabs-component.vue';
import VShinyStepperComponent from './layout/v-shiny-stepper-component.vue';
import VShinyPageIconComponent from './layout/v-shiny-page-icon-component.vue';
import VShinyFullPageComponent from './layout/v-shiny-full-page-component.vue';

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
    this.register('ShinyNotification', VShinyNotificationComponent);
    this.register('ShinyModal', VShinyModalComponent);
    this.register('ShinyJson', VShinyJsonComponent);
    this.register('ShinyList', VShinyListComponent);
    this.register('ShinyTable', VShinyTableComponent);
    this.register('ShinyDataGrid', VShinyDataGridComponent);
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
    this.register('ShinyMultiSelection', VShinyMultiSelectionComponent);
    this.register('ShinyInputText', VShinyInputTextComponent);
    this.register('ShinyToggle', VShinyToggleComponent);
    this.register('ShinyProgressBar', VShinyProgressBarComponent);
    this.register('ShinyFileUpload', VShinyFileUploadComponent);
    this.register('ShinyCodeEditor', VShinyCodeEditorComponent);
    this.register('ShinyAutocomplete', VShinyAutocompleteComponent);
    this.register('ShinyRangeSlider', VShinyRangeSliderComponent);
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
    this.register('ShinyGrid', VShinyGridComponent);
    this.register('ShinyTabs', VShinyTabsComponent);
    this.register('ShinyStepper', VShinyStepperComponent);
    this.register('ShinyPage', VShinyPageIconComponent); // Default representation
    this.register('ShinyFullPage', VShinyFullPageComponent); // Full-screen representation

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
