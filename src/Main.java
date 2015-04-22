import model.ModelMainFrame;
import controler.ControlerMainFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.ViewMainFrame;

public class Main {
    public static void main(String[] args) {
        try {
           String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
           UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException
               | InstantiationException | IllegalAccessException e) {
        }
        
        ModelMainFrame modelMainFrame         = new ModelMainFrame();
        ViewMainFrame viewMainFrame           = new ViewMainFrame(modelMainFrame);
        ControlerMainFrame controlerMainFrame = new ControlerMainFrame(viewMainFrame, modelMainFrame);

        viewMainFrame.setVisible(true);
        
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                viewMainFrame.setVisible(true);
            }
        });*/
    }
}