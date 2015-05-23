package model;

import java.util.Observable;
import org.graphstream.graph.Graph;

public abstract class AbstractModel extends Observable {
    protected Graph graph;
    protected String stream;
    protected int typeStream;

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public int getTypeStream() {
        return typeStream;
    }

    public void setTypeStream(int typeStream) {
        this.typeStream = typeStream;
    }

    public abstract void run ();

    public abstract void reset ();

    public void setAttribute(String index, String className, String value) {
        graph.getNode(index).addAttribute(className, value);
        /* notifyObservers(); */
    }

}