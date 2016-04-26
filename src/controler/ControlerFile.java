package controler;

import core.Cfg;
import core.CustomGeneratorClique;
import core.CustomGeneratorGrid;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ModelFile;
import view.ViewGeneratorGraph;

public class ControlerFile implements ActionListener, ListSelectionListener {

    private final ControlerMainFrame controlerMainFrame;
    private final ViewGeneratorGraph viewGeneratorGraph;
    private final ModelFile modelFile;
    private int nbVertex;
    private int nbChips;
    
    public ControlerFile(ControlerMainFrame controlerMainFrame, ModelFile modelOpenFile, ViewGeneratorGraph viewGeneratorGraph) {
        this.modelFile = modelOpenFile;
        this.controlerMainFrame = controlerMainFrame; 

        this.viewGeneratorGraph = viewGeneratorGraph;

        viewGeneratorGraph.getListGenerator().getSelectionModel().addListSelectionListener((ListSelectionListener) this);
        viewGeneratorGraph.getValidateGenerator().addActionListener((ActionListener) this);
        viewGeneratorGraph.getButtonCancel().addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	if (ae.getSource() == viewGeneratorGraph.getButtonCancel()) {
    		viewGeneratorGraph.dispose();
    	}
        if (ae.getSource() == viewGeneratorGraph.getValidateGenerator()) {
            if (controlerMainFrame.inProgess() == true) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the current simulation ?", "Close?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    controlerMainFrame.interruptCompute();
                } else {
                    return;
                }
            }
            
            controlerMainFrame.resetAll();
            Cfg.getInstance().setGraph(modelFile.getPreviewGraph());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {

        ListSelectionModel lsm = (ListSelectionModel) lse.getSource();
        if (lsm.getValueIsAdjusting()) {
            return;
        }
        
        if (lsm.isSelectionEmpty()) {
            System.out.println(" <none>");
        } 
        
 
        else {
            int minIndex = lsm.getMinSelectionIndex();
            viewGeneratorGraph.getPreviewGraph().removeAll();

            switch (minIndex) {
                case 0:
                    modelFile.generateGraph(
                            new CustomGeneratorGrid(
                                    false,
                                    false,
                                    false,
                                    true
                            ),
                            nbVertex,
                            nbChips
                    );
                    break;

                case 1:
                    modelFile.generateGraph(
                            new CustomGeneratorGrid(
                                    false,
                                    true,
                                    false,
                                    true
                            ),
                            nbVertex,
                            nbChips
                    );
                    break;

                case 2:
                    modelFile.generateGraph(
                            new CustomGeneratorClique(),
                            nbVertex,
                            nbChips
                    );
                    break;
    
                default:
                    break;
            }

            viewGeneratorGraph.getPreviewGraph().add(modelFile.getViewer().addDefaultView(false));
            viewGeneratorGraph.getPreviewGraph().revalidate();
        }
        
        viewGeneratorGraph.getInputAmountOfChips().getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            public void changedUpdate(javax.swing.event.DocumentEvent e){
                
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e){
            	nbVertex = Integer.parseInt(viewGeneratorGraph.getInputNumberOfVertex().getText());
                nbChips = Integer.parseInt(viewGeneratorGraph.getInputAmountOfChips().getText());  
            } 
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
               
            }
        });
        
    }
}