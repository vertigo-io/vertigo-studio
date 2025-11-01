import { ShinyBlock } from '../../ShinyBlock';
import { ShinyState } from '../../ShinyState';

export class ShinyTree implements ShinyBlock {
  id?: string;
  shinyType: string = 'tree';
  state?: ShinyState;

  label: string;
  children: ShinyTree[];

  constructor(
    label: string,
    children: ShinyTree[] = [],
    id?: string,
    state?: ShinyState
  ) {
    this.label = label;
    this.children = children;
    this.id = id;
    this.state = state;
  }

  isLeaf(): boolean {
    return this.children.length === 0;
  }
}
