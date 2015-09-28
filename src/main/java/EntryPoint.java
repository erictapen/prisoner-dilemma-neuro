
public class EntryPoint {

	public static void main(String[] args) {
		System.out.println("Starting breed process.");
		Breeder brdr = new Breeder(Settings.POPULATION_SIZE);
		brdr.startEvolution(Settings.EVOLUTION_STEPS);
		FileHandler.exportPoolToFile("test.json", brdr);
	}

}
