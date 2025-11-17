import ShinyBoard from './data/board/ShinyBoard.vue';
import ShinyContainer from './block/ShinyContainer.vue';
import ShinyError from './feedback/ShinyError.vue';
import ShinyCard from './data/ShinyCard.vue';
import ShinyChip from './text/ShinyChip.vue';
import ShinyForm from './input/form/ShinyForm.vue';
import ShinyNotification from './feedback/ShinyNotification.vue';
import ShinyModal from './feedback/ShinyModal.vue';
import ShinyJson from './data/ShinyJson.vue';
import ShinyList from './data/ShinyList.vue';
import ShinyOrganization from './dataviz/ShinyOrganization.vue';
import ShinyTable from './data/ShinyTable.vue';
import ShinyTimeline from './dataviz/ShinyTimeline.vue';
import ShinyTree from './data/ShinyTree.vue';
import ShinyDataGrid from './data/ShinyDataGrid.vue';
import ShinyAreaChart from './dataviz/ShinyAreaChart.vue';
import ShinyBarChart from './dataviz/ShinyBarChart.vue';
import ShinyDonutChart from './dataviz/ShinyDonutChart.vue';
import ShinyGeoMap from './dataviz/ShinyGeoMap.vue';
import ShinyLineChart from './dataviz/ShinyLineChart.vue';
import ShinyMindMap from './dataviz/ShinyMindmap.vue';
import ShinyPieChart from './dataviz/ShinyPieChart.vue';
import ShinyRadarChart from './dataviz/ShinyRadarChart.vue';
import ShinyRating from './text/ShinyRating.vue';
import ShinySankey from './dataviz/ShinySankey.vue';
import ShinySparkLine from './text/ShinySparkLine.vue';
import ShinyStatus from './text/ShinyStatus.vue';
import ShinyFlow from './dataviz/ShinyFlow.vue';
import ShinyAlert from './feedback/ShinyAlert.vue';
import ShinySlider from './input/form/ShinySlider.vue';
import ShinyMultiSelection from './input/multiselection/ShinyMultiSelection.vue';
import ShinyInputText from './input/text/ShinyInputText.vue';
import ShinyProgressBar from './live/ShinyProgressBar.vue';
import ShinyPdf from './media/ShinyPdf.vue';
import ShinyImage from './media/ShinyImage.vue';
import ShinyRss from './media/ShinyRss.vue';
import ShinyVideo from './media/ShinyVideo.vue';
import ShinyYoutube from './media/ShinyYoutube.vue';
import ShinyFiglet from './text/ShinyFiglet.vue';
import ShinyParagraph from './text/ShinyParagraph.vue';
import ShinyTextPath from './text/ShinyTextPath.vue';
import ShinyTitle from './text/ShinyTitle.vue';
import VSpeechToTextButton from './input/VSpeechToTextButton.vue';
import ShinyDatePicker from './input/ShinyDatePicker.vue';
import ShinyFileUpload from './input/ShinyFileUpload.vue';
import ShinyCodeEditor from './input/ShinyCodeEditor.vue';
import ShinyAutocomplete from './input/ShinyAutocomplete.vue';
import ShinyRangeSlider from './input/ShinyRangeSlider.vue';
import ShinyToggle from './input/toggle/ShinyToggle.vue';
import ShinyGrid from './layout/ShinyGrid.vue';
import ShinyTabs from './layout/ShinyTabs.vue';
import ShinyStepper from './layout/ShinyStepper.vue';
import ShinyPageIcon from './layout/ShinyPageIcon.vue';
import ShinyFullPage from './layout/ShinyFullPage.vue';

// Table Cell Components
import ShinyStringCell from './data/table/cell/ShinyStringCell.vue';
import ShinyChipCell from './data/table/cell/ShinyChipCell.vue';
import ShinyIconCell from './data/table/cell/ShinyIconCell.vue';
import ShinyButtonCell from './data/table/cell/ShinyButtonCell.vue';
import ShinyAvatarCell from './data/table/cell/ShinyAvatarCell.vue';
import ShinyProgressBarCell from './data/table/cell/ShinyProgressBarCell.vue';
import ShinyBadgeCell from './data/table/cell/ShinyBadgeCell.vue';
import ShinyRatingCell from './data/table/cell/ShinyRatingCell.vue';

