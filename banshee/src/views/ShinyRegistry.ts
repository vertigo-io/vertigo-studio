import VShinyBoard from './data/board/v-shiny-board.vue';
import VShinyContainer from './block/v-shiny-container.vue';
import VShinyError from './feedback/v-shiny-error.vue';
import VShinyCard from './data/v-shiny-card.vue';
import VShinyChip from './text/v-shiny-chip.vue';
import VShinyForm from './input/form/v-shiny-form.vue';
import VShinyNotification from './feedback/v-shiny-notification.vue';
import VShinyModal from './feedback/v-shiny-modal.vue';
import VShinyJson from './data/v-shiny-json.vue';
import VShinyList from './data/v-shiny-list.vue';
import VShinyOrganization from './dataviz/v-shiny-organization.vue';
import VShinyTable from './data/v-shiny-table.vue';
import VShinyTimeline from './dataviz/v-shiny-timeline.vue';
import VShinyTree from './data/v-shiny-tree.vue';
import VShinyDataGrid from './data/v-shiny-datagrid.vue';
import VShinyAreaChart from './dataviz/v-shiny-area-chart.vue';
import VShinyBarChart from './dataviz/v-shiny-bar-chart.vue';
import VShinyDonutChart from './dataviz/v-shiny-donut-chart.vue';
import VShinyGeoMap from './dataviz/v-shiny-geo-map.vue';
import VShinyLineChart from './dataviz/v-shiny-line-chart.vue';
import VShinyMindMap from './dataviz/v-shiny-mindmap.vue';
import VShinyPieChart from './dataviz/v-shiny-pie-chart.vue';
import VShinyRadarChart from './dataviz/v-shiny-radar-chart.vue';
import VShinyRating from './text/v-shiny-rating.vue';
import VShinySankey from './dataviz/v-shiny-sankey.vue';
import VShinySparkLine from './text/v-shiny-spark-line.vue';
import VShinyStatus from './text/v-shiny-status.vue';
import VShinyFlow from './dataviz/v-shiny-flow.vue';
import VShinyAlert from './feedback/v-shiny-alert.vue';
import VShinySlider from './input/form/v-shiny-slider.vue';
import VShinyMultiSelection from './input/multiselection/v-shiny-multiselection.vue';
import VShinyInputText from './input/text/v-shiny-inputtext.vue';
import VShinyProgressBar from './live/v-shiny-progress-bar.vue';
import VShinyPdf from './media/v-shiny-pdf.vue';
import VShinyImage from './media/v-shiny-image.vue';
import VShinyRss from './media/v-shiny-rss.vue';
import VShinyVideo from './media/v-shiny-video.vue';
import VShinyYoutube from './media/v-shiny-youtube.vue';
import VShinyFiglet from './text/v-shiny-figlet.vue';
import VShinyParagraph from './text/v-shiny-paragraph.vue';
import VShinyTextPath from './text/v-shiny-text-path.vue';
import VShinyTitle from './text/v-shiny-title.vue';
import VSpeechToTextButton from './input/VSpeechToTextButton.vue';
import VShinyDatePickerComponent from './input/v-shiny-datepicker-component.vue';
import VShinyFileUploadComponent from './input/v-shiny-fileupload-component.vue';
import VShinyCodeEditorComponent from './input/v-shiny-codeeditor-component.vue';
import VShinyAutocompleteComponent from './input/v-shiny-autocomplete-component.vue';
import VShinyRangeSliderComponent from './input/v-shiny-rangeslider-component.vue';
import VShinyToggle from './input/toggle/v-shiny-toggle.vue';
import VShinyGrid from './layout/v-shiny-grid.vue';
import VShinyTabs from './layout/v-shiny-tabs.vue';
import VShinyStepper from './layout/v-shiny-stepper.vue';
import VShinyPageIcon from './layout/v-shiny-page-icon.vue';
import VShinyFullPage from './layout/v-shiny-full-page.vue';

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
    this.register('ShinyDatePicker', VShinyDatePickerComponent);
    this.register('ShinyFileUpload', VShinyFileUploadComponent);
    this.register('ShinyCodeEditor', VShinyCodeEditorComponent);
    this.register('ShinyAutocomplete', VShinyAutocompleteComponent);
    this.register('ShinyRangeSlider', VShinyRangeSliderComponent);
    this.register('ShinyPdf', VShinyPdf);
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
