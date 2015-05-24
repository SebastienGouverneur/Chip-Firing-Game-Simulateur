package model;

import java.util.Observable;

public class ModelLogFrame extends Observable {
    String filename;
    StringBuilder log;

    public ModelLogFrame() {
    }

    public void addText(String text) {
        setChanged();
        notifyObservers();
    }
}