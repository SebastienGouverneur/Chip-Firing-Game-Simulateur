package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModeAsynchrone {
	
    private final Set<Set<List<Integer>>> returnValue;

    MyGraph graph = Cfg.getInstance().getGraph();
    
    public ModeAsynchrone(MyGraph graph) {
    	returnValue = new HashSet<>();
    	Set<List<Integer>> res = ascMode(graph);
    	returnValue.add(res);
    	this.graph = graph;
    }
   
    public Set<Set<List<Integer>>> getAllDistribution() {
        return returnValue;
    }
    
    private Set<List<Integer>> ascMode(MyGraph graph) {
    	
    	graph = Cfg.getInstance().getGraph();
    	
    	int nbrNodes = graph.getNodeCount();
    	Set<List<Integer>> res = new HashSet<>();
    	List<Integer> currentInstance = new ArrayList<>();
    	
    	if (graph.getNodeCount() < 1 || graph.getNodeSet() == null) 
    		return new HashSet<>();
    	
    	
    	for (int i = 0; i < nbrNodes; i++)
    		currentInstance.add(graph.getNbChipsNode(i));
    	System.out.println("my Current instance : "+currentInstance);
    	
    	res.add(currentInstance);
    	
    	int k = 2;
        int beginNode = 0;
        int endNode = nbrNodes - 1;
    	
    	for (int i = 0; i < nbrNodes; i++) {
    		if (currentInstance.get(i) >= k) {
    			List<Integer> instanceTemp = new ArrayList<>(nbrNodes);
    			instanceTemp.addAll(currentInstance);
    			
    			if (i == 0) {
    				int prev = graph.getNbChipsNode(endNode);
    				int next = graph.getNbChipsNode(beginNode + 1);
    				instanceTemp.remove(endNode);
    				instanceTemp.add(endNode, prev + 1);
    				instanceTemp.remove(beginNode + 1);
    				instanceTemp.add(beginNode + 1, next + 1);
    			}
    			
    			else if (i == instanceTemp.size() - 1) {
    				int prev = graph.getNbChipsNode(endNode - 1);
    				int next = graph.getNbChipsNode(beginNode);
    				instanceTemp.remove(endNode - 1);
    				instanceTemp.add(endNode - 1, prev + 1);
    				instanceTemp.remove(beginNode);
    				instanceTemp.add(beginNode, next + 1);
    			}
    			
    			else {
    				instanceTemp.remove(i - 1);
        			instanceTemp.add(i - 1, graph.getNbChipsNode(i - 1) + 1);
        			
        			instanceTemp.remove(i + 1);
        			instanceTemp.add(i + 1, graph.getNbChipsNode(i + 1) + 1);
    			}
    			
    			instanceTemp.remove(i);
    			instanceTemp.add(i, graph.getNbChipsNode(i) - 2);
    			
				System.out.println(instanceTemp);
    			res.add(instanceTemp);
    		}
    	}
    	
    	System.out.println(res);
    	
    	
		return res;
    	
    }
    
}

