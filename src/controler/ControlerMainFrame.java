package controler;

import core.Click;
import core.AddChipOp;
import core.Cfg;
import core.ConfigurationContrainer;
import core.IChipOperation;
import core.ModeSequentialBlock;
import core.MyGraph;
import core.PatternUpdate;
import core.SetChipOp;
import core.SubstractChipOp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.ModelAsynchrone;
import model.ModelEditGraph;
import model.ModelFile;
import model.ModelGraphTrans;
import model.ModelIteration;
import model.ModelKChips;
import model.ModelLogFrame;
import model.ModelMainFrame;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import view.ViewAsynchrone;
import view.ViewEditGraph;
import view.ViewGeneratorGraph;
import view.ViewGraphTrans;
import view.ViewIteration;
import view.ViewKChips;
import view.ViewLog;
import view.ViewMainFrame;

public class ControlerMainFrame implements ActionListener {

    private final ViewMainFrame viewMainFrame;
    private final ModelMainFrame modelMainFrame;

    private final ControlerIteration controlerIteration;
    private final ViewIteration viewIteration;
    private final ModelIteration modelIteration;

    private final ViewGraphTrans viewGraphTrans;
    private final ModelGraphTrans modelGraphTrans;
    private final ControlerGraphTrans controlerGraphTrans;

    private final ModelFile modelFile;
    private final ViewGeneratorGraph viewGeneratorGraph;
    private final ControlerFile controllerFile;

    private Thread compute;
    private static AtomicBoolean inProgress;

    public ControlerMainFrame(ViewMainFrame viewMainFrame, ModelMainFrame modelMainFrame) {
        this.viewMainFrame = viewMainFrame;
        this.modelMainFrame = modelMainFrame;

        inProgress = new AtomicBoolean(false);

        modelMainFrame.addObserver(viewMainFrame);
        viewMainFrame.getLogButton().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlRun().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlPause().addActionListener((ActionListener) this);
        viewMainFrame.getOptionControlForward().addActionListener((ActionListener) this);
        viewMainFrame.getValideOptionChips().addActionListener((ActionListener) this);
        viewMainFrame.getIterationModeSynchrone().addActionListener((ActionListener) this);
        viewMainFrame.getIterationModeAsynchrone().addActionListener((ActionListener) this);
        viewMainFrame.getIterationModeKChips().addActionListener((ActionListener) this);
        viewMainFrame.getInputNbChips().addActionListener((ActionListener) this);
        viewMainFrame.getValidateTime().addActionListener((ActionListener) this);
        viewMainFrame.getGraphTransButton().addActionListener((ActionListener) this);
        viewMainFrame.getSelectAllVerticesButton().addActionListener((ActionListener) this);
        viewMainFrame.getResetSelectedVerticesButton().addActionListener((ActionListener) this);
        viewMainFrame.getEditGraphButton().addActionListener((ActionListener) this);
        viewMainFrame.getOpen().addActionListener((ActionListener) this);
        viewMainFrame.getImport_().addActionListener((ActionListener) this);
        viewMainFrame.getSave().addActionListener((ActionListener) this);
        viewMainFrame.getSaveAs().addActionListener((ActionListener) this);
        viewMainFrame.getQuit().addActionListener((ActionListener) this);

        modelMainFrame.setTimeAnimation(Cfg.getTimeAnimation());
        modelMainFrame.setTimeExec(Cfg.getTimeExec());

        Cfg.getInstance().getGraph().attachViewGraph(viewMainFrame.getViewGraph());
        Cfg.getInstance().getGraph().setClickListener(new Click(modelMainFrame));

        modelIteration = new ModelIteration();
        viewIteration = new ViewIteration(modelIteration);
        controlerIteration = new ControlerIteration(viewIteration, modelIteration);

        modelGraphTrans = new ModelGraphTrans();
        viewGraphTrans = new ViewGraphTrans(modelGraphTrans);
        controlerGraphTrans = new ControlerGraphTrans(viewGraphTrans, modelGraphTrans);

        modelFile = new ModelFile();
        viewGeneratorGraph = new ViewGeneratorGraph(modelFile);
        controllerFile = new ControlerFile(this, modelFile, viewGeneratorGraph);
    }

    public void changeColorNode(String id) {
        Cfg.getInstance().getGraph().setNodeMarked(id);
    }

