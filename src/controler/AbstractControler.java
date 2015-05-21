package controler;

import java.awt.Frame;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import model.AbstractModel;
import org.graphstream.graph.Graph;

public abstract class AbstractControler implements ActionListener {

    protected AbstractModel model;
    
    public AbstractControler(AbstractModel model) {
        this.model = model;
    }

    public Graph getGraph() {
        return model.getGraph();
    }

    public Graph setGraph(Graph g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract void reset ();

    public abstract void control();
    
    public void addActionListener (AbstractButton button, Frame view)
    {
        button.addActionListener((ActionListener)this);
    }
}