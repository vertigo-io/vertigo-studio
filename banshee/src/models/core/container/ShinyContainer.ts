import { ShinyComponent } from '../../ShinyComponent';

export interface ShinyContainer extends ShinyComponent {
  components: ShinyComponent[];
}
