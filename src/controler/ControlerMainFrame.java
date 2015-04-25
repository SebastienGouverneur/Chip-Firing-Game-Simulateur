package controler;

import core.ModeSequentialBlock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AbstractModel;
import model.ModelLogFrame;
import model.ModelMainFrame;
import org.graphstream.ui.view.ViewerPipe;
import view.ViewLog;
import view.ViewMainFrame;

public class ControlerMainFrame extends AbstractControler {

    private ViewMainFrame viewMainFrame;

    public ControlerMainFrame(AbstractModel model) {
        super(model);
    }

    public ControlerMainFrame(ViewMainFrame viewMainFrame, ModelMainFrame modelMainFrame) {
        super(modelMainFrame);
        this.viewMainFrame = viewMainFrame;

        modelMainFrame.addObserver(viewMainFrame);
        viewMainFrame.getLogButton().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlRun().addActionListener((ActionListener) this);

        final ViewerPipe fromViewer;
        fromViewer = viewMainFrame.getViewer().newViewerPipe();
        fromViewer.addViewerListener(new Click(modelMainFrame));
        fromViewer.addSink(model.getGraph());

        Thread pu = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        fromViewer.blockingPump();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ViewMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        pu.start();

    }

    public void changeColorNode(String id) {
        model.setAttribute(id, "ui.class", "marked");
    }

    public void selectedNodeForChange(String id) {
        if (model.getGraph().getNode(id).getAttribute("ui.class") == "marked") {
            model.addSelectedNode(id);
        } else {
            model.setAttribute(id, "ui.class", "marked");
        }
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
        if (ae.getSource() == viewMainFrame.getLogButton()) {
            ModelLogFrame modelLogFrame = new ModelLogFrame();
            ViewLog viewLog = new ViewLog(modelLogFrame);
            ControlerLog controlerLog = new ControlerLog(viewLog, modelLogFrame);
            viewLog.setVisible(true);
        }

        if (ae.getSource() == viewMainFrame.getOptionControlRun()) {

            Thread compute = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        ((ModelMainFrame) model).execute(new ModeSequentialBlock());
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControlerMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            
            compute.start();
        }
    }
}
