package model;

import core.Cfg;
import core.ICustomGenerator;
import core.MyGraph;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.ui.view.Viewer;

public class ModelFile {

    private final MyGraph previewGraph;

    public ModelFile() {
        this.previewGraph = new MyGraph(false);
    }

    public void importDOTFile(String filename) {
        try {
            Cfg.getInstance().getGraph().importDOTFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(ModelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MyGraph getPreviewGraph() {
        return previewGraph;
    }
    
    public void generateGraph(ICustomGenerator generator, int nbVertex, int nbChips) {
        try {
            previewGraph.setGraph(generator.generate(nbVertex, nbChips), false);
        } catch (IOException ex) {
            Logger.getLogger(ModelFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Viewer getViewer() {
        return previewGraph.getViewer();
    }
}
