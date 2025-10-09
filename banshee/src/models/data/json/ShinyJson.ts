import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyJson implements ShinyComponent {
  title: string;
  json: string;
  type: string = 'json';

  constructor(title: string, json: string) {
    this.title = title;
    this.json = json;
  }
}
