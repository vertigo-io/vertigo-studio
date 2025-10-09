import { ShinyComponent } from './ShinyComponent';

export class ShinyError implements ShinyComponent {
  text: string;
  type: string = 'error';

  constructor(text: string) {
    this.text = text;
  }
}
