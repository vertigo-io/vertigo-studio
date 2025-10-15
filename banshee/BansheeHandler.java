import java.util.List;
import java.util.Map;

public class BansheeHandler {

    public ShinyFlow createEcommerceFlow() {
        List<ShinyFlowNode> nodes = List.of(
                new ShinyFlowNode.ShinyFlowNodeBuilder("1")
                        .withLabel("Order Placed")
                        .withPosition(250, 50)
                        .build(),
                new ShinyFlowNode.ShinyFlowNodeBuilder("2")
                        .withLabel("Payment Processing")
                        .withPosition(250, 150)
                        .build(),
                new ShinyFlowNode.ShinyFlowNodeBuilder("3")
                        .withLabel("Order Confirmed")
                        .withPosition(250, 250)
                        .build(),
                new ShinyFlowNode.ShinyFlowNodeBuilder("4")
                        .withLabel("Order Shipped")
                        .withPosition(250, 350)
                        .build(),
                new ShinyFlowNode.ShinyFlowNodeBuilder("5")
                        .withLabel("Delivered")
                        .withPosition(250, 450)
                        .build(),
                new ShinyFlowNode.ShinyFlowNodeBuilder("6")
                        .withLabel("Invoice Generated")
                        .withPosition(500, 250)
                        .build()
        );

        List<ShinyFlowEdge> edges = List.of(
                new ShinyFlowEdge.ShinyFlowEdgeBuilder("e1-2").withSource("1").withTarget("2").build(),
                new ShinyFlowEdge.ShinyFlowEdgeBuilder("e2-3").withSource("2").withTarget("3").build(),
                new ShinyFlowEdge.ShinyFlowEdgeBuilder("e3-4").withSource("3").withTarget("4").build(),
                new ShinyFlowEdge.ShinyFlowEdgeBuilder("e4-5").withSource("4").withTarget("5").build(),
                new ShinyFlowEdge.ShinyFlowEdgeBuilder("e3-6").withSource("3").withTarget("6").build()
        );

        return new ShinyFlow.ShinyFlowBuilder()
                .withNodes(nodes)
                .withEdges(edges)
                .build();
    }
}
