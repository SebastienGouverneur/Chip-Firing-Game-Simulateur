package controler;

import core.Cfg;
import core.PatternUpdate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ModelIteration;
import view.ViewIteration;

public class ControlerIteration implements ActionListener {

    private final ModelIteration modelIteration;
    private final ViewIteration viewIteration;

    ControlerIteration(ViewIteration viewIteration, ModelIteration modelIteration) {
        this.modelIteration = modelIteration;
        this.viewIteration = viewIteration;
        modelIteration.addObserver(viewIteration);

        viewIteration.getButtonValidate().addActionListener((ActionListener) this);
        viewIteration.getParallelButton().addActionListener((ActionListener) this);
        viewIteration.getSequentialButton().addActionListener((ActionListener) this);
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
        modelIteration.setPattern(retrivedPattern, Cfg.getInstance().getGraph());
    }

    public PatternUpdate getCurrentPattern() {
        return modelIteration.getCurrentPattern();
    }

    private void sequentialButtonPerformed() {
        viewIteration.getInputPattern().setText(modelIteration.getSequentialPattern());
    }

    private void parallelButtonPerformed() {
        viewIteration.getInputPattern().setText(modelIteration.getParallelPattern());
    }

    public void reset() {
        modelIteration.reset();
        viewIteration.reset();
    }
}