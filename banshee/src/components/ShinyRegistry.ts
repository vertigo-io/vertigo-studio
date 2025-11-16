import VShinyBoardComponent from './data/board/v-shiny-board-component.vue';
import VShinyContainerComponent from './block/v-shiny-container-component.vue';
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
import VShinyImageComponent from './media/v-shiny-image.vue';
import VShinyRssComponent from './media/v-shiny-rss.vue';
import VShinyVideoComponent from './media/v-shiny-video.vue';
import VShinyYoutubeComponent from './media/v-shiny-youtube.vue';
import VShinyFigletComponent from './text/v-shiny-figlet.vue';
import VShinyParagraphComponent from './text/v-shiny-paragraph.vue';
import VShinyTextPathComponent from './text/v-shiny-text-path.vue';
import VShinyTitleComponent from './text/v-shiny-title.vue';
import VSpeechToTextButton from './input/VSpeechToTextButton.vue';
import VShinyDatePickerComponent from './input/v-shiny-datepicker.vue';
import VShinyFileUploadComponent from './input/v-shiny-fileupload.vue';
import VShinyCodeEditorComponent from './input/v-shiny-codeeditor.vue';
import VShinyAutocompleteComponent from './input/v-shiny-autocomplete.vue';
import VShinyRangeSliderComponent from './input/v-shiny-rangeslider.vue';
import VShinyToggleComponent from './input/toggle/v-shiny-toggle.vue';
import VShinyGridComponent from './layout/v-shiny-grid.vue';
import VShinyTabsComponent from './layout/v-shiny-tabs.vue';
import VShinyStepperComponent from './layout/v-shiny-stepper.vue';
import VShinyPageIconComponent from './layout/v-shiny-page-icon.vue';
import VShinyFullPageComponent from './layout/v-shiny-full-page.vue';

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
    this.register('ShinyBoard', VShinyBoard);
    this.register('ShinyContainer', VShinyContainer);
    this.register('ShinyError', VShinyError);
    this.register('ShinyForm', VShinyForm);
    this.register('ShinyNotification', VShinyNotification);
    this.register('ShinyModal', VShinyModal);
    this.register('ShinyJson', VShinyJson);
    this.register('ShinyList', VShinyList);
    this.register('ShinyTable', VShinyTable);
    this.register('ShinyDataGrid', VShinyDataGrid);
    this.register('ShinyTree', VShinyTree);
    this.register('ShinyTimeline', VShinyTimeline);
    this.register('ShinyChip', VShinyChip);
    this.register('ShinyOrganization', VShinyOrganization);
    this.register('ShinyBarChart', VShinyBarChart);
    this.register('ShinyRadarChart', VShinyRadarChart);
    this.register('ShinyPieChart', VShinyPieChart);
    this.register('ShinyDonutChart', VShinyDonutChart);
    this.register('ShinyAreaChart', VShinyAreaChart);
    this.register('ShinyLineChart', VShinyLineChart);
    this.register('ShinyProgressBar', VShinyProgressBar);
    this.register('ShinyRating', VShinyRating);
    this.register('ShinySparkline', VShinySparkLine);
    this.register('ShinyStatus', VShinyStatus);
    this.register('ShinyFlow', VShinyFlow);
    this.register('ShinyMindMap', VShinyMindMap);
    this.register('ShinyAlert', VShinyAlert);
    this.register('ShinySlider', VShinySlider);
    this.register('ShinyMultiSelection', VShinyMultiSelection);
    this.register('ShinyInputText', VShinyInputText);
    this.register('ShinyToggle', VShinyToggle);
    this.register('ShinyDatePicker', VShinyDatePicker);
    this.register('ShinyFileUpload', VShinyFileUpload);
    this.register('ShinyCodeEditor', VShinyCodeEditor);
    this.register('ShinyAutocomplete', VShinyAutocomplete);
    this.register('ShinyRangeSlider', VShinyRangeSlider);
    this.register('ShinyPdfComponent', VShinyPdfComponent);
    this.register('ShinyImage', VShinyImage);
    this.register('ShinyRss', VShinyRss);
    this.register('ShinyVideo', VShinyVideo);
    this.register('ShinyYoutube', VShinyYoutube);
    this.register('ShinyGeoMap', VShinyGeoMap);
    this.register('ShinyCard', VShinyCard);
    this.register('ShinyFiglet', VShinyFiglet);
    this.register('ShinyParagraph', VShinyParagraph);
    this.register('ShinyTextPath', VShinyTextPath);
    this.register('ShinyTitle', VShinyTitle);
    this.register('ShinyGrid', VShinyGrid);
    this.register('ShinyTabs', VShinyTabs);
    this.register('ShinyStepper', VShinyStepper);
    this.register('ShinyPage', VShinyPageIcon); // Default representation
    this.register('ShinyFullPage', VShinyFullPage); // Full-screen representation

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
