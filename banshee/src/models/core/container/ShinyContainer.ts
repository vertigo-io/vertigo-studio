import { ShinyComponent } from '../../ShinyComponent';

export class ShinyContainer implements ShinyComponent {
  components: ShinyComponent[];
  type: string = 'container';

  constructor(components: ShinyComponent[]) {
    this.components = components;
  }
}
