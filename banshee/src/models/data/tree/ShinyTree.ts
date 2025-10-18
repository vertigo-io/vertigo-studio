import { ShinyModel } from '../../ShinyModel';

export class ShinyTree implements ShinyModel {
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
