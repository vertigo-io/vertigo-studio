import { BansheeRole } from './BansheeRole';
import { ShinyComponent } from '../../models/ShinyComponent';

export class BansheeMessage {
  readonly id: string;
  readonly role: BansheeRole;
  readonly component: ShinyComponent;

  constructor( role: BansheeRole, component: ShinyComponent) {
    this.id = `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    this.role = role;
    this.component = component;
  }

}
