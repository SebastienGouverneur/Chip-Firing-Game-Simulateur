package controler;

import core.AddChipOp;
import core.ConfigurationContrainer;
import core.IChipOperation;
import core.ModeSequentialBlock;
import core.MyGraph;
import core.PatternUpdate;
import core.SetChipOp;
import core.SubstractChipOp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ModelEditGraph;
import model.ModelGraphTrans;
import model.ModelIteration;
import model.ModelLogFrame;
import model.ModelMainFrame;
import org.graphstream.graph.Node;
import view.ViewEditGraph;
import view.ViewGraphTrans;
import view.ViewIteration;
import view.ViewLog;
import view.ViewMainFrame;

public class ControlerMainFrame  implements ActionListener {
    
    private final ViewMainFrame viewMainFrame;
    private final ModelMainFrame modelMainFrame;
    
    private final ControlerIteration controlerIteration;
    private final ViewIteration viewIteration;
    private final ModelIteration modelIteration;

    private final ViewGraphTrans viewGraphTrans;
    private final ModelGraphTrans modelGraphTrans;
    private final ControlerGraphTrans controlerGraphTrans;

    private final Thread checkUpdateGraph;
    private Thread compute;

    private static AtomicBoolean inProgess;

    public ControlerMainFrame(ViewMainFrame viewMainFrame, final ModelMainFrame modelMainFrame) {
        this.viewMainFrame  = viewMainFrame;
        this.modelMainFrame = modelMainFrame;
        
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
        viewMainFrame.getEditGraphButton().addActionListener((ActionListener) this);  
        
        modelMainFrame.createViewGraph ();
        modelMainFrame.getFromViewer().addViewerListener(new Click(modelMainFrame));
//        modelMainFrame.getFromViewer().addViewerListener(new Mouse(modelMainFrame));

        modelIteration = new ModelIteration(modelMainFrame.getGraph());
        viewIteration = new ViewIteration(modelIteration);
        controlerIteration = new ControlerIteration(viewIteration, modelIteration);

        modelGraphTrans = new ModelGraphTrans();
        viewGraphTrans = new ViewGraphTrans(modelGraphTrans);
        controlerGraphTrans = new ControlerGraphTrans(viewGraphTrans, modelGraphTrans);

        modelMainFrame.setTimeAnimation(1000);
        modelMainFrame.setTimeExec(1000);
        
        checkUpdateGraph = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        modelMainFrame.getFromViewer().blockingPump();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ViewMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        checkUpdateGraph.start();
    }

    public void changeColorNode(String id) {
        modelMainFrame.getGraph().setNodeMarked(id);
    }

    public void selectedNodeForChange(String id) {
        if (modelMainFrame.getGraph().isNodeMarked(id)) {
            modelMainFrame.addSelectedNode(id);
        } else {
            modelMainFrame.getGraph().setNodeMarked(id);
        }
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

        if (ae.getSource() == viewMainFrame.getEditGraphButton()) {
            editGraphButtonPerformed();
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
                final MyGraph graph = modelMainFrame.getGraph();
                StringBuilder config = new StringBuilder(graph.getNodeCount());

                for (Node node : graph.getNodeSet()) {
                    config.append(node.getAttribute("chips"));
                }

                ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());

                modelGraphTrans.reset();
                viewMainFrame.printLimitCycleSize(0);

                inProgess.set(true);
                
                boolean noCycleDetected = true;
                
                while (!configSet.cycleDetected()) {
                    PatternUpdate p = controlerIteration.getCurrentPattern();
                    String configFrom = configSet.getLastConfig();

                    modelMainFrame.execute(
                            new ModeSequentialBlock(
                                    p,
                                    configSet,
                                    modelMainFrame.getTimeExec(),
                                    modelMainFrame.getTimeAnimation()
                            )
                    );

                    String configTo = configSet.getLastConfig();
                    System.err.println(configFrom + " -> " + configTo);
                    modelGraphTrans.addConfig(configFrom, configTo);
                }

                viewMainFrame.printLimitCycleSize(configSet.retrieveLimitCycleSize());
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

            @Override
            public void run() {
                final MyGraph graph = modelMainFrame.getGraph();
                StringBuilder config = new StringBuilder(graph.getNodeCount());

                for (Node node : graph.getNodeSet()) {
                    config.append(node.getAttribute("chips"));
                }

                ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());

                PatternUpdate p = controlerIteration.getCurrentPattern();
                inProgess.set(true);

                modelMainFrame.execute(
                        new ModeSequentialBlock(
                                p,
                                configSet,
                                modelMainFrame.getTimeExec(),
                                modelMainFrame.getTimeAnimation()
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
        } else if (viewMainFrame.getModeSetChips().isSelected()) {
            op = new SetChipOp();
        } else {
            op = new SubstractChipOp();
        }

        int nbChips = Integer.parseInt(viewMainFrame.getInputNbChips().getText());
        modelMainFrame.computeNodesValues(nbChips, op);
    }

    private void iterationButtonPerformed() {
        viewIteration.getInputPattern().setText("");
        viewIteration.getStateTextField().setText("");
        viewIteration.setVisible(true);
    }

    private void validateTimeButtonPerformed() {
        double timeExec = Double.parseDouble(viewMainFrame.getOptionControlTime().getText());
        modelMainFrame.setTimeExec(timeExec);
        modelMainFrame.setTimeAnimation(timeExec);
    }

    private void graphTransButtonPerformed() {
        viewGraphTrans.setVisible(true);
    }

    private void selectAllVerticesButtonPerformed() {
        for (Node node : modelMainFrame.getNodeSet()) {
            modelMainFrame.setSelectedNode(node.getId());
        }
    }

    private void resetSelectedVerticesButtonPerformed() {
        for (String nodeId : modelMainFrame.getSelectedNode()) {
            modelMainFrame.setUnselectedNode(nodeId);
        }
    }

    private void editGraphButtonPerformed() {
        ModelEditGraph modelEditGraph          = new ModelEditGraph(modelMainFrame.getGraph());
        ViewEditGraph viewEditGraph            = new ViewEditGraph(modelEditGraph);
        ControllerEditGraph controlerEditGraph = new ControllerEditGraph(viewEditGraph, modelEditGraph);
    }

    public void start() {
        viewMainFrame.setVisible(true);
    }
}
