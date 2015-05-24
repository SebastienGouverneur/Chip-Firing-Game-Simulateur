package core;

import org.graphstream.algorithm.DynamicAlgorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

public class MyGraph {
    private final Graph graph;
    private String format; /*TODO : associer le format a chaque graphe */
    private final Viewer viewer;
    private ViewPanel viewCamera;
    private ViewerPipe fromViewer;

    public MyGraph(Graph graph) {
        this.graph = graph;
        initGraphAttributes();
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
    }

    public void setAllNodesUnmarked() {
        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
            node.addAttribute("chips", 2);
            node.addAttribute("ui.label", node.getAttribute("chips").toString());
        }
    }

    public void clear() {
        graph.clear();
        initGraphAttributes();
    }

    public Node addNode(String id) {
        return graph.addNode(id);
    }

    public Edge addEdge(String id, Node from, Node to, boolean b) {
        return graph.addEdge(id, from, to, b);
    }

    public Edge addEdge(String id, String from, String to, boolean b) {
        return graph.addEdge(id, from, to, b);
    }
    
    public void addAttribute(String id, String aclass, String value) {
        graph.getNode(id).addAttribute(id, "ui.class", "marked");
    }
    
    public void pump() {
        fromViewer.pump();
    }

    public Iterable<Node> getNodeSet() {
        return graph.getNodeSet();
    }

    private void initGraphAttributes() {
        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('view/graph.css')");
    }

    public int getNodeCount() {
        return graph.getNodeCount();
    }

    public void setAllEdgesUnmarked() {
        for (Node node : graph) {
            for (Edge edgeOut : node.getEachLeavingEdge()) {
                edgeOut.addAttribute("ui.class", "unmarked");
            }
        }
    }

    public void setNodeMarked(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");
    }

    public boolean isSelected(String id) {
        return graph.getNode(id).getAttribute("ui.class").equals("marked");
    }

    public void execute(DynamicAlgorithm algo) {
        algo.init(graph);
        algo.compute();
        algo.terminate();
    }

    public int getNbChipsNode(String nodeId) {
        return graph.getNode(nodeId).getAttribute("chips");
    }

    public void setNodeLabel(String nodeId, int nbChips) {
        graph.getNode(nodeId).setAttribute("ui.label", nbChips);
    }

    public void setNbChipsNode(String nodeId, int nbChips) {
        graph.getNode(nodeId).setAttribute("chips", nbChips);
    }

    public Object getNode(String id) {
        return graph.getNode (id);
    }

    public boolean isNodeMarked(String id) {
        return graph.getNode(id).getAttribute("ui.class") == "marked";
    }


    public Graph getGraph() {
        return graph;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void createViewGraph() {
        fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
    }

    public ViewerPipe getFromViewer() {
        return fromViewer;
    }

    public void setNodeUnmarked(String id) {
         graph.getNode(id).setAttribute("ui.class", "unmarked");
    }
}
