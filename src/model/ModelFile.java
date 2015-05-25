package model;

import core.EnumTypeGraph;
import core.MyGraph;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelFile {

    private final MyGraph graph;
    private final MyGraph previewGraph;
    
    public ModelFile(MyGraph graph) {
        this.graph = graph;
        this.previewGraph = null;
    }
    
    public void importDOTFile (String filename) {
        try {
            graph.importDOTFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(ModelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generatePreviewGraph (int dimension, EnumTypeGraph typeGraph){
        switch (typeGraph) {
            case EMPTY_GRAPH:
            case SIMPLE_GRID:
            case TORE_GRID:
            case SIMPLE_LINKED_CYCLE:
            case DOUBLE_LINKED_CYCLE:
            case CLIQUE:
        }
    }
    
    
}
