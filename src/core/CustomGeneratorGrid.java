package core;


import core.CustomGenerator;
import core.MyGraph;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class CustomGeneratorGrid implements CustomGenerator {

    private final Generator generator;

    public CustomGeneratorGrid(boolean cross, boolean tore, boolean generateXY, boolean directed) {
        generator = new GridGenerator(cross, tore, generateXY, directed);
    }

    @Override
    public MyGraph generate(MyGraph graphToFill, int nbVertex, int nbChips) {
        Graph generatedgraph = new MultiGraph("GeneratedGridGraph", false, true);

        generator.addSink(generatedgraph);
        generator.begin();
        for (int i = 0; i < nbVertex; i++) {
            generator.nextEvents();
        }
        generator.end();

        for (Node node : generatedgraph) {
            node.addAttribute("label", nbChips);
        }

        MyGraph graph = new MyGraph(generatedgraph);
        graph.setAllNodesUnmarked();
        graph.setAllEdgesUnmarked();

        return graph;
    }

}
