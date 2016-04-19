package core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

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

    public void saveInDirectory(String directory) {
        Date dNow = new Date();
        SimpleDateFormat ft
                = new SimpleDateFormat("E dd-MM-yy_hh:mm:ss");
        
        graph.saveGraph("/home/sebastien/Documents/saved-CFG/graph-" + ft.format(dNow)+".txt");
        graph.saveGraph("/home/sebastien/Documents/saved-CFG/graphTrans-" + ft.format(dNow)+".txt");
        
    }
    
    public void saveAsInJFileChooser(JFileChooser fileChooser) {
    	fileChooser = new JFileChooser();
   	 Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E dd-MM-yy_hh:mm:ss");
        String fileSave = "graph-"+ft.format(dNow)+".txt";
        fileChooser.setSelectedFile(new File(fileSave));
        fileChooser.showSaveDialog(null);
        graph.saveGraph(fileChooser.getSelectedFile().toString());  
    }
   
}
