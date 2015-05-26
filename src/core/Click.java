package core;

import model.ModelMainFrame;
import org.graphstream.ui.view.ViewerListener;

public class Click implements ViewerListener {
    private final ModelMainFrame modelMainFrame;
    
    public Click(ModelMainFrame model) {
        this.modelMainFrame = model;
    }

    public ModelMainFrame getModel() {
        return modelMainFrame;
    }
    
    @Override
    public void viewClosed(String viewName) {

    }

    @Override
    public void buttonPushed(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buttonReleased(String id) {
        System.err.println("ooooooooooooo");
        modelMainFrame.toggleSelectedNode(id);
    }
    
}