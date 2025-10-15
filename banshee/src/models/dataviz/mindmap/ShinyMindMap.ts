import { ShinyComponent } from '../../ShinyComponent';

export interface MindMapNode {
  id: string;
  topic: string;
  background?: string;
  foreground?: string;
  direction: boolean;
  expanded: boolean; 
  children: MindMapNode[];
}

export interface ShinyMindMap extends ShinyComponent {
  title: string;
  rootNode: MindMapNode;
}
