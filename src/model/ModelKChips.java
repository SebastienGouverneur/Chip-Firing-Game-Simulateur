/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import core.Cfg;
import core.ConfigurationContrainer;
import core.KChips;
import core.ModeSequentialBlock;
import core.MyGraph;
import core.PatternUpdate;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

/**
 *
 * @author jon-snow
 */
public class ModelKChips extends Observable {

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
