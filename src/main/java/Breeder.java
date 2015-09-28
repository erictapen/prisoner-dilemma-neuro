import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Breeder {
	ArrayList<Prisoner> pool = new ArrayList<Prisoner>();
	
	public Breeder(int populationSize) {
		generateRandomPool(populationSize);
	}

	public void generateRandomPool(int populationSize){
		for (int i = 0; i < populationSize; i++) {
			Prisoner p = new Prisoner(Settings.COMMUNICATION_NEURONS, Settings.NEURONS_AMOUNT, Settings.CONNECTIVITY);
			pool.add(p);
		}		
	}
	
	public void startEvolution(int generations){
		for (int i = 0; i < generations; i++) {
			Director.updateFitness(pool);
			selectFittest();
			reproduceFittest();
		}
	}
	
	private void reproduceFittest() {
		while(pool.size() < Settings.POPULATION_SIZE) {
			int i = (int)(Math.random()*pool.size());
			//TODO add cloned and mutated object
		}
	}

	/**Removes the (1-TRUNCATION)*100 percent und keeps the TRUNCATION*100 percent
	 * 
	 */
	private void selectFittest() {
		Collections.sort(pool, new Comparator<Prisoner>() {
	        @Override public int compare(Prisoner p1, Prisoner p2) {
	        	int res = 0;
	        	if (p1.getFitness() > p2.getFitness()) res = 1;
	            return res; // Ascending
	        }
	    });
		
		for (int i = pool.size(); i < (int) (pool.size() * Settings.TRUNCATION); i--) {
			pool.remove(i);
		}
		
	}

	public ArrayList<Prisoner> getPool() {
		return pool;
	}
	
	
}
