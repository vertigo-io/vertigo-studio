import { ShinyModel } from '../../ShinyModel';
import { ShinyGeoPoint } from './ShinyGeoPoint';

export interface ShinyGeoMap extends ShinyModel {
  title: string;
  geoPoints: ShinyGeoPoint[];
}
