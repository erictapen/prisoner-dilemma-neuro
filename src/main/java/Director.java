import java.util.ArrayList;


public class Director {
	
	private static ArrayList<ArrayList<Double>> commData;
	
	/**
	 * @param pool
	 * @param fightPercentage The likelihood, that a certain prisoner fights against another one
	 */
	public static void updateFitness(ArrayList<Prisoner> pool, boolean recordComm) {
		int iterations = (int)(Settings.FIGHT_PERCENTAGE*pool.size());
		if(recordComm) {
			commData = new ArrayList<ArrayList<Double>>();
			for(int i=0; i<iterations; i++) commData.add(new ArrayList<Double>());
		}
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<Integer> plays = new ArrayList<Integer>();
		for (int i = 0; i < pool.size(); i++) {
			scores.add(new Integer(0));
			plays.add(new Integer(0));
		}
		for(int i=0; i<pool.size(); i++) {
			for (int j = 0; j < iterations; j++) {
				Prisoner p2 = pool.get((int)(Math.random()*pool.size()));
				ArrayList<Integer> matchResult = runMatch(pool.get(i), p2, recordComm);
				scores.set(i, new Integer(scores.get(i).intValue() + matchResult.get(0)));
				scores.set(j, new Integer(scores.get(j).intValue() + matchResult.get(1)));
				plays.set(i, new Integer(plays.get(i).intValue()+1));
				plays.set(j, new Integer(plays.get(j).intValue()+1));
			}
		}
		for (int i = 0; i < pool.size(); i++) {
			pool.get(i).setFitness(((double)scores.get(i)) / plays.get(i));
		}
	}
	
	private static ArrayList<Integer> runMatch(Prisoner p1, Prisoner p2, boolean recordComm) {
		p1.resetActivations();
		p2.resetActivations();
		for (int i = 0; i < Settings.THINKING_STEPS; i++) {
			p1.update();
			p2.update();
			p1.communicateTo(p2);
			p2.communicateTo(p1);
			if(recordComm) { //write Communication to commData
				commData.get(i).add(p1.getCommInputNeurons().get(0).getActivation());
				commData.get(i).add(p2.getCommInputNeurons().get(0).getActivation());
			}
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

	public static ArrayList<ArrayList<Double>> getCommData() {
		return commData;
	}
}
