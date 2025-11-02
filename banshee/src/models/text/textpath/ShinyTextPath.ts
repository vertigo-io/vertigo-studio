import { ShinyModel } from '../../ShinyModel';

export interface ShinyTextPath extends ShinyModel {
  path: string;
  separator?: string;
}
