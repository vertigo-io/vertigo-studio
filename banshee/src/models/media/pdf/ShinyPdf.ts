import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyPdf extends ShinyBlock {

  title?: string;
  pdfPath: string;
  initialPage: number;
  height: string;
}
