package problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileProperties {
	private static FileProperties uniqueInstance;
	public String graphVizPath = "";
	public String flags = "";
	public String fileIn = "";
	public String fileOut = "";
	public String sdEditPath = "";
	public List<String> whiteList = new ArrayList<String>();
	
	public static FileProperties getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new FileProperties();
		}
		return uniqueInstance;
	}
	
	private FileProperties() {
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
				case "whiteList":
					String[] temp = lineArray[1].trim().split(",");
					for (String s: temp) {
						whiteList.add(s);
					}
					break;
				case "sdEditPath":
					sdEditPath = lineArray[1].trim();
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
