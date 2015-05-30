package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ModelLogFrame;
import view.ViewLog;

public class ControlerLog implements ActionListener {
    private final ModelLogFrame modelLogFrame;
    private final ViewLog viewLog;
    
    ControlerLog(ViewLog viewLog, ModelLogFrame modelLogFrame) {
        this.modelLogFrame = modelLogFrame;
        this.viewLog = viewLog;
        modelLogFrame.addObserver(viewLog);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}