import { ShinyModel } from '../../ShinyModel';

export interface ShinyPdf extends ShinyModel {

  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
}
