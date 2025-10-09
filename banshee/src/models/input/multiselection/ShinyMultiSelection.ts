import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyMultiSelection implements ShinyComponent {
  title: string;
  options: string[];
  selectedIndices: Set<number>;
  type: string = 'multiSelection';

  constructor(title: string, options: string[], selectedIndices: Set<number>) {
    this.title = title;
    this.options = options;
    this.selectedIndices = selectedIndices;
  }

  getSelectedOptions(): string[] {
    return Array.from(this.selectedIndices)
      .sort((a, b) => a - b)
      .map(index => this.options[index]);
  }
}
