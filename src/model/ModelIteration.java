package model;

import core.MyGraph;
import core.PatternUpdate;
import core.UpdateStateEnum;
import java.util.Observable;

public class ModelIteration extends Observable {

    private MyGraph graph;
    private PatternUpdate currentPattern;
    private PatternUpdate patternTested;
    private UpdateStateEnum currentState;

    public ModelIteration() {
        currentPattern = new PatternUpdate("", graph);
        currentState = UpdateStateEnum.NOTHING_UPDATED;
    }

    public MyGraph getGraph() {
        return graph;
    }

    public ModelIteration(MyGraph graph) {
        this.graph = graph;
        currentPattern = new PatternUpdate("", graph);
        patternTested = new PatternUpdate("", graph);
        currentState = UpdateStateEnum.NOTHING_UPDATED;
    }

    public PatternUpdate getCurrentPattern() {
        return currentPattern;
    }

    public PatternUpdate getpatternTested() {
        return patternTested;
    }

    public UpdateStateEnum getCurrentState() {
        return currentState;
    }

    public void setPattern(String retrivedPattern, MyGraph graph) {
        patternTested = new PatternUpdate(retrivedPattern, graph);

        if (patternTested.isValid()) {
            this.currentPattern = patternTested;
        }

        currentState = UpdateStateEnum.ALL_UPDATED;

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public String getSequentialPattern() {
        return PatternUpdate.buildSequentialPattern(graph);
    }

    public String getParallelPattern() {
        return PatternUpdate.buildParallelPattern(graph);
    }

    public int getNodeCount() {
        return graph.getNodeCount();
    }

    public void reset() {
        currentPattern = new PatternUpdate("", graph);
        currentState = UpdateStateEnum.RESET;
        
        setChanged();
        notifyObservers(this);
        clearChanged();
    }
}
