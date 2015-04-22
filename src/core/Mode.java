package core;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Mode {
    private int mode; /* 1: parallele - 2: sequentielle - 3: asynchrone */
    
    public Mode () {
        mode = 1;
    }
    
    public Mode (int mode) {
        this.mode = mode;
    }
    
    public Graph iter (Graph graph) {
        for (Node node : graph) {
            /* algo */
        }
        
        return graph;
    }
}