package core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.graph.implementations.SingleGraph;

public final class Cfg {

    private static final Cfg instance = new Cfg();
    private MyGraph graph = null;
    private MyGraph graphTrans = null;
    private static double timeExec;
    private static double timeAnimation;

    public static double getTimeExec() {
        return timeExec;
    }

    public static void setTimeExec(double timeExec) {
        Cfg.timeExec = timeExec;
    }

    public static double getTimeAnimation() {
        return timeAnimation;
    }

    public static void setTimeAnimation(double timeAnimation) {
        Cfg.timeAnimation = timeAnimation;
    }

    public static Cfg getInstance() {
        return instance;
    }

    private Cfg() {
        graph = new MyGraph(true);
        graphTrans = new MyGraph(new SingleGraph("graph_trans", false, true));
        timeAnimation = timeExec = 1000;
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
