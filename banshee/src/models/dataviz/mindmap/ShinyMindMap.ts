import { ShinyModel } from '../../ShinyModel';

export interface MindMapNode {
  id: string;
  topic: string;
  background?: string;
  foreground?: string;
  direction: boolean;
  expanded: boolean; 
  children: MindMapNode[];
}

export interface ShinyMindMap extends ShinyModel {
  title: string;
  rootNode: MindMapNode;
}
