package controler;

import core.PatternUpdate;
import java.awt.event.ActionEvent;
import model.AbstractModel;
import model.ModelIteration;
import view.ViewIteration;

public class ControlerIteration extends AbstractControler {

    private ViewIteration viewIteration;

    public ControlerIteration(AbstractModel model) {
        super(model);
    }

    ControlerIteration(ViewIteration viewIteration, ModelIteration modelIteration) {
        super(modelIteration);
        this.viewIteration = viewIteration;
        modelIteration.addObserver(viewIteration);

        addActionListener(viewIteration.getButtonValidate(), viewIteration);
        addActionListener(viewIteration.getParallelButton(), viewIteration);
        addActionListener(viewIteration.getSequentialButton(), viewIteration);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewIteration.getButtonValidate()) {
            buttonValidatePerformed();
        }

        if (ae.getSource() == viewIteration.getParallelButton()) {
            parallelButtonPerformed();
        }

        if (ae.getSource() == viewIteration.getSequentialButton()) {
            sequentialButtonPerformed();
        }
    }

    private void buttonValidatePerformed() {
        String retrivedPattern = viewIteration.getInputPattern().getText();
        ((ModelIteration) model).setPattern(retrivedPattern, model.getGraph().getNodeCount());
    }

    public PatternUpdate getCurrentPattern() {
        return ((ModelIteration) model).getPattern();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void control() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void sequentialButtonPerformed() {
        ((ModelIteration) model).setSequentialPattern();
    }

    private void parallelButtonPerformed() {
        ((ModelIteration) model).setParallelPattern();
    }
}
