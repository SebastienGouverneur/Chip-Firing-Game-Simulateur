package model;

import core.MyGraph;
import core.PatternUpdate;
import core.UpdateStateEnum;
import java.util.Observable;

public class ModelIteration extends Observable {

    private MyGraph graph;
    private PatternUpdate pattern;
    private UpdateStateEnum currentState;

    public ModelIteration() {
        pattern = new PatternUpdate("", 0);
        currentState = UpdateStateEnum.NOTHING_UPDATED;
    }

    public ModelIteration(MyGraph graph) {
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

    public int getNodeCount() {
        return graph.getNodeCount();
    }
}
