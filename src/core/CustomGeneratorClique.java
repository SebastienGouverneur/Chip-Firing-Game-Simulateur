package core;

public class CustomGeneratorClique implements ICustomGenerator {

    public CustomGeneratorClique() {
    }
    
    @Override
    public MyGraph generate(int nbVertex, int nbChips) {
        MyGraph graph = new MyGraph();
        
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
        
        return graph;
    }
    
}
