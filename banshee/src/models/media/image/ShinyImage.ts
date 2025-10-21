import { ShinyModel } from '../../ShinyModel';

export interface ShinyImage extends ShinyModel {
  title?: string;
  url: string;
  alt?: string;
}
