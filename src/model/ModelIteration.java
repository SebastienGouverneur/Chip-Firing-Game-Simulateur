package model;

import core.PatternUpdate;
import core.UpdateStateEnum;
import org.graphstream.graph.Graph;

public class ModelIteration extends AbstractModel {

    private PatternUpdate pattern;
    private UpdateStateEnum currentState;

    public ModelIteration() {
        pattern = new PatternUpdate("", 0);
        currentState = UpdateStateEnum.NOTHING_UPDATED;
    }

    public ModelIteration(Graph graph) {
        this.graph = graph;
        pattern = new PatternUpdate("", 0);
        currentState = UpdateStateEnum.NOTHING_UPDATED;
    }

    public PatternUpdate getPattern() {
        return pattern;
    }

    public UpdateStateEnum getCurrentState() {
        return currentState;
    }

    public void setPattern(String retrivedPattern, int nodeCount) {
        PatternUpdate toTest = new PatternUpdate(retrivedPattern, nodeCount);

        if (toTest.isValid()) {
            this.pattern = toTest;
        }

        currentState = UpdateStateEnum.ALL_UPDATED;

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setSequentialPattern() {
        PatternUpdate toTest = new PatternUpdate(PatternUpdate.buildSequentialPattern(graph), graph.getNodeCount());

        if (toTest.isValid()) {
            this.pattern = toTest;
        }

        currentState = UpdateStateEnum.INPUT_PATTERN_UPDATED;

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setParallelPattern() {
        PatternUpdate toTest = new PatternUpdate(PatternUpdate.buildParallelPattern(graph), graph.getNodeCount());

        if (toTest.isValid()) {
            this.pattern = toTest;
        }

        currentState = UpdateStateEnum.INPUT_PATTERN_UPDATED;

        setChanged();
        notifyObservers(this);
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
