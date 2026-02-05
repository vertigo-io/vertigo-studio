import dagre from '@dagrejs/dagre';
import { Graph } from '@dagrejs/graphlib';
import { ShinyFlowNode } from '../../../models/dataviz/flow/ShinyFlowNode';
import { ShinyFlowEdge } from '../../../models/dataviz/flow/ShinyFlowEdge';
import { ShinyFlowNodeType } from '../../../models/dataviz/flow/ShinyFlowNodeType'; // Updated import

export class FlowLayout {
  static layoutElements(nodes: ShinyFlowNode[], edges: ShinyFlowEdge[], direction = 'LR') {
    const dagreGraph = new Graph();
    dagreGraph.setDefaultEdgeLabel(() => ({}));
    dagreGraph.setGraph({ rankdir: direction, nodesep: 80, ranksep: 100 });

    nodes.forEach(node => {
      let nodeWidth = 120;
      let nodeHeight = 120;
      if (node.nodeType === ShinyFlowNodeType.LR || node.nodeType === ShinyFlowNodeType.LL || node.nodeType === ShinyFlowNodeType.RR) { // Updated enum usage
        nodeWidth = 180;
        nodeHeight = 60;
      }
      dagreGraph.setNode(node.id, { width: nodeWidth, height: nodeHeight });
    });
    edges.forEach(edge => dagreGraph.setEdge(edge.source, edge.target));

    dagre.layout(dagreGraph);

    nodes.forEach(node => {
      const pos = dagreGraph.node(node.id);
      let nodeWidth = 120;
      let nodeHeight = 120;
      if (node.nodeType === ShinyFlowNodeType.LR || node.nodeType === ShinyFlowNodeType.LL || node.nodeType === ShinyFlowNodeType.RR) { // Updated enum usage
        nodeWidth = 180;
        nodeHeight = 60;
      }
      node.position = { x: pos.x - nodeWidth / 2, y: pos.y - nodeHeight / 2 };
    });

    return { nodes, edges };
  }
}
