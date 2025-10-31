import { ShinyElement } from '../../../ShinyElement';
import { ShinyState } from '../../../ShinyState';

export class ShinyMultiSelection implements ShinyElement {
  id?: string;
  shinyType: string = 'multiSelection';
  state?: ShinyState;

  title: string;
  options: string[];
  selectedIndices: Set<number>;

  constructor(
    title: string,
    options: string[],
    selectedIndices: Set<number>,
    id?: string,
    state?: ShinyState
  ) {
    this.title = title;
    this.options = options;
    this.selectedIndices = selectedIndices;
    this.id = id;
    this.state = state;
  }

  getSelectedOptions(): string[] {
    return Array.from(this.selectedIndices)
      .sort((a, b) => a - b)
      .map(index => this.options[index]);
  }
}
