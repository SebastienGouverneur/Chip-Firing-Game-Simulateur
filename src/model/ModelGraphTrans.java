package model;

import core.Cfg;
import java.util.Observable;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

public class ModelGraphTrans extends Observable {

    public void reset() {
        Cfg.getInstance().getGraphTrans().clear();
    }  
    
    public void pump() {
        Cfg.getInstance().getGraphTrans().pump();
    }

    public void createViewGraph() {
        Cfg.getInstance().getGraphTrans().createViewGraph();
    }

    public Viewer getViewer() {
        return Cfg.getInstance().getGraphTrans().getViewer();
    }

    public void addConfig(String configFrom, String configTo) {
        Node from = Cfg.getInstance().getGraphTrans().addNode(configFrom);
        Node to = Cfg.getInstance().getGraphTrans().addNode(configTo);

        Edge e = Cfg.getInstance().getGraphTrans().addEdge(configFrom + configTo, from, to, true);

        from.addAttribute("ui.label", from.getId());
        to.addAttribute("ui.label", to.getId());

        setChanged();
        notifyObservers(this);
        clearChanged();
    }
    
}