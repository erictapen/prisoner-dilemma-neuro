import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;


public class Breeder {
	ArrayList<Prisoner> pool = new ArrayList<Prisoner>();
	
	public ArrayList<Double> maxFitnessrecord = new ArrayList<Double>();
	public ArrayList<Double> minFitnessrecord = new ArrayList<Double>();
	public ArrayList<Double> meanFitnessrecord = new ArrayList<Double>();
	
	
	Comparator<Prisoner> prisonerCompare = new Comparator<Prisoner>() {
        @Override public int compare(Prisoner p1, Prisoner p2) {
            return (int) Math.signum(p1.getFitness() - p2.getFitness()); // Ascending
        }
	};
	
	public Breeder(int populationSize) {
		generateRandomPool(populationSize);
	}

	public void generateRandomPool(int populationSize){
		System.out.println("Generating random prisoner pool with " + populationSize + " prisoners.");
		for (int i = 0; i < populationSize; i++) {
			Prisoner p = new Prisoner(Settings.COMMUNICATION_NEURONS, Settings.NEURONS_AMOUNT, Settings.CONNECTIVITY);
			pool.add(p);
			if (i % 100 == 0) System.out.println("Prisoner " + i + " created.");
		}	
		System.out.println("Done.");
	}
	
	public void startEvolution(int generations){
		System.out.println("Starting evolution with " +  generations + " iterations.");
		for (int i = 0; i < generations; i++) {
			Director.updateFitness(pool);
			selectFittest();
			reproduceFittest();
			evaluate();
			System.out.println("Evolution round " + (i+1) + " finished.");
		}
	}
	
	private void evaluate() {
		double maxFitness = Collections.max(pool, prisonerCompare).getFitness();
		double minFitness = Collections.min(pool, prisonerCompare).getFitness();
		double meanFitness = 0.0;
		for (Prisoner prisoner : pool) {
			meanFitness += prisoner.getFitness();
		}
		meanFitness /= pool.size();
		
		System.out.println("The max Fitness is: " + maxFitness);
		System.out.println("The min Fitness is: " + minFitness);
		System.out.println("The mean Fitness is: " + meanFitness);
		
		this.maxFitnessrecord.add(maxFitness);
		this.minFitnessrecord.add(minFitness);
		this.meanFitnessrecord.add(meanFitness);
	}

	private void reproduceFittest() {
		Gson gson = new Gson();
		while(pool.size() < Settings.POPULATION_SIZE) {
			int i = (int)(Math.random()*pool.size());
			Prisoner newPris = gson.fromJson(gson.toJson(pool.get(i), Prisoner.class), Prisoner.class);
			newPris.mutate();
			pool.add(newPris);
		}
	}

	/**Removes the (1-TRUNCATION)*100 percent und keeps the TRUNCATION*100 percent
	 * 
	 */
	private void selectFittest() {
		Collections.sort(pool, prisonerCompare);
		
		for (int i = pool.size(); i < (int) (pool.size() * Settings.TRUNCATION); i--) {
			pool.remove(i);
		}
		
	}

	public ArrayList<Prisoner> getPool() {
		return pool;
	}
	
	
}
