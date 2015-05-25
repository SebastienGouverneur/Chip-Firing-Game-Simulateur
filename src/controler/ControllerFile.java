package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import model.ModelFile;
import view.ViewOpenFile;

public class ControllerFile {

    private final ViewOpenFile viewOpenFile;
    private final ModelFile modelOpenFile;
    private boolean isGraphImported;

    public ControllerFile(ViewOpenFile viewOpenFile, ModelFile modelOpenFile) {
        this.viewOpenFile = viewOpenFile;
        this.modelOpenFile = modelOpenFile;
        this.isGraphImported = false;
        
        
//        viewOpenFile.getjFileChooser1().addActionListener((ActionListener) this);
    }

//    
//    public void actionPerformed(ActionEvent ae) {
//        int returnVal = viewOpenFile.getReturnVal ();
//
//        System.err.println(returnVal);
//        System.err.println(JFileChooser.APPROVE_OPTION);
//        
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            importDOTFile(viewOpenFile.getjFileChooser1().getSelectedFile().getAbsolutePath());
//            isGraphImported = true;
//            System.err.println("graph importé");
//        }
//    }
//
//    public void importDOTFile(String filename) {
//        modelOpenFile.importDOTFile(filename);
//    }
//
//    boolean isGraphImported() {
//        System.err.println("return isGraphImported");
//        return isGraphImported;
//    }
}
