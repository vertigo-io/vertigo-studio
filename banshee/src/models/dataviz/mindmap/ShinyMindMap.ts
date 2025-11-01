import { ShinyBlock } from '../../ShinyBlock';

export interface MindMapNode {
  id: string;
  topic: string;
  background?: string;
  foreground?: string;
  direction: boolean;
  expanded: boolean; 
  children: MindMapNode[];
}

export interface ShinyMindMap extends ShinyBlock {
  title: string;
  rootNode: MindMapNode;
}
