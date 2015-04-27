package model;

import core.PatternUpdate;
import org.graphstream.graph.Graph;

public class ModelIteration extends AbstractModel {

    PatternUpdate pattern;
    String statePattern;

    public ModelIteration() {
        pattern = new PatternUpdate();
        statePattern = new String();
    }

    public ModelIteration(Graph graph) {
        this.graph = graph;
        pattern = new PatternUpdate();
        statePattern = new String();
    }

    public void setStatePattern(String statePattern) {
        this.statePattern = statePattern;

        setChanged();
        notifyObservers(statePattern);
        clearChanged();
    }

    public PatternUpdate getPattern() {
        return pattern;
    }
    
    public void setPattern(PatternUpdate pattern) {
        this.pattern = pattern;

        setChanged();
        notifyObservers(pattern);
        clearChanged();
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
