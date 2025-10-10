import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinyPdfComponent extends ShinyComponent {
  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
}
