package core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

public class ModeSequentialBlock implements IAlgorithm {

    private final PatternUpdate patternUpdate;
    private final ConfigurationContrainer configSet;
    private MyGraph graph;
    private final double time;
    private final double timeAnimation;

    public ModeSequentialBlock(PatternUpdate patternUpdate, ConfigurationContrainer configSet, double time, double timeAnimation) {
        this.patternUpdate = patternUpdate;
        this.timeAnimation = timeAnimation;
        this.configSet = configSet;
        this.time = time;
    }
    
    
    @Override
    public void terminate() {
        StringBuilder config = new StringBuilder(graph.getNodeCount());

        for (Node node : graph.getNodeSet()) {
            config.append(node.getAttribute("chips"));
            node.setAttribute("ui.label", node.getAttribute("chips").toString());
        }

        boolean isInserted = configSet.insertConfiguration(config.toString());

        try {
            Thread.sleep((long) (time));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    
    @Override
    public void init(MyGraph graph) {
        this.graph = graph;
    }
    
    @Override
    public void compute() {

        /* start */       
        for (Map.Entry<Integer, LinkedList<String>> stepIter : patternUpdate.getAllStep()) {
            int numStep = stepIter.getKey();
            LinkedList<String> parallelPattern = stepIter.getValue();

            HashMap<String, Integer> initialState = new HashMap<>(parallelPattern.size());

            for (String nodeId : parallelPattern) {
                initialState.put(nodeId, graph.getNbChipsNode(nodeId));
            }

            for (Map.Entry<String, Integer> entry : initialState.entrySet()) {
                if (entry.getValue() >= graph.getNode(entry.getKey()).getOutDegree()) {
                    for (Edge edgeOut : graph.getNode(entry.getKey()).getEachLeavingEdge()) {
                        edgeOut.setAttribute("ui.class", "marked");
                    }
                }
            }

            try {
                Thread.sleep((long) (timeAnimation));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }

            for (Edge e : graph.getEdgeSet()) {
                e.setAttribute("ui.class", "unmarked");
            }

            /* Mise a jour des tokens */
            for (Map.Entry<String, Integer> entry : initialState.entrySet()) {
                if (entry.getValue() >= graph.getNode(entry.getKey()).getOutDegree()) {
                    for (Edge edgeOut : graph.getNode(entry.getKey()).getEachLeavingEdge()) {
                        edgeOut.getNode1().setAttribute("chips", (int) edgeOut.getNode1().getAttribute("chips") + 1);
                        edgeOut.getNode1().setAttribute("ui.label", edgeOut.getNode1().getAttribute("chips").toString());
                        edgeOut.getNode0().setAttribute("chips", (int) edgeOut.getNode0().getAttribute("chips") - 1);
                        edgeOut.getNode0().setAttribute("ui.label", edgeOut.getNode0().getAttribute("chips").toString());
                    }
                }
            }
        }
    }
}