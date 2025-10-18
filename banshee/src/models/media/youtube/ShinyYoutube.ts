import { ShinyModel } from '../../ShinyModel';

export interface ShinyYoutube extends ShinyModel {
  title?: string;
  videoId: string;
}
