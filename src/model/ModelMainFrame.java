package model;

import core.IChipOperation;
import core.MyGraph;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.algorithm.DynamicAlgorithm;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

public class ModelMainFrame extends Observable {
    
    private final MyGraph graph;
    private final ConcurrentSkipListSet<String> selectedNode;
    private double timeExec;
    private double timeAnimation;

    public ModelMainFrame() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        selectedNode = new ConcurrentSkipListSet<>();
        graph = new MyGraph();
    }

    public ConcurrentSkipListSet<String> getSelectedNode() {
        return selectedNode;
    }

    public void changeColor(String id) {
        graph.setNodeMarked(id);

        setChanged();
        notifyObservers(graph.getNode(id));
        clearChanged();
    }

    public void setSelectedNode(String id) {
        graph.setNodeMarked(id);
        selectedNode.add(id);

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setUnselectedNode(String id) {
        graph.setNodeUnmarked(id);
        selectedNode.remove(id);

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public boolean isSelected(String id) {
        return graph.isSelected(id);
    }

    public void execute(DynamicAlgorithm algo) {
        graph.execute(algo);
    }

    public void toggleSelectedNode(String id) {
        if (isSelected(id)) {
            setUnselectedNode(id);
        } else {
            setSelectedNode(id);
        }
    }

    public void computeNodesValues(int nbToApply, IChipOperation op) {
        for (String nodeId : selectedNode) {
            int nodeCurrentChips = graph.getNbChipsNode(nodeId);
            graph.setNbChipsNode(nodeId, op.compute(nbToApply, nodeCurrentChips));
            graph.setNodeLabel(nodeId, graph.getNbChipsNode(nodeId));
            setUnselectedNode(nodeId);
        }

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setTimeExec(double timeExec) {
        this.timeExec = timeExec;
        
        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public double getTimeExec() {
        return timeExec;
    }

    public void setTimeAnimation(double timeAnimation) {
        this.timeAnimation = timeAnimation;

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void addSelectedNode(String id) {
        graph.addAttribute(id, "ui.class", "marked");
    }

    public double getTimeAnimation() {
        return timeAnimation;
    }

    public MyGraph getGraph() {
        return graph;
    }

    public Iterable<Node> getNodeSet() {
        return graph.getNodeSet();
    }

    public Viewer getViewer() {
        return graph.getViewer();
    }

    public void createViewGraph() {
        graph.createViewGraph();
    }

    public ViewerPipe getFromViewer() {
        return graph.getFromViewer();
    }

    public void importDOTFile(String filename) {
        try {
            graph.importDOTFile(filename);
            
        } catch (IOException ex) {
            Logger.getLogger(ModelMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
