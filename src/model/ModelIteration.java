package model;

import core.Cfg;
import core.MyGraph;
import core.PatternUpdate;
import core.UpdateStateEnum;
import java.util.Observable;

public class ModelIteration extends Observable {

    private PatternUpdate currentPattern;
    private PatternUpdate patternTested;
    private UpdateStateEnum currentState;


    public ModelIteration() {
        currentPattern = new PatternUpdate("", Cfg.getInstance().getGraph());
        patternTested = new PatternUpdate("", Cfg.getInstance().getGraph());
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
        return PatternUpdate.buildSequentialPattern(Cfg.getInstance().getGraph());
    }

    public String getParallelPattern() {
        return PatternUpdate.buildParallelPattern(Cfg.getInstance().getGraph());
    }

    public void reset() {
        currentPattern = new PatternUpdate("", Cfg.getInstance().getGraph());
        currentState = UpdateStateEnum.RESET;
        
        setChanged();
        notifyObservers(this);
        clearChanged();
    }
}
