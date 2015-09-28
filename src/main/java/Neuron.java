import java.util.ArrayList;


public class Neuron {
	private ArrayList<Neuron> parents = new ArrayList<Neuron>();
	
	private ArrayList<Neuron> children = new ArrayList<Neuron>();
	
	private ArrayList<Double> weights = new ArrayList<Double>();
	
	private double activation;
	
	private double oldActivation;
	
	private int id;
	
	
	public Neuron(double activation, int id) {
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
	
	/** builds a new connection, pointing from this to newChild
	 * @param newChild
	 */
	public void connect(Neuron newChild, double newWeight) {
		this.children.add(newChild);
		newChild.getWeights().add(newWeight);
		newChild.getParents().add(this);
	}

	public double getOldActivation() {
		return oldActivation;
	}

	public double getActivation() {
		return activation;
	}

	public void setActivation(double activation) {
		this.activation = activation;
	}

	public ArrayList<Neuron> getParents() {
		return parents;
	}

	public ArrayList<Double> getWeights() {
		return weights;
	}

	public int getId() {
		return id;
	}
	
	public boolean isActivated() {
		return this.activation > 0;
	}
	
	
	
	
	
}
