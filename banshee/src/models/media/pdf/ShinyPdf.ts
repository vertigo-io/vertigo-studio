import { ShinyModel } from '../../ShinyModel';

export interface ShinyPdf extends ShinyModel {
  id: string;
  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
}
