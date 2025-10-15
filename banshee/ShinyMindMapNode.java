import java.util.List;

public record ShinyMindMapNode(
        String id,
        String topic,
        String background,
        String foreground,
        boolean direction,
        boolean expanded,
        List<ShinyMindMapNode> children
) {
    public static class ShinyMindMapNodeBuilder {
        private String id;
        private String topic;
        private String background;
        private String foreground;
        private boolean direction;
        private boolean expanded;
        private List<ShinyMindMapNode> children = List.of();

        public ShinyMindMapNodeBuilder(String id, String topic) {
            this.id = id;
            this.topic = topic;
        }

        public ShinyMindMapNodeBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public ShinyMindMapNodeBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public ShinyMindMapNodeBuilder withBackground(String background) {
            this.background = background;
            return this;
        }

        public ShinyMindMapNodeBuilder withForeground(String foreground) {
            this.foreground = foreground;
            return this;
        }

        public ShinyMindMapNodeBuilder withDirection(boolean direction) {
            this.direction = direction;
            return this;
        }

        public ShinyMindMapNodeBuilder withExpanded(boolean expanded) {
            this.expanded = expanded;
            return this;
        }

        public ShinyMappublic ShinyMindMapNodeBuilder withChildren(List<ShinyMindMapNode> children) {
            this.children = children;
            return this;
        }

        public ShinyMindMapNode build() {
            return new ShinyMindMapNode(
                    id,
                    topic,
                    background,
                    foreground,
                    direction,
                    expanded,
                    children
            );
        }
    }
}
