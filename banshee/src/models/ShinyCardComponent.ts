import { ShinyComponent } from './ShinyComponent';
import { ShinyCardFormat } from './ShinyCardFormat';

export class ShinyCardComponent implements ShinyComponent {
  title: string;
  subtitle?: string;
  description?: string;
  imageUrl?: string;
  imageAlt?: string;
  link?: string;
  icon?: string;
  badgeLabel?: string;
  badgeColor?: string;
  format: ShinyCardFormat;
  type: string = 'card';

  constructor(
    title: string,
    subtitle: string,
    description: string,
    imageUrl: string,
    imageAlt: string,
    link: string,
    icon: string,
    badgeLabel: string,
    badgeColor: string,
    format: ShinyCardFormat
  ) {
    this.title = title;
    this.subtitle = subtitle;
    this.description = description;
    this.imageUrl = imageUrl;
    this.imageAlt = imageAlt;
    this.link = link;
    this.icon = icon;
    this.badgeLabel = badgeLabel;
    this.badgeColor = badgeColor;
    this.format = format;
  }
}
