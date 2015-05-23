package model;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class ModelGraphTrans extends AbstractModel {

    public ModelGraphTrans() {
        this.graph = new SingleGraph("graph_trans", false, true);

        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('view/graph.css')");
    }

    public void addConfig(String configFrom, String configTo) {
        Node from = graph.addNode(configFrom);
        Node to = graph.addNode(configTo);
        
        Edge e = graph.addEdge(configFrom + configTo, from, to, true);
        
        from.addAttribute("ui.label", from.getId());
        to.addAttribute("ui.label", from.getId());
        
        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        this.graph.clear();

        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('view/graph.css')");
    }
}
