package controler;

import core.AddChipOp;
import core.ConfigurationContrainer;
import core.IChipOperation;
import core.ModeSequentialBlock;
import core.PatternUpdate;
import core.SetChipOp;
import core.SubstractChipOp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ModelGraphTrans;
import model.ModelIteration;
import model.ModelLogFrame;
import model.ModelMainFrame;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.ViewerPipe;
import view.ViewGraphTrans;
import view.ViewIteration;
import view.ViewLog;
import view.ViewMainFrame;

public class ControlerMainFrame extends AbstractControler {

    private final ControlerIteration controlerIteration;
    private final ViewIteration viewIteration;
    private final ModelIteration modelIteration;

    private final ViewGraphTrans viewGraphTrans;
    private final ModelGraphTrans modelGraphTrans;
    private final ControlerGraphTrans controlerGraphTrans;

    private final ViewMainFrame viewMainFrame;
    private final Thread checkUpdateGraph;
    private Thread compute;

    private static AtomicBoolean inProgess;

    public ControlerMainFrame(ViewMainFrame viewMainFrame, ModelMainFrame modelMainFrame) {
        super(modelMainFrame);
        this.viewMainFrame = viewMainFrame;
        
        inProgess = new AtomicBoolean(false);
        
        modelMainFrame.addObserver(viewMainFrame);
        viewMainFrame.getLogButton().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlRun().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlForward().addActionListener((ActionListener) this);
        viewMainFrame.getValideOptionChips().addActionListener((ActionListener) this);
        viewMainFrame.getInputNbChips().addActionListener((ActionListener) this);
        viewMainFrame.getIterationButton().addActionListener((ActionListener) this);
        viewMainFrame.getValidateTime().addActionListener((ActionListener) this);
        viewMainFrame.getGraphTransButton().addActionListener((ActionListener) this);
        viewMainFrame.getSelectAllVerticesButton().addActionListener((ActionListener) this);
        viewMainFrame.getResetSelectedVerticesButton().addActionListener((ActionListener) this);

        final ViewerPipe fromViewer;
        fromViewer = viewMainFrame.getViewer().newViewerPipe();
        fromViewer.addViewerListener(new Click(modelMainFrame));
        fromViewer.addSink(model.getGraph());

        modelIteration = new ModelIteration(model.getGraph());
        viewIteration = new ViewIteration(modelIteration);
        controlerIteration = new ControlerIteration(viewIteration, modelIteration);

        modelGraphTrans = new ModelGraphTrans(new SingleGraph("graph_trans", false, true));
        viewGraphTrans = new ViewGraphTrans(modelGraphTrans);
        controlerGraphTrans = new ControlerGraphTrans(viewGraphTrans, modelGraphTrans);

        modelMainFrame.setTimeAnimation(1000);
        modelMainFrame.setTimeExec(1000);

        checkUpdateGraph = new Thread(new Runnable() {
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
        checkUpdateGraph.start();
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
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewMainFrame.getLogButton()) {
            logButtonPerformed();
        }

        if (ae.getSource() == viewMainFrame.getOptionControlRun()) {
            optionControlRunPerformed();
        }

        if (ae.getSource() == viewMainFrame.getOptionControlForward()) {
            optionControlForwardPerformed();
        }

        if (ae.getSource() == viewMainFrame.getValideOptionChips()) {
            valideOptionChipsPerformed();
        }

        if (ae.getSource() == viewMainFrame.getIterationButton()) {
            iterationButtonPerformed();
        }

        if (ae.getSource() == viewMainFrame.getValidateTime()) {
            validateTimeButtonPerformed();
        }

        if (ae.getSource() == viewMainFrame.getGraphTransButton()) {
            graphTransButtonPerformed();
        }
        
        if (ae.getSource() == viewMainFrame.getSelectAllVerticesButton()) {
            selectAllVerticesButtonPerformed();
        }
        
        if (ae.getSource() == viewMainFrame.getResetSelectedVerticesButton()) {
            resetSelectedVerticesButtonPerformed();
        }
    }

    @Override
    public void control() {
        if (compute.isAlive()) {

        }
    }

    public void logButtonPerformed() {
        ModelLogFrame modelLogFrame = new ModelLogFrame();
        ViewLog viewLog = new ViewLog(modelLogFrame);
        ControlerLog controlerLog = new ControlerLog(viewLog, modelLogFrame);
        viewLog.setVisible(true);
    }

    public void optionControlRunPerformed() {
        if (inProgess.get()) {
            return;
        }

        if (!controlerIteration.getCurrentPattern().isValid()) {
            iterationButtonPerformed();
            return;
        }

        compute = new Thread(new Runnable() {

            @Override
            public void run() {
                ConfigurationContrainer configSet = new ConfigurationContrainer();
                inProgess.set(true);

                while (!configSet.cycleDetected()) {
                    PatternUpdate p = controlerIteration.getCurrentPattern();
                    String configFrom = configSet.getLastConfig();

                    ((ModelMainFrame) model).execute(
                            new ModeSequentialBlock(
                                    p,
                                    configSet,
                                    ((ModelMainFrame) model).getTimeExec(),
                                    ((ModelMainFrame) model).getTimeAnimation()
                            )
                    );

                    String configTo = configSet.getLastConfig();
                    modelGraphTrans.addConfig(configFrom, configTo);
                }

                inProgess.set(false);
            }
        });

        compute.start();
    }

    public void optionControlForwardPerformed() {
        if (inProgess.get()) {
            return;
        }

        if (!controlerIteration.getCurrentPattern().isValid()) {
            iterationButtonPerformed();
            return;
        }

        compute = new Thread(new Runnable() {
            ConfigurationContrainer configSet = new ConfigurationContrainer();

            @Override
            public void run() {
                PatternUpdate p = controlerIteration.getCurrentPattern();
                inProgess.set(true);
                
                ((ModelMainFrame) model).execute(
                        new ModeSequentialBlock(
                                p,
                                configSet,
                                ((ModelMainFrame) model).getTimeExec(),
                                ((ModelMainFrame) model).getTimeAnimation()
                        )
                );

                inProgess.set(false);
            }
        });

        compute.start();
    }

    private void valideOptionChipsPerformed() {
        IChipOperation op;

        if (viewMainFrame.getModeAddChips().isSelected()) {
            op = new AddChipOp();
        } else if (viewMainFrame.getModeSetChips().isSelected()){
            op = new SetChipOp();
        } else {
            op = new SubstractChipOp();
        }

        int nbChips = Integer.parseInt(viewMainFrame.getInputNbChips().getText());
        ((ModelMainFrame) model).computeNodesValues(nbChips, op);
    }

    private void iterationButtonPerformed() {
        viewIteration.getInputPattern().setText("");
        viewIteration.getStateTextField().setText("");
        viewIteration.setVisible(true);
    }

    private void validateTimeButtonPerformed() {
        double timeExec = Double.parseDouble(viewMainFrame.getOptionControlTime().getText());
        ((ModelMainFrame) model).setTimeExec(timeExec);
        ((ModelMainFrame) model).setTimeAnimation(timeExec);
    }

    private void graphTransButtonPerformed() {
        viewGraphTrans.setVisible(true);
    }

    private void selectAllVerticesButtonPerformed() {
        for (Node node : model.getGraph().getNodeSet()) {
            ((ModelMainFrame)model).setSelectedNode(node.getId());
        }
    }

    private void resetSelectedVerticesButtonPerformed() {
        for (String nodeId : ((ModelMainFrame)model).getSelectedNode()) {
            ((ModelMainFrame)model).setUnselectedNode(nodeId);
        }
    }

}
