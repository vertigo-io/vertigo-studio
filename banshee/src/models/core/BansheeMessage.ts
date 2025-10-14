import { BansheeRole } from './BansheeRole';
import { ShinyComponent } from '../../models/ShinyComponent';

export class BansheeMessage {
  readonly id: string;
  readonly role: BansheeRole;
  readonly content?: string;
  readonly component?: ShinyComponent;

  private constructor(id: string, role: BansheeRole, content?: string, component?: ShinyComponent) {
    this.id = id;
    this.role = role;
    this.content = content;
    this.component = component;
  }

  static fromContent(role: BansheeRole, content: string): BansheeMessage {
    const id = `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    return new BansheeMessage(id, role, content);
  }

  static fromComponent(role: BansheeRole, component: ShinyComponent): BansheeMessage {
    const id = `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    return new BansheeMessage(id, role, undefined, component);
  }
}
