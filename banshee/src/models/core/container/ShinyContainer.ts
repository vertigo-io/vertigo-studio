import { ShinyModel } from '../../ShinyModel';

export interface ShinyContainer extends ShinyModel {
  components: ShinyModel[];
}
