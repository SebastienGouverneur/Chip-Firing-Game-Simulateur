package model;

import core.IMode;
import java.util.HashMap;
import java.util.Map;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class ModelMainFrame extends AbstractModel {

    public ModelMainFrame() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph = new SingleGraph("Tutorial 1");

        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", "url('file:///home/abdelhak/NetBeansProjects/cfg/src/view/graphCSS.css')");

        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("AC", "A", "C", true);
        graph.addEdge("BC", "B", "C", true);
        graph.addEdge("CB", "C", "B", true);

        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
            node.addAttribute("chips", 2);
            node.addAttribute("ui.label", node.getAttribute("chips"));
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void changeColor(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");

        setChanged();
        notifyObservers(graph.getNode(id));
        clearChanged();
    }

    public void iter() {

        HashMap<String, Integer> initialState = new HashMap<>(graph.getNodeCount());

        for (Node node : graph) {
            initialState.put(node.getId(), (int) node.getAttribute("chips"));
        }

        for (Map.Entry<String, Integer> entry : initialState.entrySet()) {
            if (entry.getValue() >= graph.getNode(entry.getKey()).getOutDegree()) {
                for (Edge edgeOut : graph.getNode(entry.getKey()).getEachLeavingEdge()) {
                    edgeOut.getNode1().setAttribute("chips", (int) edgeOut.getNode1().getAttribute("chips") + 1);
                    edgeOut.getNode0().setAttribute("chips", (int) edgeOut.getNode0().getAttribute("chips") - 1);
                }
            }
        }

        for (Node node : graph) {
            System.out.println(node.getId() + " : " + node.getAttribute("chips"));
        }

        setChanged();
        notifyObservers(graph);
        clearChanged();
    }

    public void execute(IMode mode) {
        mode.execute (graph);
        
        setChanged();
        notifyObservers(graph);
        clearChanged();
    }
}
