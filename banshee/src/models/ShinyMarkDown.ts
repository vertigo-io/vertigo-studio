import { ShinyComponent } from '../ShinyComponent';

export class ShinyMarkDown implements ShinyComponent {
  markdownText: string;
  type: string = 'markdown';

  constructor(markdownText: string) {
    this.markdownText = markdownText;
  }
}