    public void selectedNodeForChange(String id) {
        if (Cfg.getInstance().getGraph().isNodeMarked(id)) {
            modelMainFrame.addSelectedNode(id);
        } else {
            Cfg.getInstance().getGraph().setNodeMarked(id);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewMainFrame.getLogButton()) {
            logButtonPerformed();
        }
        
        if (ae.getSource() == viewMainFrame.getOptionControlPause()) {
            optionControlPausePerformed();
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
        
        if (ae.getSource() == viewMainFrame.getIterationModeSynchrone()) {
            iterationModeSynchronePerformed();
        }
        
        if (ae.getSource() == viewMainFrame.getIterationModeAsynchrone()) {
        	iterationModeAsynchronePerformed();
        }
        
        if (ae.getSource() == viewMainFrame.getIterationModeKChips()) {
        	iterationModeKChipsPerformed();
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

        if (ae.getSource() == viewMainFrame.getOpen()) {
            openFileExplorer();
        }

        if (ae.getSource() == viewMainFrame.getImport_()) {
            openGeneratorExplorer();
        }

        if (ae.getSource() == viewMainFrame.getSave()) {
            saveCurrentCFG();
        }
        
        if (ae.getSource() == viewMainFrame.getSaveAs()) {
        	saveAsCurrentCFG();
        }
        
        if (ae.getSource() == viewMainFrame.getQuit()) {
        	quitSimulator();
        }
    }

    public void logButtonPerformed() {
        ModelLogFrame modelLogFrame = new ModelLogFrame();
        ViewLog viewLog = new ViewLog(modelLogFrame);
        ControlerLog controlerLog = new ControlerLog(viewLog, modelLogFrame);
        viewLog.setVisible(true);
    }
    
    public void optionControlPausePerformed() {
        
		interruptCompute();
    }

    
    public void optionControlRunPerformed() {
    	
    	if (Cfg.getInstance().getGraph().getNodeCount() == 0) { 
    		errorDialogMessage();
    	}
    	
    	else if (viewMainFrame.getIterationModeKChips().isSelected()) {
    		if (inProgress.get()) {
    			return;
    		}
    		
            setUpKChips();
        } 
        
        
    	else if (viewMainFrame.getIterationModeSynchrone().isSelected()) {
            if (inProgress.get()) {
                return;
            }

            if (!controlerIteration.getCurrentPattern().isValid()) {
            	invalidPatternDialogMessage();
            }
            
            compute = new Thread(new Runnable() {

                @Override
                public void run() {
                	
                    final MyGraph graph = Cfg.getInstance().getGraph();
                    StringBuilder config = new StringBuilder(graph.getNodeCount());

                    for (Node node : graph.getNodeSet()) {
                        config.append(node.getAttribute("chips"));
                    }

                    ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());

                    controlerGraphTrans.reset();
                    viewMainFrame.printLimitCycleSize(0);

                    inProgress.set(true);

                    while (!configSet.cycleDetected() && !Thread.currentThread().isInterrupted()) {
  
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
                        modelGraphTrans.addTransition(configFrom, configTo);
                    }
                    viewMainFrame.printLimitCycleSize(configSet.retrieveLimitCycleSize());
                    
                    inProgress.set(false);
                    
                }
            });
            compute.start();
        }
        
        else if (viewMainFrame.getIterationModeAsynchrone().isSelected()) {
        	if (inProgress.get())
        		return;
        	
        	iterationModeAsynchronePerformed();
        }
    }

    public void optionControlForwardPerformed() {
        if (inProgress.get()) {
            return;
        }

        if (!controlerIteration.getCurrentPattern().isValid()) {
            iterationModeSynchronePerformed();
            return;
        }

        compute = new Thread(new Runnable() {

            @Override
            public void run() {
                final MyGraph graph = Cfg.getInstance().getGraph();
                StringBuilder config = new StringBuilder(graph.getNodeCount());

                for (Node node : graph.getNodeSet()) {
                    config.append(node.getAttribute("chips"));
                }

                ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());

                controlerGraphTrans.reset();
                viewMainFrame.printLimitCycleSize(0);

                inProgress.set(true);

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
                modelGraphTrans.addTransition(configFrom, configTo);

                viewMainFrame.printLimitCycleSize(configSet.retrieveLimitCycleSize());
                inProgress.set(false);
            }
        });

        compute.start();
    }
    
    private void valideOptionChipsPerformed() {
        IChipOperation op;
        int nbChips = 0;

        if (viewMainFrame.getModeAddChips().isSelected()) {
            op = new AddChipOp();
        } else if (viewMainFrame.getModeSetChips().isSelected()) {
            op = new SetChipOp();
        } else {
            op = new SubstractChipOp();
        }

        String valueText = viewMainFrame.getInputNbChips().getText();

        try {
            nbChips = Integer.parseInt(valueText);
        } catch (NumberFormatException numberFormatException) {
            return;
        }

        modelMainFrame.computeNodesValues(nbChips, op);
    }
    
    private void setUpKChips() {
    	viewMainFrame.printLimitCycleSize(0);

        ModelKChips modelKChips = new ModelKChips();
        ViewKChips viewKChips = new ViewKChips(modelKChips);
        ControlerKChips controlerKChips = new ControlerKChips(viewKChips, modelKChips);
    }
    
