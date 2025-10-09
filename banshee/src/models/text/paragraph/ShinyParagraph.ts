import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyParagraph implements ShinyComponent {
  text: string;
  type: string = 'paragraph';

  constructor(text: string) {
    this.text = text;
  }
}
