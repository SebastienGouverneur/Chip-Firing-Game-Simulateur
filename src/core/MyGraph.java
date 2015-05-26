package core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ModelMainFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

public class MyGraph {

    private final Graph graph;
    private String format; /*TODO : associer le format a chaque graphe */

    private SpriteManager spriteManager;

    private final Viewer viewer;
    private ViewPanel viewCamera;
    private ViewerPipe fromViewer;

    public void importDOTFile(String filename) throws IOException {
        graph.clear();

        try {
            FileSource fs = new FileSourceDOT();
            fs.addSink(graph);
            fs.readAll(filename);
            fs.removeSink(graph);
        } catch (IOException ex) {
            Logger.getLogger(ModelMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        initGraphAttributes();
    }

    public MyGraph() {
        graph = new MultiGraph("imported_graph", false, true);
        spriteManager = new SpriteManager(graph);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        createViewGraph();
    }

    public MyGraph(Graph graph) {
        this.graph = graph;
        initGraphAttributes();
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
    }

    public void setAllNodesUnmarked() {
        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
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

        for (Node node : graph) {
            displayIdNode(node);
            node.addAttribute("chips", Integer.parseInt(node.getAttribute("label").toString()));
            node.setAttribute("ui.class", "unmarked");

            for (Edge edgeOut : node.getEachLeavingEdge()) {
                edgeOut.addAttribute("ui.class", "unmarked");
            }
        }
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

    public void displayIdNode(Node node) {
        Sprite s;
        s = spriteManager.addSprite(node.getId());
        s.addAttribute("label", node.getId());
        s.attachToNode(node.getId());
        s.setPosition(0.15);
    }

    public void setNodeMarked(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");
    }

    public boolean isSelected(String id) {
        return graph.getNode(id).getAttribute("ui.class").equals("marked");
    }

    public void execute(Algorithm algo) {
        algo.init(Cfg.getInstance().getGraph());
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

    public Node getNode(String id) {
        return graph.getNode(id);
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

    Iterable<Edge> getEdgeSet() {
        return graph.getEdgeSet();
    }
}
