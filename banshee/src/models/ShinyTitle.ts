import { ShinyComponent } from '../ShinyComponent';

export class ShinyTitle implements ShinyComponent {
  title: string;
  level: number;
  type: string = 'title';

  constructor(title: string, level: number) {
    this.title = title;
    this.level = level;
  }
}
