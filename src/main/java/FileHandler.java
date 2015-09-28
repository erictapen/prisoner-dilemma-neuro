import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;


public class FileHandler {
	
	public void exportPoolToFile(String ofile, Breeder brdr) {
		Gson gson = new Gson();

		// convert java object to JSON format,
		// and returned as JSON formatted string
		String json = gson.toJson(brdr);

		try {
			//write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(ofile);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(json);
	}
	
	public Breeder importPoolFromFile(String ifile) {
		Gson gson = new Gson();
		Breeder res = null;
		try {

			BufferedReader br = new BufferedReader(
				new FileReader(ifile));

			//convert the json string back to object
			res = gson.fromJson(br, Breeder.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
