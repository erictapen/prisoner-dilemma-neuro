import java.util.ArrayList;


public class Prisoner {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	
	private Neuron answerNeuron;
	
	private ArrayList<Neuron> commInputNeurons = new ArrayList<Neuron>();
	
	private ArrayList<Neuron> commOutputNeurons = new ArrayList<Neuron>();
	
	private double fitness;
	
	public Prisoner(ArrayList<Neuron> neurons, Neuron answerNeuron, ArrayList<Neuron> commInputNeurons,
			ArrayList<Neuron> commOutputNeurons, double fitness) {
		super();
		this.neurons = neurons;
		this.answerNeuron = answerNeuron;
		this.commInputNeurons = commInputNeurons;
		this.commOutputNeurons = commOutputNeurons;
		this.fitness = fitness;
	}

	/**
	 * 
	 * @param communicationNeurons
	 * @param amount
	 * @param connectivity
	 */
	public Prisoner(int communicationNeurons, int amount, int connectivity) {
		
		int n = (int) Math.round(gaussian(amount, amount/2.0));
		
		for (int i = 0; i < n; i++) {
			Neuron neuron = new Neuron(Math.random() * 2 - 1, i);
			this.neurons.add(neuron);
		}
		
		generateConnections(connectivity);
		//output and input neurons should not intersect
		ArrayList<Neuron> specialNeurons = getRandomNeurons(2*communicationNeurons + 1);
		this.commInputNeurons = (ArrayList<Neuron>) specialNeurons.subList(0, communicationNeurons-1);
		this.commOutputNeurons = (ArrayList<Neuron>) specialNeurons.subList(communicationNeurons, 2*communicationNeurons-1);
		this.answerNeuron = specialNeurons.get(2*communicationNeurons);
	}
	
	private ArrayList<Neuron> getRandomNeurons(int n) {
		ArrayList<Neuron> res = new ArrayList<Neuron>();
		for (int i = 0; i < n; i++) {
			Neuron toAdd = this.neurons.get((int) Math.random()*this.neurons.size());
			if(!res.contains(toAdd)) res.add(toAdd);
			else i--;
		}
		return res;
	}

	private void generateConnections(int connectivity) {
		int connections = 0;
		ArrayList<Neuron> newParents = new ArrayList<Neuron>();
		for (Neuron x : this.neurons) {
			connections = (int) Math.round(gaussian(connectivity, connectivity/2.0));
			
			for (int i = 0; i < connections; i++) {
				Neuron toAdd = this.neurons.get((int) Math.random()*this.neurons.size());
				if(!newParents.contains(toAdd)) newParents.add(toAdd);
				else i--;
			}
			for(Neuron parent : newParents) {
				parent.connect(x, Math.random()*2 - 1.0);
			}
		}
	}

	public void update() {
		for(Neuron x : this.neurons) {
			x.prepareNextStep();
		}
		for(Neuron x : this.neurons) {
			x.update();
		}
	}
	
	public void randomizeActivations() {
		for(Neuron x : this.neurons) {
			x.setActivation(Math.random()*2 - 1);
		}
	}

	/** One way messag from this to p
	 * @param p2
	 */
	public void communicateTo(Prisoner p) {
		for (int i = 0; i < this.commOutputNeurons.size(); i++) {
			double message = this.commOutputNeurons.get(i).getActivation();
			p.getCommInputNeurons().get(i).setActivation(message);
		}
	}
	
	private double gaussian(double my, double sigma) {
		return my;
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
