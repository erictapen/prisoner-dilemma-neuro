import org.jfree.ui.RefineryUtilities;


public class EntryPoint {

	public static void main(String[] args) {
		System.out.println("Starting breed process.");
		Breeder brdr = new Breeder(Settings.POPULATION_SIZE);
		brdr.startEvolution(Settings.EVOLUTION_STEPS);
		FileHandler.exportPoolToFile("test.json", brdr);
		
		//plot a fitness chart over evolution steps
		FitnessVisualizer demo = new FitnessVisualizer("Line Chart Demo 6", brdr.maxFitnessrecord, 
				brdr.minFitnessrecord, brdr.meanFitnessrecord);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

	}

}
