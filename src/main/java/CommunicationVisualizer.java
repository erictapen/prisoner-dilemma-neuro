import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;


@SuppressWarnings("serial")
public class CommunicationVisualizer extends ApplicationFrame{
    /** 
     * 
     */ 
    public CommunicationVisualizer(ArrayList<ArrayList<Double>> valuesLists, int evGen, int yRes) { 
        super("Use of the communication channel between two random prisoners over game time"); 
        int count=0;
        for(ArrayList<Double> lx : valuesLists) {
        	for(Double x : lx) count++;
        }
        double[][] heat = new double[3][valuesLists.size()*yRes];
        int it=0;
        for(int i=0; i<valuesLists.size(); i++) {
        	ArrayList<Integer> distribution = new ArrayList<Integer>();
        	for(Double x : valuesLists.get(i)) {
        		
        		if(distribution.contains((int)(x.doubleValue()*yRes))) System.out.println("bla"); //TODO
        		heat[0][it] = (double)i;
        		heat[1][it] = x.doubleValue();
        		heat[2][it] = 1.0;
        		it++;
        	}
        }
        
        DefaultXYZDataset data = new DefaultXYZDataset();
        data.addSeries("Generation " + evGen, heat);
        JPanel chartPanel = new ChartPanel(createChart(data));
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270)); 
        setContentPane(chartPanel); 
    } 
     
    /** 
     * Creates a sample chart. 
     *  
     * @param dataset  the dataset. 
     *  
     * @return A sample chart. 
     */ 
    private static JFreeChart createChart(XYZDataset dataset) { 
        NumberAxis xAxis = new NumberAxis("Game steps"); 
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
        xAxis.setLowerMargin(0.0); 
        xAxis.setUpperMargin(0.0); 
        NumberAxis yAxis = new NumberAxis("Actication of communication neuron"); 
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
        yAxis.setLowerMargin(0.0); 
        yAxis.setUpperMargin(0.0); 
        XYBlockRenderer renderer = new XYBlockRenderer(); 
        PaintScale scale = new GrayPaintScale(-2.0, 1.0); 
        renderer.setPaintScale(scale); 
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer); 
        plot.setBackgroundPaint(Color.lightGray); 
        plot.setDomainGridlinesVisible(false); 
        plot.setRangeGridlinePaint(Color.white); 
        JFreeChart chart = new JFreeChart(
        		"Use of the communication channel between two random prisoners over game time", plot); 
        chart.removeLegend(); 
        chart.setBackgroundPaint(Color.white); 
        return chart; 
    } 

}
