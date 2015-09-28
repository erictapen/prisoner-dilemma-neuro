
public class EntryPoint {

	public static void main(String[] args) {
		System.out.println("Starting breed process.");
		Breeder brdr = new Breeder(Settings.POPULATION_SIZE);
		brdr.startEvolution(5);
		FileHandler.exportPoolToFile("test.json", brdr);
	}

}
