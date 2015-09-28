import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FileHandler {
	
	public exportPoolToFile(Breeder br) {
		try (BufferedReader br = new BufferedReader(new FileReader(ifile))) {
			
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + ifile + "\" not found. Abort.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("IOException. Abort.");
			e.printStackTrace();
			return null;
		}
	}
}
