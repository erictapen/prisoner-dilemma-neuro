import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


@SuppressWarnings("serial")
public class FitnessVisualizer extends ApplicationFrame{
	public FitnessVisualizer(String title, ArrayList<Double> max, ArrayList<Double> min, ArrayList<Double> mean) {
		super(title);

		final XYSeries maxSeries = new XYSeries("Max");
		for(int i=0; i<max.size(); i++) {
			maxSeries.add((double)i, max.get(i));
		}
		
		final XYSeries minSeries = new XYSeries("Min");
		for(int i=0; i<min.size(); i++) {
			minSeries.add((double)i, min.get(i));
		}
		
		final XYSeries meanSeries = new XYSeries("Mean");
		for(int i=0; i<mean.size(); i++) {
			meanSeries.add((double)i, mean.get(i));
		}
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(maxSeries);
		dataset.addSeries(meanSeries);
		dataset.addSeries(minSeries);
		
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
	}

	private JFreeChart createChart(XYSeriesCollection dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Fitnessfunction",      // chart title
            "Time in generations",                      // x axis label
            "Fitness",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        //        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
	}
}
