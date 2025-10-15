public record ShinyFlowEdge(
        String id,
        String source,
        String target
) {
    public static class ShinyFlowEdgeBuilder {
        private String id;
        private String source;
        private String target;

        public ShinyFlowEdgeBuilder(String id) {
            this.id = id;
        }

        public ShinyFlowEdgeBuilder withSource(String source) {
            this.source = source;
            return this;
        }

        public ShinyFlowEdgeBuilder withTarget(String target) {
            this.target = target;
            return this;
        }

        public ShinyFlowEdge build() {
            return new ShinyFlowEdge(id, source, target);
        }
    }
}
