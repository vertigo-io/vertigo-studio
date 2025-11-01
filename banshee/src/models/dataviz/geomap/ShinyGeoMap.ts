import { ShinyBlock } from '../../ShinyBlock';
import { ShinyGeoPoint } from './ShinyGeoPoint';

export interface ShinyGeoMap extends ShinyBlock {
  title: string;
  geoPoints: ShinyGeoPoint[];
}
