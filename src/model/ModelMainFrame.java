package model;

import core.IChipOperation;
import java.util.concurrent.ConcurrentSkipListSet;
import org.graphstream.algorithm.DynamicAlgorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class ModelMainFrame extends AbstractModel {

    private ConcurrentSkipListSet<String> selectedNode;
    private double timeExec;
    private double timeAnimation;

    public ModelMainFrame() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        selectedNode = new ConcurrentSkipListSet<>();

        graph = new SingleGraph("Tutorial 1");

        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", "url('file:////home/abdelhak/NetBeansProjects/cfg/src/view/graphCSS.css')");

        graph.addEdge("12", "1", "2", true);
        graph.addEdge("23", "2", "3", true);
        graph.addEdge("34", "3", "4", true);
        graph.addEdge("45", "4", "5", true);
        graph.addEdge("51", "5", "1", true);

        graph.addEdge("21", "2", "1", true);
        graph.addEdge("32", "3", "2", true);
        graph.addEdge("43", "4", "3", true);
        graph.addEdge("54", "5", "4", true);
        graph.addEdge("15", "1", "5", true);
        
        
        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
            node.addAttribute("chips", 2);
            node.addAttribute("ui.label", node.getAttribute("chips").toString());
        }

        for (Node node : graph) {
            for (Edge edgeOut : node.getEachLeavingEdge()) {
                edgeOut.addAttribute("ui.class", "unmarked");
            }
        }
    }

    public ModelMainFrame(Graph graphi) {
        this.graph = graphi;
        selectedNode = new ConcurrentSkipListSet<>();

        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", "url('file:////home/abdelhak/NetBeansProjects/cfg/src/view/graphCSS.css')");

        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
            node.addAttribute("chips", 2);
            node.addAttribute("ui.label", node.getAttribute("chips").toString());
        }

        for (Node node : graph) {
            for (Edge edgeOut : node.getEachLeavingEdge()) {
                edgeOut.addAttribute("ui.class", "unmarked");
            }
        }
    }

    public ConcurrentSkipListSet<String> getSelectedNode() {
        return selectedNode;
    }

    public void changeColor(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");

        setChanged();
        notifyObservers(graph.getNode(id));
        clearChanged();
    }

    public void setSelectedNode(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");
        selectedNode.add(id);

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setUnselectedNode(String id) {
        graph.getNode(id).setAttribute("ui.class", "unmarked");
        selectedNode.remove(id);

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public boolean isSelected(String id) {
        return graph.getNode(id).getAttribute("ui.class").equals("marked");
    }

    public void execute(DynamicAlgorithm algo) {
        algo.init(graph);
        algo.compute();
        algo.terminate();

        setChanged();
        notifyObservers(graph);
        clearChanged();
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
            int nodeCurrentChips = ((int) graph.getNode(nodeId).getAttribute("chips"));
            graph.getNode(nodeId).setAttribute("chips", op.compute(nbToApply, nodeCurrentChips));
            graph.getNode(nodeId).setAttribute("ui.label", (int) graph.getNode(nodeId).getAttribute("chips"));
            setUnselectedNode(nodeId);
        }

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public double getTimeExec() {
        return timeExec;
    }

    public void setTimeExec(double timeExec) {
        this.timeExec = timeExec;

        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public double getTimeAnimation() {
        return timeAnimation;
    }

    public void setTimeAnimation(double timeAnimation) {
        this.timeAnimation = timeAnimation;

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
