import { BansheeRole } from './BansheeRole';
import { ShinyComponent } from '../../ShinyComponent';

export interface BansheeMessage {
  id: string;
  role: BansheeRole;
  content?: string; // For plain text messages
  component?: ShinyComponent; // For UI components
  cssClass: string; // To maintain current styling
}
