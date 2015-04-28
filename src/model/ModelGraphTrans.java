package model;

import org.graphstream.graph.Graph;

public class ModelGraphTrans extends AbstractModel {

    public ModelGraphTrans(Graph graph) {
        this.graph = graph;
        
        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('file:////home/abdelhak/NetBeansProjects/cfg/src/view/graphCSS.css')");
    }

    public void addConfig(String configFrom, String configTo) {
        graph.addEdge(configFrom + configTo, configFrom, configTo);
        
        setChanged();
        notifyObservers(this);
        clearChanged();
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
