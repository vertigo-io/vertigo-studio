import { ShinyComponent } from '../ShinyComponent';

export class ShinyPdfComponent implements ShinyComponent {
  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
  type: string = 'pdf';

  constructor(title: string, pdfPath: string, initialPage: number, height: string) {
    this.title = title;
    this.pdfPath = pdfPath;
    this.initialPage = initialPage;
    this.height = height;
  }
}
