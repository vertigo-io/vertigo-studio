import java.util.Map;

public record ShinyFlowNode(
        String id,
        String label,
        Map<String, Double> position,
        String type,
        Map<String, Object> style
) {
    public static class ShinyFlowNodeBuilder {
        private String id;
        private String label;
        private Map<String, Double> position;
        private String type;
        private Map<String, Object> style;

        public ShinyFlowNodeBuilder(String id) {
            this.id = id;
        }

        public ShinyFlowNodeBuilder withLabel(String label) {
            this.label = label;
            return this;
        }

        public ShinyFlowNodeBuilder withPosition(double x, double y) {
            this.position = Map.of("x", x, "y", y);
            return this;
        }

        public ShinyFlowNodeBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public ShinyFlowNodeBuilder withStyle(Map<String, Object> style) {
            this.style = style;
            return this;
        }

        public ShinyFlowNode build() {
            return new ShinyFlowNode(id, label, position, type, style);
        }
    }
}
