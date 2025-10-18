import { ShinyModel } from '../../ShinyModel';

export interface ShinyPhoto extends ShinyModel {
  title?: string;
  url: string;
  alt?: string;
}
