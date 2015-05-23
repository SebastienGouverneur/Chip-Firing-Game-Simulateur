package model;

import core.MyGraph;
import java.util.Observable;

public class ModelEditGraph extends Observable {
    private final MyGraph graph;
    
    public ModelEditGraph(MyGraph graph) {
        this.graph = graph;
    }

    public void setFormat(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
