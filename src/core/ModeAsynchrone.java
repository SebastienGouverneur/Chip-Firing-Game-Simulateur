package core;

import org.graphstream.graph.Node;

public class ModeAsynchrone implements IAlgorithm {
	
	private final PatternUpdate patternUpdate;
    private final ConfigurationContrainer configSet;
    private MyGraph graph;
    private final double time;
    private final double timeAnimation;
    
    public ModeAsynchrone(PatternUpdate patternUpdate, ConfigurationContrainer configSet, double time, double timeAnimation) {
    
    	this.patternUpdate = patternUpdate;
    	this.configSet = configSet;
    	this.time = time;
    	this.timeAnimation = timeAnimation;
    	
    }

	@Override
	public void terminate() {
		StringBuilder config = new StringBuilder(graph.getNodeCount());

        for (Node node : graph.getNodeSet()) {
            config.append(node.getAttribute("chips"));
            node.addAttribute("ui.label", node.getAttribute("chips").toString());
        }

        boolean isInserted = configSet.insertConfiguration(config.toString());

        try {
            Thread.sleep((long) (time));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
		
	}

	@Override
	public void init(MyGraph graph) {
		this.graph = graph;	
	}

	@Override
	public void compute() {
		
		
		
	}

}
