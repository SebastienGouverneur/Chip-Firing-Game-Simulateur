package model;

import java.util.Observable;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import core.Cfg;
import core.MyGraph;

public class ModelAsynchrone extends Observable {

	private static final MyGraph graph = Cfg.getInstance().getGraph();

    public void addTransition(String configFrom, String configTo) {
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