import { ShinyModel } from '../models/ShinyModel';

export class ShinyRegistry {
  private _componentMap: Record<string, any> = {};

  constructor() {
    this.register('ShinyBoard', ShinyBoard);
    this.register('ShinyContainer', ShinyContainer);
    this.register('ShinyError', ShinyError);
    this.register('ShinyForm', ShinyForm);
    this.register('ShinyNotification', ShinyNotification);
    this.register('ShinyModal', ShinyModal);
    this.register('ShinyJson', ShinyJson);
    this.register('ShinyList', ShinyList);
    this.register('ShinyTable', ShinyTable);
    this.register('ShinyDataGrid', ShinyDataGrid);
    this.register('ShinyTree', ShinyTree);
    this.register('ShinyTimeline', ShinyTimeline);
    this.register('ShinyChip', ShinyChip);
    this.register('ShinyOrganization', ShinyOrganization);
    this.register('ShinyBarChart', ShinyBarChart);
    this.register('ShinyRadarChart', ShinyRadarChart);
    this.register('ShinyPieChart', ShinyPieChart);
    this.register('ShinyDonutChart', ShinyDonutChart);
    this.register('ShinyAreaChart', ShinyAreaChart);
    this.register('ShinyLineChart', ShinyLineChart);
    this.register('ShinyProgressBar', ShinyProgressBar);
    this.register('ShinyRating', ShinyRating);
    this.register('ShinySparkline', ShinySparkLine);
    this.register('ShinyStatus', ShinyStatus);
    this.register('ShinyFlow', ShinyFlow);
    this.register('ShinyMindMap', ShinyMindMap);
    this.register('ShinyAlert', ShinyAlert);
    this.register('ShinySlider', ShinySlider);
    this.register('ShinyMultiSelection', ShinyMultiSelection);
    this.register('ShinyInputText', ShinyInputText);
    this.register('ShinyToggle', ShinyToggle);
    this.register('ShinyDatePicker', ShinyDatePicker);
    this.register('ShinyFileUpload', ShinyFileUpload);
    this.register('ShinyCodeEditor', ShinyCodeEditor);
    this.register('ShinyAutocomplete', ShinyAutocomplete);
    this.register('ShinyRangeSlider', ShinyRangeSlider);
    this.register('ShinyPdf', ShinyPdf);
    this.register('ShinyImage', ShinyImage);
    this.register('ShinyRss', ShinyRss);
    this.register('ShinyVideo', ShinyVideo);
    this.register('ShinyYoutube', ShinyYoutube);
    this.register('ShinyGeoMap', ShinyGeoMap);
    this.register('ShinyCard', ShinyCard);
    this.register('ShinyFiglet', ShinyFiglet);
    this.register('ShinyParagraph', ShinyParagraph);
    this.register('ShinyTextPath', ShinyTextPath);
    this.register('ShinyTitle', ShinyTitle);
    this.register('ShinyGrid', ShinyGrid);
    this.register('ShinyTabs', ShinyTabs);
    this.register('ShinyStepper', ShinyStepper);
    this.register('ShinyPage', ShinyPageIcon); // Default representation
    this.register('ShinyFullPage', ShinyFullPage); // Full-screen representation

    // Register Table Cell Components
    this.register('ShinyStringCell', ShinyStringCell);
    this.register('ShinyChipCell', ShinyChipCell);
    this.register('ShinyIconCell', ShinyIconCell);
    this.register('ShinyButtonCell', ShinyButtonCell);
    this.register('ShinyAvatarCell', ShinyAvatarCell);
    this.register('ShinyProgressBarCell', ShinyProgressBarCell);
    this.register('ShinyBadgeCell', ShinyBadgeCell);
    this.register('ShinyRatingCell', ShinyRatingCell);
  }
 
  register(shinyType: string, componentType: any): void {
    this._componentMap[shinyType] = componentType;
  }

  resolve(shinyType: string): any {
    const component = this._componentMap[shinyType];
    return component;
  }
}
