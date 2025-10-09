import { ShinyComponent } from '../ShinyComponent';

export class ShinyPhoto implements ShinyComponent {
  title?: string;
  url: string;
  alt?: string;
  type: string = 'photo';

  constructor(title: string, url: string, alt: string) {
    this.title = title;
    this.url = url;
    this.alt = alt;
  }
}
