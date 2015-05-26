package core;


import java.awt.Container;
import javax.swing.JPanel;

import org.graphstream.graph.implementations.SingleGraph;

public final class Cfg {

    private static Cfg instance = new Cfg();
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
        Container container = graph.getViewer().getDefaultView().getParent();
        Click clickListener = graph.getClickListener();

        graph = graphTemp;

        graph.setClickListener(clickListener);

        container.removeAll();
        container.add(this.graph.getViewer().addDefaultView(false));
        graph.startPump();
        graph.initGraphAttributes();
        
        container.revalidate();
        graphTrans.clear();
    }


    public void setGraphTrans(MyGraph graphTrans) {
        this.graphTrans = graphTrans;
    }
}
