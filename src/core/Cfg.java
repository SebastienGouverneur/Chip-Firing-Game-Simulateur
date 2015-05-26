package core;

import org.graphstream.graph.implementations.SingleGraph;

public class Cfg {
    
    private static final Cfg instance = new Cfg();
    private MyGraph graph = null;
    private MyGraph graphTrans = null;
    
    public static Cfg getInstance() {
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
        this.graph = graph;
        graphTrans.clear ();
    }
    
    public void setGraphTrans (MyGraph graphTrans) {
        this.graphTrans = graphTrans;
    }
}
