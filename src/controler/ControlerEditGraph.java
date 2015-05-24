package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ModelEditGraph;
import view.ViewEditGraph;

public class ControlerEditGraph implements ActionListener {
    private final ModelEditGraph modelEditGraph;
    private final ViewEditGraph viewEditGrap;

    ControlerEditGraph(ViewEditGraph viewEditGraph, ModelEditGraph modelEditGraph) {
        this.modelEditGraph = modelEditGraph;
        this.viewEditGrap = viewEditGraph;
        modelEditGraph.addObserver(viewEditGraph);
        
        viewEditGraph.getValidateButton().addActionListener((ActionListener) this);
        viewEditGraph.getInputFormatGraph().addActionListener((ActionListener) this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewEditGrap.getValidateButton()) {
            validateFormatGraph();
        }
    }

    private void validateFormatGraph() {
        modelEditGraph.setFormat(viewEditGrap.getInputFormatGraph().getText());
        viewEditGrap.dispose();
    }
}