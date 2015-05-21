
import controler.ControlerMainFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.ModelMainFrame;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import view.ViewMainFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try {
            String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }

        Graph graph = new MultiGraph("grid", false, false);
        Generator gen = new CustomGeneratorGrid(true, true, false, true);

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < 2; i++) {
            gen.nextEvents();
        }
        gen.end();
//        
        ModelMainFrame modelMainFrame = new ModelMainFrame(graph);
        ViewMainFrame viewMainFrame = new ViewMainFrame(modelMainFrame);
        ControlerMainFrame controlerMainFrame = new ControlerMainFrame(viewMainFrame, modelMainFrame);

        viewMainFrame.setVisible(true);
    }
}
