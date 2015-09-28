import java.io.FileWriter;
import java.io.IOException;


public class FileHandler {
	
	public void exportPoolToFile(String ofile, Breeder brdr) {
		try{
			FileWriter writer = new FileWriter(ofile);
			writer.append("Pool1");
			for(Prisoner pr : brdr.getPool())  {
				writer.append("\tPrisoner");
				for(Neuron n : pr.getNeurons()) {
					String append = "\t\tNeuron %id"
							+ "\t\t\tparents = %parentIDs"
							+ "\t\t\tweights = %weights"
							+ "\t\t\tactivation = %activation";
					append = append.replaceAll("%id", Integer.toString(n.getId()));
					String parentIDs = "";
					for(Neuron x : n.getParents()) parentIDs += "," + x.getId();
					parentIDs = parentIDs.substring(1);
					append = append.replaceAll("%parentIDs", parentIDs);
					String weights = "";
					for(Double x : n.getWeights()) weights += "," + Double.toString(x);
					weights = weights.substring(1);
					append = append.replaceAll("%weights", weights);
					append = append.replaceAll("%activation", Double.toString(n.getActivation()));
					writer.append(append);
				}
			}
			writer.close();
		} catch(IOException e)
		{
			System.out.println("Problem occured:");
			e.printStackTrace();
		}
		System.out.println("Export completed.");
	}
}