    private void errorDialogMessage() {
    	JOptionPane.showMessageDialog(null, "There's no graph yet, please open a graph or import one from a template before.",
				"Iteration Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void iterationModeSynchronePerformed() {
    	viewMainFrame.getIterationModeSynchrone().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/Unicast Filled-32red.png")));
    	if(Cfg.getInstance().getGraph().getNodeCount() == 0) { 
    		errorDialogMessage();
    		viewMainFrame.getIterationModeSynchrone().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/Unicast Filled-32.png")));
    	}
    	else {
	        viewIteration.getInputPattern().setText("");
	        viewIteration.getStateTextField().setText("");
	        viewIteration.setVisible(true);
    	}
    }
    
    private void iterationModeAsynchronePerformed() {
    	viewMainFrame.printLimitCycleSize(0);
    	viewMainFrame.getIterationModeAsynchrone().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/Broadcasting Filled-32red.png")));
    	if(Cfg.getInstance().getGraph().getNodeCount() == 0) { 
    		errorDialogMessage();
    		viewMainFrame.getIterationModeAsynchrone().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/Broadcasting Filled-32.png")));
    	}
    	else {
    		System.out.println("passe ici");
    		 ModelAsynchrone modelAsynchrone = new ModelAsynchrone();
    		 ViewAsynchrone viewAsynchrone = new ViewAsynchrone(this);
    		 ViewGeneratorGraph viewGeneratorGraph = new ViewGeneratorGraph(modelAsynchrone);
    		 ControlerAsynchrone controlerAsynchrone = new ControlerAsynchrone(viewMainFrame, viewGeneratorGraph, modelAsynchrone);
    	}
    }
    
    private void iterationModeKChipsPerformed() {
    	viewMainFrame.getIterationModeKChips().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/States Filled-32red.png")));
    	if(Cfg.getInstance().getGraph().getNodeCount() == 0) { 
    		errorDialogMessage();
    		viewMainFrame.getIterationModeKChips().setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icons/States Filled-32.png")));
    	}
    	else {
    		setUpKChips();
    	}
    }


    private void validateTimeButtonPerformed() {
        double timeExec = 0;

        try {
            timeExec = Double.parseDouble(viewMainFrame.getOptionControlTime().getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }

        modelMainFrame.setTimeExec(timeExec);
        modelMainFrame.setTimeAnimation(timeExec);
    }

    public void graphTransButtonPerformed() {
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
        ModelEditGraph modelEditGraph = new ModelEditGraph();
        ViewEditGraph viewEditGraph = new ViewEditGraph(modelEditGraph);
        ControlerEditGraph controlerEditGraph = new ControlerEditGraph(viewEditGraph, modelEditGraph);
    }

    private void openFileExplorer() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(viewMainFrame.getMenu());

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            if (inProgress.get() == true) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the current simulation ?", "Close?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    compute.interrupt();
                    try {
                        compute.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControlerMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inProgress.set(false);
                } else {
                    return;
                }
            }

            resetSelectedVerticesButtonPerformed();
            modelMainFrame.importDOTFile(fc.getSelectedFile().getAbsolutePath());
            controlerIteration.reset();
            controlerGraphTrans.reset();
        }
    }

    private void openGeneratorExplorer() {
        viewGeneratorGraph.setVisible(true);
    }

    public void resetAll() {
        resetSelectedVerticesButtonPerformed();
        controlerIteration.reset();
        controlerGraphTrans.reset();
    }

    public boolean inProgess() {
        return inProgress.get();
    }

    void interruptCompute() {
    	if (inProgess() == true) {
	        compute.interrupt();
	        try {
	            compute.join();
	        } catch (InterruptedException ex) {
	            Logger.getLogger(ControlerMainFrame.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        inProgress.set(false);
	        
	        viewMainFrame.printLimitCycleSize(0);
    	}
    }

    private void saveCurrentCFG() {
    	Cfg.getInstance().saveInDirectory("/home/sebastien/Documents/saved-CFG/");     
    }
    
    private void saveAsCurrentCFG() {
    	JFileChooser fileChooser = new JFileChooser();
    	Cfg.getInstance().saveAsInJFileChooser(fileChooser);	
    }
    
    private void invalidPatternDialogMessage() {
    	int reply = JOptionPane.showConfirmDialog(null, "Current pattern is invalid or as not been set yet, do you want to choose a pattern to valid ?",
    										"Invalid Pattern",
    										JOptionPane.YES_NO_OPTION);
    	if (reply == JOptionPane.YES_OPTION) {
    		iterationModeSynchronePerformed();
    	}
    	else
    		return;
    }
    
    private void quitSimulator() {
    	int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the simulator ?", "Exit CFG-Simulator", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
            System.exit(0);
        else 
            return;
        
    }
}
