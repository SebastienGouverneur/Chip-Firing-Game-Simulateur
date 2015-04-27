package model;

import core.PatternUpdate;
import org.graphstream.graph.Graph;

public class ModelIteration extends AbstractModel {

    PatternUpdate pattern;

    public ModelIteration() {
        pattern = new PatternUpdate("", 0);
    }

    public ModelIteration(Graph graph) {
        this.graph = graph;
        pattern = new PatternUpdate("", 0);
    }

    public PatternUpdate getPattern() {
        return pattern;
    }

    public void setPattern(String retrivedPattern, int nodeCount) {
        PatternUpdate toTest = new PatternUpdate(retrivedPattern, nodeCount);

        if (toTest.isValid()) {
            this.pattern = toTest;
        }

        setChanged();
        notifyObservers(toTest);
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
