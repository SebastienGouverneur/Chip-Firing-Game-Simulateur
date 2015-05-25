package model;

import core.MyGraph;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelFile {

    private final MyGraph graph;

    public ModelFile(MyGraph graph) {
        this.graph = graph;
    }
    
    public void importDOTFile (String filename) {
        try {
            graph.importDOTFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(ModelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
