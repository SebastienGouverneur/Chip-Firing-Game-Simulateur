package core;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.graph.implementations.SingleGraph;

public final class Cfg {

    private static final Cfg instance = new Cfg();
    private MyGraph graph = null;
    private MyGraph graphTrans = null;

    // Lazy Initialization (If required then only)
    public static Cfg getInstance() {
        return instance;
    }

    private Cfg() {
        graph = new MyGraph(true);
        graphTrans = new MyGraph(new SingleGraph("graph_trans", false, true));
    }

    public MyGraph getGraph() {
        return graph;
    }

    public MyGraph getGraphTrans() {
        return graphTrans;
    }

    public void setGraph(MyGraph graphTemp) {
        
        try {
            graph.setGraph(graphTemp, true);
            graphTrans.clear();
        } catch (IOException ex) {
            Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
