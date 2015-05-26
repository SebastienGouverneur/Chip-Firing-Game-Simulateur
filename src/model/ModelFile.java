package model;

import core.Cfg;
import core.ICustomGenerator;
import core.MyGraph;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.ui.view.Viewer;

public class ModelFile {

    private MyGraph previewGraph;

    public ModelFile() {
        this.previewGraph = null;
    }

    public void importDOTFile(String filename) {
        try {
            Cfg.getInstance().getGraph().importDOTFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(ModelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createPreviewGraph() {
        previewGraph.createViewGraph();
    }
    
    public MyGraph getPreviewGraph() {
        return previewGraph;
    }
    
    public void generateGraph(ICustomGenerator generator, int nbVertex, int nbChips) {
        previewGraph = generator.generate(nbVertex, nbChips);
        previewGraph.createViewGraph();
    }

    public Viewer getViewer() {
        return previewGraph.getViewer();
    }
}
