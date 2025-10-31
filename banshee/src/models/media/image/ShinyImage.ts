import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyImage extends ShinyBlock {
  title?: string;
  url: string;
  alt?: string;
}
