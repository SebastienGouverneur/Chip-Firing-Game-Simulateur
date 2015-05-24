package model;

import core.MyGraph;
import java.util.Observable;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class ModelGraphTrans extends Observable {
    private final MyGraph graph;

    public ModelGraphTrans() {
        this.graph = new MyGraph(new SingleGraph("graph_trans", false, true));
    }

    public void reset() {
        this.graph.clear();
    }  
    
    public void pump() {
        graph.pump();
    }

    public void createViewGraph() {
        graph.createViewGraph();
    }

    public Viewer getViewer() {
        return graph.getViewer();
    }

    public void addConfig(String configFrom, String configTo) {
        Node from = graph.addNode(configFrom);
        Node to = graph.addNode(configTo);

        Edge e = graph.addEdge(configFrom + configTo, from, to, true);

        from.addAttribute("ui.label", from.getId());
        to.addAttribute("ui.label", to.getId());

        setChanged();
        notifyObservers(this);
        clearChanged();
    }
    
}