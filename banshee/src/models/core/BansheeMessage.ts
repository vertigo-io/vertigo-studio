import { BansheeRole } from './BansheeRole';
import { ShinyElement } from '../ShinyElement';
import { ShinyBlock } from '../ShinyBlock';
import { ShinyLayout } from '../ShinyLayout';

export class BansheeMessage {
  readonly id: string;
  readonly role: BansheeRole;
  component: ShinyElement | ShinyBlock | ShinyLayout;

  constructor( role: BansheeRole, component: ShinyElement | ShinyBlock | ShinyLayout) {
    this.id = `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    this.role = role;
    this.component = component;
  }
}
