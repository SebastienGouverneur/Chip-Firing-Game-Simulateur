package controler;

import java.awt.event.ActionEvent;
import model.AbstractModel;
import model.ModelLogFrame;
import view.ViewLog;

public class ControlerLog extends AbstractControler {

    public ControlerLog(AbstractModel model) {
        super(model);
    }

    ControlerLog(ViewLog viewLog, ModelLogFrame modelLogFrame) {
        super(modelLogFrame);
        modelLogFrame.addObserver(viewLog);
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