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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewIteration.getButtonValidate()) {
            buttonValidatePerformed();
        }

    }

    private void buttonValidatePerformed() {
        String retrivedPattern = viewIteration.getInputPattern().getText();
        String state = "";

        System.out.print(retrivedPattern);

        PatternUpdate pattern = new PatternUpdate();
        boolean isValid = pattern.initPattern(retrivedPattern, model.getGraph().getNodeCount());

        if (isValid) {
            state = "Valid pattern !";
            ((ModelIteration) model).setPattern(pattern);
        } else {
            state = "Invalid pattern !";
        }
        
        ((ModelIteration) model).setStatePattern(state);
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
}
