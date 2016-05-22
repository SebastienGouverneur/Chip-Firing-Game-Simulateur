/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import core.Cfg;
import core.ConfigurationContrainer;
import core.KChips;
import core.ModeSequentialBlock;
import core.MyGraph;
import core.PatternUpdate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import model.ModelKChips;

import org.graphstream.graph.Node;

import scala.collection.parallel.ParSeqLike.LastIndexWhere;
import view.ViewKChips;
import view.ViewMainFrame;

/**
 *
 * @author jon-snow
 */
public class ControlerKChips implements ActionListener {

    private final ViewKChips viewKChips;
    private final ModelKChips modelKChips;
    private AtomicBoolean inProgress;
    private ViewMainFrame viewMainFrame;
    
    public static LinkedHashMap<String, List<String>> succMap = new LinkedHashMap<String, List<String>>();
    public static LinkedHashMap<String, List<String>> precMap = new LinkedHashMap<String, List<String>>();

    
    public ControlerKChips(ViewKChips viewKChips, ModelKChips modelKChips) {
        this.viewKChips = viewKChips;
        this.modelKChips = modelKChips;
        inProgress = new AtomicBoolean(false);

        modelKChips.addObserver((Observer) viewKChips);

        viewKChips.getButtonValidateNbChips().addActionListener((ActionListener) this);
        
        viewMainFrame = new ViewMainFrame();
        
        viewMainFrame.getOptionControlPause().addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewKChips.getButtonValidateNbChips()) {
            buttonValidateNbChipsPerformed();
        }
    }
    
    public LinkedHashMap<String, List<String>> getSuccMap() {
    	return succMap;
    }
    
    public LinkedHashMap<String, List<String>> getPrecMap() {
    	return precMap;
    }
    
    private int getInputNbChips() {
    	 final String NbChipsTxt = viewKChips.getInputNbChips().getText();
         int nbChips = 0;

         try {
             nbChips = Integer.parseInt(NbChipsTxt);
         } catch (NumberFormatException numberFormatException) {
         }

    	return nbChips;
    }

    private void buttonValidateNbChipsPerformed() {
        int nbChips = getInputNbChips();
        
        final MyGraph graph = Cfg.getInstance().getGraph();
        
        KChips k = new KChips(nbChips, graph.getNodeCount());
        final Set<Set<List<Integer>>> n = k.getAllDistribution();

        final Iterable<Node> nodes = graph.getNodeSet();

        Cfg.getInstance().getGraphTrans().clear();

        Thread compute = new Thread(new Runnable() {

            @Override
            public void run() {
            	

                for (Set<List<Integer>> permutations : n) {
                    for (List<Integer> currConfig : permutations) {
                        graph.setNbChipsNodes(nodes, currConfig);
                        
                        String newConfig = modifyListIntToString(currConfig);
                        
                        String befConfigTo = new String();
                        
                        System.err.println(currConfig);
                        StringBuilder config = new StringBuilder(graph.getNodeCount());

                        for (Node node : graph.getNodeSet()) {
                            config.append(node.getAttribute("chips"));
                        }

                        ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());
                        inProgress.set(true);
                        
                        List<String> configSucc = new ArrayList<String>();
                        List<String> configPrec = new ArrayList<String>();
                        
                        while (!configSet.cycleDetected() && !Thread.currentThread().isInterrupted()) {
                            PatternUpdate p = new PatternUpdate(PatternUpdate.buildParallelPattern(graph), graph);
                            String configFrom = configSet.getLastConfig();

                            graph.execute(
                                    new ModeSequentialBlock(
                                            p,
                                            configSet,
                                            Cfg.getTimeExec(),
                                            Cfg.getTimeAnimation()
                                    )
                            );

                            String configTo = configSet.getLastConfig();
                            befConfigTo = configTo;
                            System.err.println(configFrom + " -> " + configTo);
                            modelKChips.addTransition(configFrom, configTo);
                            
                            configSucc.add(configTo);
                            configPrec.add(configFrom);
                        
                        }
                        //precMap.put(befConfigTo, configPrec);
                        succMap.put(newConfig, configSucc);
                        Cfg.setTimeAnimation(1);
						Cfg.setTimeExec(1);
                    }
                }
            }
        });
        
        compute.start();
        
        
    }
    
    public String modifyListIntToString(List<Integer> list) {
    	StringBuilder strbul  = new StringBuilder();
        Iterator<Integer> iter = list.iterator();
        while(iter.hasNext())
        {
            strbul.append(iter.next());
           if(iter.hasNext()){
            strbul.append("");
           }
        }
        return strbul.toString();
    }
    
    public void SearchInSuccMap() {
    	System.out.println("SEARCH");
    	List<String> tabAux = new ArrayList<String>();
    	for (Entry<String, List<String>> me : getSuccMap().entrySet()) {
    		List<String> tabString = me.getValue();
    		List<String> configPrec = new ArrayList<String>();
    		configPrec.add(me.getKey());
    		precMap.put(me.getValue().get(0), configPrec);
    	} 
    	
    }
    
    public void printSuccMap(HashMap<String, List<String>> hashMap) {
    	System.out.println("SUCC");
    	for (Entry<String, List<String>> me : getSuccMap().entrySet()) {
    	      System.out.print(me.getKey() + " : ");
    	      System.out.println(me.getValue());
    	}
    }
    public void printPrecMap(HashMap<String, List<String>> hashMap) {
    	System.out.println("PREC");
    	for (Entry<String, List<String>> me : getPrecMap().entrySet()) {
    	      System.out.print(me.getKey() + " : ");
    	      System.out.println(me.getValue());
    	}
    }

}
