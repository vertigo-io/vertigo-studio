import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyGeoPoint } from './ShinyGeoPoint';

export interface ShinyGeoMap extends ShinyComponent {
  title: string;
  geoPoints: ShinyGeoPoint[];
}
