package problem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
	private volatile static ConfigProperties uniqueController;
	private static String inputFolder;
	private static String[] inputClasses;
	private static String outputFolder;
	private static String dotPath;
	private static String[] phases;
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
		Properties props = new Properties();
		FileInputStream in = new FileInputStream(path);
		props.load(in);
		in.close();
		inputFolder = props.get("Input-Folder").toString();
		inputClasses = props.get("Input-Classes").toString().split(",");
		outputFolder = props.get("Output-Directory").toString();
		dotPath = props.get("Dot-Path").toString();
		phases = props.get("Phases").toString().split(",");
	}
}
