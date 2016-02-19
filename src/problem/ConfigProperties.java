package problem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
	private volatile static ConfigProperties uniqueController;
	private Properties props;
	private String inputFolder;
	private String[] inputClasses;
	private String outputFolder;
	private String dotPath;
	private String[] phases;
	private ConfigProperties() {
	}
	
	public static ConfigProperties getInstance() {
		if (uniqueController == null) {
			synchronized (ClassStorage.class) {
				if (uniqueController == null) {
					uniqueController = new ConfigProperties();
				}
			}
		}
		return uniqueController;
	}
	
	public void setupConfig(String path) throws IOException {
		props = new Properties();
		FileInputStream in = new FileInputStream(path);
		props.load(in);
		in.close();
		inputFolder = props.get("Input-Folder").toString();
		inputClasses = props.get("Input-Classes").toString().split(",");
		outputFolder = props.get("Output-Directory").toString();
		dotPath = props.get("Dot-Path").toString();
		phases = props.get("Phases").toString().split(",");
		System.out.println("CONFIRMED");
		System.out.println(inputFolder);
		for (String ic : inputClasses) {
			System.out.println(ic);
		}
	}

	public String getInputFolder() {
		return inputFolder;
	}

	public String[] getInputClasses() {
		return inputClasses;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public String getDotPath() {
		return dotPath;
	}

	public String[] getPhases() {
		return phases;
	}
	
	public String getProperty(String key) {
		return props.getProperty(key, "");
	}
	
}
