package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.graphstream.ui.swingViewer.ViewPanel;

public class MouseGraphTrans implements MouseListener {

    private final ViewPanel viewCamera;
    private int x;
    private int y;

    public MouseGraphTrans(ViewPanel viewCamera) {
        this.viewCamera = viewCamera;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    
    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
