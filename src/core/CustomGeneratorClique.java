package core;

import org.graphstream.graph.Node;

public class CustomGeneratorClique implements ICustomGenerator {

    public CustomGeneratorClique() {
    }
    
    @Override
    public MyGraph generate(int nbVertex, int nbChips) {
        MyGraph graph = new MyGraph(false);
        
        if (nbVertex <= 1) {
            return graph;
        }
        
        int i = 1, j = 1;
        
        while(j < nbVertex) {
            j++;
            graph.addEdge(""+i+""+j, ""+i, ""+j, true);
            i++;
        }
        
        graph.addEdge(""+nbVertex+"1", ""+nbVertex, "1", true);
        
        for (Node node : graph.getNodeSet()) {
            node.addAttribute("label", nbChips);
        }
        
        return graph;
    }
    
}
