import { ShinyComponent } from '../ShinyComponent';

export class ShinyYoutube implements ShinyComponent {
  title?: string;
  videoId: string;
  type: string = 'youtube';

  constructor(title: string, videoId: string) {
    this.title = title;
    this.videoId = videoId;
  }
}
