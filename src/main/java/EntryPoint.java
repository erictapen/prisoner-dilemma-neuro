import org.jfree.ui.RefineryUtilities;


public class EntryPoint {

	public static void main(String[] args) {
		System.out.println("Starting breed process.");
		Breeder brdr = new Breeder(Settings.POPULATION_SIZE);
		System.out.println("Starting evolution with " +  Settings.EVOLUTION_STEPS + " iterations.");
		for (int i = 1; i <= Settings.EVOLUTION_STEPS; i++) {
			if(i == Settings.EVOLUTION_STEPS) brdr.doEvolution(true);  //should be recorded only on last generation
			else brdr.doEvolution(false);
			System.out.println("Evolution round " + i + " finished.");
		}
		FileHandler.exportPoolToFile("test.json", brdr);
		
		//plot a fitness chart over evolution steps
		FitnessVisualizer fitViz = new FitnessVisualizer("Fitness over evolution steps", brdr.maxFitnessrecord, 
				brdr.minFitnessrecord, brdr.meanFitnessrecord);
		fitViz.pack();
        RefineryUtilities.centerFrameOnScreen(fitViz);
        fitViz.setVisible(true);
        
        //plot a communication chart from the last evolution step
        CommunicationVisualizer commViz = new CommunicationVisualizer(Director.getCommData(), 
        		Settings.EVOLUTION_STEPS, 100);
        commViz.pack();
        RefineryUtilities.centerFrameOnScreen(commViz);
        commViz.setVisible(true);

	}

}
