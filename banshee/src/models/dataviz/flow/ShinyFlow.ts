import { ShinyComponent } from "../../ShinyComponent";

export interface ShinyFlowNode {
    id: string;
    label: string;
    position: { x: number; y: number };
    type?: string;
    style?: any;
}

export interface ShinyFlowEdge {
    id: string;
    source: string;
    target: string;
}

export interface ShinyFlow extends ShinyComponent {
    nodes: ShinyFlowNode[];
    edges: ShinyFlowEdge[];
}
