import { ShinyComponent } from '../ShinyComponent';

export class ShinyTree implements ShinyComponent {
  label: string;
  children: ShinyTree[];
  type: string = 'tree';

  constructor(label: string, children: ShinyTree[] = []) {
    this.label = label;
    this.children = children;
  }

  isLeaf(): boolean {
    return this.children.length === 0;
  }
}
