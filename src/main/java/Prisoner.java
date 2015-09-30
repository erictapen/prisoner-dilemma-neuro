import java.util.ArrayList;
import java.util.Collections;


public class Prisoner {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	
	private ArrayList<Double> initialActivations = new ArrayList<Double>();
	
	private Neuron answerNeuron;
	
	private ArrayList<Neuron> commInputNeurons = new ArrayList<Neuron>();
	
	private ArrayList<Neuron> commOutputNeurons = new ArrayList<Neuron>();
	
	private double fitness;
	
	public Prisoner(ArrayList<Neuron> neurons, Neuron answerNeuron, ArrayList<Neuron> commInputNeurons,
			ArrayList<Neuron> commOutputNeurons, ArrayList<Double> initialActivations, double fitness) {
		super();
		this.neurons = neurons;
		this.answerNeuron = answerNeuron;
		this.commInputNeurons = commInputNeurons;
		this.commOutputNeurons = commOutputNeurons;
		this.initialActivations = initialActivations;
		this.fitness = fitness;
	}
	
	public Prisoner(int communicationNeurons, int amount, int connectivity) {
		int n = (int) Math.round(gaussian(amount, amount/2.0));
				
		for (int i = 0; i < n; i++) {
			initialActivations.add(Math.random() * 2 - 1);
			Neuron neuron = new Neuron(initialActivations.get(i), i);
			this.neurons.add(neuron);
		}
		
		generateConnections(connectivity);
		//output and input neurons should not intersect
		ArrayList<Neuron> specialNeurons = getRandomNeurons(2*communicationNeurons + 1);
		for (int i = 0; i < specialNeurons.size(); i++) {
			if (i < communicationNeurons - 1)
				this.commInputNeurons.add(specialNeurons.get(i));
			else if (i > communicationNeurons && i < 2*communicationNeurons - 1)
				this.commOutputNeurons.add(specialNeurons.get(i));
			else
				this.answerNeuron = specialNeurons.get(i);
		}
	}

	private void generateConnections(int connectivity) {
		for (int i = 0; i < neurons.size(); i++) {
			int connections = (int) Math.round(gaussian(connectivity, connectivity/2.0));
			//System.out.println("will do " + connections + " connections.");
			ArrayList<Neuron> newParents = getRandomNeurons(connections);
			for(Neuron parent : newParents) {
				parent.connect(neurons, i, Math.random()*2 - 1.0);
			}
		}
	}
	
	private ArrayList<Neuron> getRandomNeurons(int n) {
		if(n > this.neurons.size()) {
			System.out.println("There are too many Communication Neurons.");
			n = this.neurons.size();
		}
		
		ArrayList<Integer> numbers = getRandomNumbers();
		
		ArrayList<Neuron> res = new ArrayList<Neuron>();
		for (int i = 0; i < n; i++) {
			res.add(neurons.get(numbers.get(i)));
		}
		return res;
	}

	private ArrayList<Integer> getRandomNumbers() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < neurons.size(); i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		return numbers;
	}

	public void update() {
		for(Neuron x : this.neurons) {
			x.prepareNextStep();
		}
		for(Neuron x : this.neurons) {
			x.update(neurons);
		}
	}

	public void mutate() {
		int mutations = (int) gaussian(Settings.MUTATION_RATE, Settings.MUTATION_RATE/2);
		
		for (int i = 0; i < mutations; i++) {
			double mutationType = Math.random();
			if (mutationType < Settings.WEIGHT_MUTATION_PROPABILITY) {
				neurons.get((int) Math.random() * neurons.size()).mutateWeights(Settings.WEIGHT_MUTATION_INTENSITY);
			} else if (mutationType > Settings.CONNECTION_MUTATION_PROPABILITY
					&& mutationType < Settings.WEIGHT_MUTATION_PROPABILITY + Settings.CONNECTION_MUTATION_PROPABILITY){
				neurons.get((int) Math.random() * neurons.size()).mutateConnections(neurons, Settings.NUMBER_OF_CONNECTION_MUTATIONS);
			} else {
				this.mutateInitialActivations();
			}
		}
		
	}

	public void resetActivations() {
		for (int i = 0; i < this.neurons.size(); i++) {
			this.neurons.get(i).setActivation(this.initialActivations.get(i));
		}
	}

	public void mutateInitialActivations() {
		for (int i = 0; i < initialActivations.size(); i++) {
			initialActivations.set(i, Math.random() * 2 - 1);
		}
	}
	
	/*public void randomizeActivations() {
		for(Neuron x : this.neurons) {
			x.setActivation(Math.random()*2 - 1);
		}
	}*/

	/** One way message from this to p
	 * @param p2
	 */
	public void communicateTo(Prisoner p) {
		for (int i = 0; i < this.commOutputNeurons.size(); i++) {
			double message = this.commOutputNeurons.get(i).getActivation();
			p.getCommInputNeurons().get(i).setActivation(message);
		}
	}
	
	public static double gaussian(double my, double sigma) {
		double x1, x2, w, y, res;
		 
        do {
        	x1 = 2.0 * Math.random() - 1.0;
        	x2 = 2.0 * Math.random() - 1.0;
        	w = x1 * x1 + x2 * x2;
        } while (w >= 1.0);

        w = Math.sqrt((-2.0 * Math.log(w)) / w);
        y = x1 * w;
        res = y * sigma + my;
        
		return res > 0 ? res : 0;
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	public ArrayList<Neuron> getCommInputNeurons() {
		return commInputNeurons;
	}

	public ArrayList<Neuron> getCommOutputNeurons() {
		return commOutputNeurons;
	}

	public Neuron getAnswerNeuron() {
		return answerNeuron;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
}
