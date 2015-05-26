package core;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Cfg {
    
    private static Cfg instance = null;
    private static MyGraph graph = null;
    private static MyGraph graphTrans = null;
    
        // Lazy Initialization (If required then only)
    public static Cfg getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (Cfg.class) {
                if (instance == null) {
                    instance = new Cfg();
                }
            }
        }

        return instance;
    }
    
    private Cfg () {
        graph = new MyGraph ();
        graphTrans = new MyGraph(new SingleGraph("graph_trans", false, true));
    }
    
    public MyGraph getGraph () {
        return graph;
    }
    
    public MyGraph getGraphTrans () {
        return graphTrans;
    }

    public void setGraph(MyGraph graph) {
        graph.clear();
        Cfg.graph = graph;
        graphTrans.clear ();
    }
    
    public void setGraphTrans (MyGraph graphTrans) {
        Cfg.graphTrans = graphTrans;
    }
}
