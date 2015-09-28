import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


public class Neuron {
	// List of Neurons would lead to JSon-incompatible Objects (because of the circles)
	private ArrayList<Integer> parents = new ArrayList<Integer>();
	
	private ArrayList<Integer> children = new ArrayList<Integer>();
	
	private ArrayList<Double> weights = new ArrayList<Double>();
	
	private double activation;
	
	private double oldActivation;
	
	private int id;
	
	
	public Neuron(double activation, int id) {
		super();
		this.activation = activation;
		this.oldActivation = 0.0;
		this.id = id;
	}

	public Neuron(ArrayList<Integer> parents, ArrayList<Integer> children, ArrayList<Double> weights, double activation,
			double oldActivation, int id) {
		super();
		this.parents = parents;
		this.children = children;
		this.weights = weights;
		this.activation = activation;
		this.oldActivation = oldActivation;
		this.id = id;
	}

	public void update(ArrayList<Neuron> neurons) {
		//cumulate
		double sum = 0.0;
		for(int i=0; i<this.parents.size(); i++) {
			sum += this.weights.get(i) * neurons.get(parents.get(i)).getOldActivation();
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
	public void connect(ArrayList<Neuron> neurons, int newChild, double newWeight) {
		this.children.add(newChild);
		neurons.get(newChild).getWeights().add(newWeight);
		neurons.get(newChild).getParents().add(id);
	}
	
	/**
	 * Removes connection from neuron #oldChild from the list of children
	 * @param neurons
	 * @param oldChild
	 */
	public void disconnect(ArrayList<Neuron> neurons, int oldChild) {
		int oldChildID = children.get(oldChild);
		this.children.remove(oldChild);
		neurons.get(oldChildID).getParents().remove(neurons.get(oldChildID).getParents().indexOf(this.id));
	}

	public void mutateWeights() {
		for (int i = 0; i < weights.size(); i++) {
			weights.set(i, Math.random() * 2 - 1);
		}
	}	

	public void mutateConnections(ArrayList<Neuron> neurons, int n) {
		for (int i = 0; i < n; i++) {
			disconnect(neurons, (int) (Math.random() * children.size()));
			connect(neurons, (int) (Math.random() * neurons.size()), Math.random() * 2 - 1);
		}
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

	public ArrayList<Integer> getParents() {
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
