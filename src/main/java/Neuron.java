import java.util.ArrayList;


public class Neuron {
	ArrayList<Neuron> parents = new ArrayList<Neuron>();
	
	ArrayList<Neuron> children = new ArrayList<Neuron>();
	
	ArrayList<Double> weights = new ArrayList<Double>();
	
	double activation;
	
	double oldActivation;
	
	
	public Neuron(double activation) {
		super();
		this.activation = activation;
		this.oldActivation = 0.0;
	}

	public void update() {
		//cumulate
		double sum = 0.0;
		for(int i=0; i<this.parents.size(); i++) {
			sum += this.weights.get(i) * this.parents.get(i).getOldActivation();
		}
		//activate
		this.activation = Math.tanh(sum);
	}
	
	/** copies activation into oldActivation. Must be executed before any connected neuron update()
	 * 
	 */
	public void prepareNextStep() {
		this.oldActivation = this.activation;
	}

	public double getOldActivation() {
		return oldActivation;
	}
	
	
}
