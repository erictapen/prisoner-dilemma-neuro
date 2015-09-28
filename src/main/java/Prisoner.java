import java.util.ArrayList;


public class Prisoner {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	
	private Neuron answerNeuron;
	
	private ArrayList<Neuron> commInputNeurons = new ArrayList<Neuron>();
	
	private ArrayList<Neuron> commOutputNeurons = new ArrayList<Neuron>();
	
	private double fitness;
	
	/**
	 * 
	 * @param communicationNeurons
	 * @param amount
	 * @param connectivity
	 */
	public Prisoner(int communicationNeurons, int amount, int connectivity) {
		
		int n = (int) Math.round(gaussian(amount, amount/2.0));
		
		for (int i = 0; i < n; i++) {
			Neuron neuron = new Neuron(Math.random() * 2 - 1);
			this.neurons.add(neuron);
		}
		
		generateConnections(connectivity);
		//output and input neurons should not intersect
		ArrayList<Neuron> commNeurons = getRandomNeurons(2*communicationNeurons);
		this.commInputNeurons = (ArrayList<Neuron>) commNeurons.subList(0, communicationNeurons-1);
		this.commOutputNeurons = (ArrayList<Neuron>) commNeurons.subList(communicationNeurons, 2*communicationNeurons-1);
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
	
	private double gaussian(double my, double sigma) {
		return my;
	}
	
}
