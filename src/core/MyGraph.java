package core;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import model.ModelMainFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import view.ViewMainFrame;

public class MyGraph {

    private final Graph graph;
    private SpriteManager spriteManager;
    private final Viewer viewer;
    private ViewerPipe fromViewer;
    private final Thread checkUpdateGraph;
    private Click clicklistener;

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

        spriteManager = new SpriteManager(graph);
        initGraphAttributes();
    }

    public void setGraph(MyGraph graph, boolean initAttribute) throws IOException {
        this.graph.clear();
        Graphs.mergeIn(this.graph, graph.getGraph());

        if (initAttribute) {
            spriteManager = new SpriteManager(this.graph);
            initGraphAttributes();
        }
    }

    public void setClickListener(Click clickListener) {
        this.clicklistener = clickListener;
        fromViewer.addViewerListener(clickListener);
    }

    public Click getClickListener() {
        return clicklistener;
    }

    public MyGraph(boolean autoPump) {
        graph = new MultiGraph("imported_graph", false, true);
        spriteManager = new SpriteManager(graph);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();

        fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);

        checkUpdateGraph = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        fromViewer.blockingPump();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ViewMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        if (autoPump) {
            checkUpdateGraph.start();
        }
    }

    public void startPump() {
        if (!checkUpdateGraph.isAlive()) {
            checkUpdateGraph.start();
        }
    }

    public MyGraph(Graph graph) {
        this.graph = graph;
        spriteManager = new SpriteManager(graph);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();

        fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(this.graph);

        checkUpdateGraph = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        fromViewer.blockingPump();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ViewMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
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

    public void initGraphAttributes() {
        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("ui.antialias");
        this.graph.addAttribute("ui.stylesheet", "url('view/graph.css')");

        for (Node node : graph) {
            displayIdNode(node);
            node.addAttribute("chips", Integer.parseInt(node.getAttribute("label").toString()));
            node.addAttribute("ui.class", "unmarked");

            for (Edge edgeOut : node.getEachLeavingEdge()) {
                edgeOut.addAttribute("ui.class", "unmarked");
            }
            displayIdNode(node);
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
        Sprite s = spriteManager.addSprite(node.getId());
        s.setPosition(Units.PX, 200, 200, 100);
        s.attachToNode(node.getId());
        s.addAttribute("ui.label", node.getId());
    }

    public void setNodeMarked(String id) {
        graph.getNode(id).addAttribute("ui.class", "marked");
    }

    public boolean isSelected(String id) {
        return graph.getNode(id).getAttribute("ui.class").equals("marked");
    }

    public void execute(IAlgorithm algo) {
        algo.init(Cfg.getInstance().getGraph());
        algo.compute();
        if (Thread.currentThread().isInterrupted()) {
            System.err.println("ne pas executer terminate() !!!");
            Thread.currentThread().interrupt();
            return;
        }
        algo.terminate();
    }

    public int getNbChipsNode(String nodeId) {
        return graph.getNode(nodeId).getAttribute("chips");
    }

    public void setNodeLabel(String nodeId, int nbChips) {
        graph.getNode(nodeId).addAttribute("ui.label", nbChips);
    }

    public void setNbChipsNode(String nodeId, int nbChips) {
        graph.getNode(nodeId).addAttribute("chips", nbChips);
    }
    
    public void setNbChipsNodes(Iterable<Node> nodes, Iterable<Integer> config) {
        
        Iterator<Node> itNodes = nodes.iterator();
        Iterator<Integer> itConf = config.iterator();
        
        while(itNodes.hasNext() && itConf.hasNext()) {
            final Node curNode = itNodes.next();   
            final Integer curConf = itConf.next();
            graph.getNode(curNode.getId()).addAttribute("chips", curConf);
            graph.getNode(curNode.getId()).addAttribute("ui.label", curConf);
        }
    }

    public Node getNode(String id) {
        return graph.getNode(id);
    }

    public boolean isNodeMarked(String id) {
        return graph.getNode(id).getAttribute("ui.class") == "marked";
    }

    private Graph getGraph() {
        return graph;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public final void createViewGraph() {
        fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(graph);
    }

    public ViewerPipe getFromViewer() {
        return fromViewer;
    }

    public void setNodeUnmarked(String id) {
        graph.getNode(id).addAttribute("ui.class", "unmarked");
    }

    Iterable<Edge> getEdgeSet() {
        return graph.getEdgeSet();
    }

    void toggleSelectedNode(String id) {
        if (isSelected(id)) {
            setUnselectedNode(id);
        } else {
            setSelectedNode(id);
        }
    }

    public void setSelectedNode(String id) {
        Cfg.getInstance().getGraph().setNodeMarked(id);
    }

    public void setUnselectedNode(String id) {
        Cfg.getInstance().getGraph().setNodeUnmarked(id);
    }

    public void attachViewGraph(JPanel viewGraph) {
        viewGraph.add(getViewer().addDefaultView(false));
        viewGraph.revalidate();
    }

}
