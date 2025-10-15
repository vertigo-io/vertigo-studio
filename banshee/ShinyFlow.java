import java.util.List;

public record ShinyFlow(
        List<ShinyFlowNode> nodes,
        List<ShinyFlowEdge> edges
) {
    public static class ShinyFlowBuilder {
        private List<ShinyFlowNode> nodes = List.of();
        private List<ShinyFlowEdge> edges = List.of();

        public ShinyFlowBuilder withNodes(List<ShinyFlowNode> nodes) {
            this.nodes = nodes;
            return this;
        }

        public ShinyFlowBuilder withEdges(List<ShinyFlowEdge> edges) {
            this.edges = edges;
            return this;
        }

        public ShinyFlow build() {
            return new ShinyFlow(nodes, edges);
        }
    }
}
