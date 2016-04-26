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
import java.util.List;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import model.ModelAsynchrone;
import org.graphstream.graph.Node;

import view.ViewGeneratorGraph;
import view.ViewMainFrame;


public class ControlerAsynchrone implements ActionListener {

    private final ViewMainFrame viewMainFrame;
    private final ViewGeneratorGraph viewGeneratorGraph;
    private final ModelAsynchrone modelAsynchrone;
    private AtomicBoolean inProgess;

    ControlerAsynchrone(ViewMainFrame viewMainFrame, ViewGeneratorGraph viewGeneratorGraph, ModelAsynchrone modelAsynchrone) {
        this.viewMainFrame = viewMainFrame;
        this.viewGeneratorGraph = viewGeneratorGraph;
        this.modelAsynchrone = modelAsynchrone;
        inProgess = new AtomicBoolean(false);

        modelAsynchrone.addObserver((Observer) viewGeneratorGraph);
        viewMainFrame.getIterationModeAsynchrone().addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewMainFrame.getIterationModeAsynchrone()) {
        	System.out.println(Integer.parseInt(viewGeneratorGraph.getInputAmountOfChips().getText()));
            buttonValidateAsynchronePerformed();
        }
    }

    private void buttonValidateAsynchronePerformed() {
        final String NbChipsTxt = viewGeneratorGraph.getInputAmountOfChips().getText();
        int nbChips = 0;

        try {
            nbChips = Integer.parseInt(NbChipsTxt);
        } catch (NumberFormatException numberFormatException) {
        }

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
                        System.err.println(currConfig);
                        StringBuilder config = new StringBuilder(graph.getNodeCount());

                        for (Node node : graph.getNodeSet()) {
                            config.append(node.getAttribute("chips"));
                        }

                        ConfigurationContrainer configSet = new ConfigurationContrainer(config.toString());
                        inProgess.set(true);

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
                            System.err.println(configFrom + " -> " + configTo);
                            modelAsynchrone.addTransition(configFrom, configTo);
                        }

                        inProgess.set(false);
                    }
                }
            }
        });

        compute.start();
    }

}
