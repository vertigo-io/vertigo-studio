import { ShinyModel } from '../../ShinyModel';

export interface ShinyPdfComponent extends ShinyModel {
  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
}
