package controler;

import core.Cfg;
import core.CustomGeneratorGrid;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ModelFile;
import view.ViewGeneratorGraph;

public class ControlerFile implements ActionListener, ListSelectionListener {

    private final ViewGeneratorGraph viewGeneratorGraph;
    private final ModelFile modelFile;

    public ControlerFile(ModelFile modelOpenFile) {
        this.modelFile = modelOpenFile;
        this.viewGeneratorGraph = new ViewGeneratorGraph(modelOpenFile);

        viewGeneratorGraph.getListGenerator().getSelectionModel().addListSelectionListener((ListSelectionListener) this);
        viewGeneratorGraph.getValidateGenerator().addActionListener((ActionListener) this);
        viewGeneratorGraph.getButtonCancel().addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewGeneratorGraph.getValidateGenerator()) {
            System.out.println("h");
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
        } else {
            int minIndex = lsm.getMinSelectionIndex();

            switch (minIndex) {
                case 0:

                    modelFile.generateGraph(
                            new CustomGeneratorGrid(
                                    false,
                                    false,
                                    false,
                                    true
                            ), 10, 10);
//                            Integer.parseInt(viewGeneratorGraph.getInputNumberOfVertex().getText()),
//                            Integer.parseInt(viewGeneratorGraph.getInputAmountOfChips().getText()));
                    break;
                case 1:
//                    modelFile.generateGraph (new CustomGraphZob ());
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }

            viewGeneratorGraph.getPreviewGraph().add(modelFile.getViewer().addDefaultView(false));
            viewGeneratorGraph.getPreviewGraph().revalidate();
        }
    }
}
