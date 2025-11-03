import { BansheeRole } from './BansheeRole';
import { ShinyModel } from '../ShinyModel';

export class BansheeMessage {
  readonly id: string;
  readonly role: BansheeRole;
  model: ShinyModel;

  constructor( role: BansheeRole, model: ShinyModel ) {
    this.id = `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    this.role = role;
    this.model = model;
  }
}
