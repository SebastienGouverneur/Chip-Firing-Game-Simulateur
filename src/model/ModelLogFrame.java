package model;

public class ModelLogFrame extends AbstractModel {

    String filename;
    StringBuilder log;

    public ModelLogFrame() {

    }

    public void addText(String text) {

        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
