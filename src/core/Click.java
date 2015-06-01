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
    public void buttonReleased(String id) {
        modelMainFrame.toggleSelectedNode(id);
    }
    
    @Override
    public void buttonPushed(String id) {
    }

    @Override
    public void viewClosed(String viewName) {
    }


    
}