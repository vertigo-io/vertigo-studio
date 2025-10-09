import { ShinyComponent } from '../ShinyComponent';
import { ShinyGeoPoint } from './ShinyGeoPoint';

export class ShinyGeoMap implements ShinyComponent {
  title: string;
  geoPoints: ShinyGeoPoint[];
  type: string = 'geoMap';

  constructor(title: string, geoPoints: ShinyGeoPoint[]) {
    this.title = title;
    this.geoPoints = geoPoints;
  }
}
