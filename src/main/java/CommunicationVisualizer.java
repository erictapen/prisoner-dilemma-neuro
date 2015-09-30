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
        
        double heat[][] = new double[valuesLists.size()][yRes];
        for (int i = 0; i < valuesLists.size(); i++) {
			for (Double x : valuesLists.get(i)) {
				double v = x.doubleValue() + 1.0;
				v *= yRes*0.5;
				if(v<0.0) v=0.0;
				if(v>=yRes) v = yRes-1;
				heat[i][(int)v] += 1.0;
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
