import { ShinyComponent } from '../ShinyComponent';

export class ShinyTextPath implements ShinyComponent {
  path: string;
  separator?: string;
  type: string = 'textPath';

  constructor(path: string, separator?: string) {
    this.path = path;
    this.separator = separator;
  }
}
