import { ShinyComponent } from '../ShinyComponent';

export class ShinyFiglet implements ShinyComponent {
  text: string;
  type: string = 'figlet';

  constructor(text: string) {
    this.text = text;
  }
}
