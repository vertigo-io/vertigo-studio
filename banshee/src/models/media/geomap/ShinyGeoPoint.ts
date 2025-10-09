export class ShinyGeoPoint {
  latitude: number;
  longitude: number;
  label?: string;

  constructor(latitude: number, longitude: number, label?: string) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.label = label;
  }
}
