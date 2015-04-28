package controler;

import java.awt.event.ActionEvent;
import model.ModelGraphTrans;
import org.graphstream.ui.view.ViewerPipe;
import view.ViewGraphTrans;

public class ControlerGraphTrans extends AbstractControler {

    private ViewGraphTrans viewGraphTrans;
    
    ControlerGraphTrans(ViewGraphTrans viewGraphTrans, ModelGraphTrans modelGraphTrans) {
        super(modelGraphTrans);
        this.viewGraphTrans = viewGraphTrans;
        modelGraphTrans.addObserver(viewGraphTrans);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void control() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
