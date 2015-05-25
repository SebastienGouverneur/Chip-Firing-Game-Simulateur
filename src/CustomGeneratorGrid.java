
import core.MyGraph;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class CustomGeneratorGrid extends GridGenerator {
    
    private final Generator generator;

    CustomGeneratorGrid(boolean cross, boolean tore, boolean generateXY, boolean directed) {
        generator = new GridGenerator(cross, tore, generateXY, directed);
    }

    MyGraph generateGridGraph(int dimension, int nbChips) {

        Graph generatedgraph = new MultiGraph("GeneratedGridGraph", false, true);

        generator.addSink(generatedgraph);
        generator.begin();
        for (int i = 0; i < dimension; i++) {
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
