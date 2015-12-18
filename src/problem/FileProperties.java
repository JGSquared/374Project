package problem;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileProperties {
	public String graphVizPath = "";
	public String flags = "";
	public String fileIn = "";
	public String fileOut = "";
	
	public FileProperties() {
		// Read in properties
		try (BufferedReader br = new BufferedReader(new FileReader(
				"./input_output/properties.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split("->");
				switch (lineArray[0]) {
				case "graphVizPath":
					graphVizPath = lineArray[1].trim();
					break;
				case "flags":
					flags = lineArray[1].trim();
					break;
				case "fileIn":
					fileIn = lineArray[1].trim();
					break;
				case "fileOut":
					fileOut = lineArray[1].trim();
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
