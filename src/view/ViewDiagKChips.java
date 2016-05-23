package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;

import model.ModelKChips;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RefineryUtilities;

import controler.ControlerKChips;

import core.Cfg;
import core.MyGraph;

@SuppressWarnings("serial")
public class ViewDiagKChips extends javax.swing.JFrame {
	
    MyGraph graph = Cfg.getInstance().getGraph();
    
    private ControlerKChips controlerKChips;
    private ModelKChips modelKChips;
    private ViewKChips viewKChips;
    
	public ViewDiagKChips(MyGraph graph) {
		super("Diagram K-Chips");
		this.graph = graph;
		JPanel jpanel = createPanel();
		jpanel.setPreferredSize(new Dimension(560, 370));
		setContentPane(jpanel);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	
		modelKChips = new ModelKChips();
		viewKChips = new ViewKChips();
		controlerKChips = new ControlerKChips(viewKChips, modelKChips);
		
		controlerKChips.printSuccMap(controlerKChips.getSuccMap());
		//controlerKChips.printPrecMap(controlerKChips.getPrecMap());
		controlerKChips.SearchInSuccMap();
		controlerKChips.printPrecMap(controlerKChips.getPrecMap());
	}

	private JFreeChart createChart(XYZDataset xyzdataset) {
		JFreeChart jfreechart = ChartFactory.createBubbleChart(
				"Diagram of limit cycles for 4 chips", 
				"PERIOD", 
				"CHIPS", 
				xyzdataset, 
				PlotOrientation.HORIZONTAL, 
				true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setForegroundAlpha(0.8F);
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		xyitemrenderer.setSeriesPaint(0, Color.blue);
		NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
		numberaxis.setLowerMargin(0.2);
		numberaxis.setUpperMargin(0.5);
		NumberAxis numberaxis1 = (NumberAxis)xyplot.getRangeAxis();
		numberaxis1.setLowerMargin(0.8);
		numberaxis1.setUpperMargin(0.9);
		return jfreechart;
	}

	public XYZDataset createDataset() {
		DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset();
		double[] sommets = {1, 1, 1, 1, 2, 4, 2, 2, 2, 1, 2, 4, 1, 1};
		double[] chips =   {1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 8, 8, 9, 10};
		double[] bubbleSize = new double[sommets.length];
		for (int i = 0; i < bubbleSize.length; i++) {
			bubbleSize[i] = 0.5;
		}
		double matriceSchema[][] = {sommets ,chips ,bubbleSize};
		defaultxyzdataset.addSeries("Cycles limites", matriceSchema);
		return defaultxyzdataset;
	}

	public JPanel createPanel( )
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setDomainZoomable(true);
		chartpanel.setRangeZoomable(true);
		return chartpanel;
	}

	public void initComponents() {
		ViewDiagKChips bubblechart = new ViewDiagKChips(graph);
		//System.out.println(graph.getNodeCount());
		bubblechart.pack();
		RefineryUtilities.centerFrameOnScreen(bubblechart);
		bubblechart.setVisible(true);
	}

}
