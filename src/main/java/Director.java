import java.util.ArrayList;


public class Director {
	public static void updateFitness(ArrayList<Neuron> pool) {
		
	}
	
	private static ArrayList<Integer> runMatch(Prisoner p1, Prisoner p2, int iterations) {
		p1.randomizeActivations();
		p2.randomizeActivations();
		for (int i = 0; i < iterations; i++) {
			p1.update();
			p2.update();
			p1.communicateTo(p2);
			p2.communicateTo(p1);
		}
		boolean answer1 = p1.getAnswerNeuron().isActivated();
		boolean answer2 = p2.getAnswerNeuron().isActivated();
		ArrayList<Integer> res = new ArrayList<Integer>();
		//true is betrayal
		if(answer1 && answer2) {
			res.add(new Integer(2));
			res.add(new Integer(2));
		} else if(answer1 && !answer2) {
			res.add(new Integer(0));
			res.add(new Integer(3));
		} else if(!answer1 && answer2) {
			res.add(new Integer(3));
			res.add(new Integer(0));
		} else if(!answer1 && !answer2) {
			res.add(new Integer(1));
			res.add(new Integer(1));
		}
		return res;
	}
}
