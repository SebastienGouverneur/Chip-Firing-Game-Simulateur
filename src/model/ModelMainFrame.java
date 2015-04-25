package model;

import core.IMode;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class ModelMainFrame extends AbstractModel {

    public ModelMainFrame() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph = new SingleGraph("Tutorial 1");

        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", "url('file:///home/abdelhak/NetBeansProjects/cfg/src/view/graphCSS.css')");

        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("BC", "B", "C", true);
        graph.addEdge("CA", "C", "A", true);
        graph.addEdge("AD", "A", "D", true);
        graph.addEdge("DE", "D", "E", true);
        graph.addEdge("DF", "D", "F", true);
        graph.addEdge("EF", "E", "F", true);

        for (Node node : graph) {
            node.addAttribute("ui.class", "unmarked");
            node.addAttribute("chips", 2);
            node.addAttribute("ui.label", node.getAttribute("chips"));
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void changeColor(String id) {
        graph.getNode(id).setAttribute("ui.class", "marked");

        setChanged();
        notifyObservers(graph.getNode(id));
        clearChanged();
    }

    public void execute(IMode mode) {
        mode.execute(graph);

        setChanged();
        notifyObservers(graph);
        clearChanged();
    }
}
