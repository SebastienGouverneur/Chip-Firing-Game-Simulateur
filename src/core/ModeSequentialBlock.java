package core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class ModeSequentialBlock implements IMode {

    @Override
    public void execute(Graph graph) {
        LinkedHashMap<Integer, LinkedList<String>> patternUpdate = new LinkedHashMap<>();

        int count = 0;
        for (Node node : graph) {
            LinkedList<String> patternIteration = new LinkedList<>();
            patternIteration.add(node.getId());
            patternUpdate.put(count++, patternIteration);
        }


        /* start */
        for (Map.Entry<Integer, LinkedList<String>> stepIter : patternUpdate.entrySet()) {
            int numStep = stepIter.getKey();
            LinkedList<String> parallelPattern = stepIter.getValue();

            HashMap<String, Integer> initialState = new HashMap<>(parallelPattern.size());
            for (String nodeId : parallelPattern) {
                initialState.put(nodeId, (int) graph.getNode(nodeId).getAttribute("chips"));
            }

            for (Map.Entry<String, Integer> entry : initialState.entrySet()) {
                if (entry.getValue() >= graph.getNode(entry.getKey()).getOutDegree()) {
                    for (Edge edgeOut : graph.getNode(entry.getKey()).getEachLeavingEdge()) {
                        edgeOut.getNode1().setAttribute("chips", (int) edgeOut.getNode1().getAttribute("chips") + 1);
                        edgeOut.getNode0().setAttribute("chips", (int) edgeOut.getNode0().getAttribute("chips") - 1);
                        edgeOut.setAttribute("ui.class", "actif");
                    }
                }

//                for (String parallel : parallelPattern) {
//                    for (Edge edgeOut : graph.getNode(parallel).getEachLeavingEdge()) {
//                        edgeOut.setAttribute("ui.class", "actif");
//                    }
//                }

                graph.getNode(entry.getKey()).setAttribute("ui.label", graph.getNode(entry.getKey()).getAttribute("chips"));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ModeSequentialBlock.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (String parallel : parallelPattern) {
                    for (Edge edgeOut : graph.getNode(parallel).getEachLeavingEdge()) {
                        edgeOut.setAttribute("ui.class", "");
                    }
                }

            }
        }
        
        for (Node node : graph) {
            System.out.println(node.getId() + " : " + node.getAttribute("chips"));
        }
    }
}
