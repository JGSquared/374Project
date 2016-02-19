package problem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JProgressBar;

import problem.api.IPhase;

public class ConfigProperties {
	private volatile static ConfigProperties uniqueController;
	private Properties props;
	private String inputFolder;
	private String[] inputClasses;
	private String outputFolder;
	private String dotPath;
	private String[] phases;
	private JProgressBar progressBar;
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
//		progressBar.setValue(progressBar.getValue() + 20);
		FileInputStream in = new FileInputStream(path);
//		progressBar.setValue(progressBar.getValue() + 10);
		props.load(in);
//		progressBar.setValue(progressBar.getValue() + 10);
		in.close();
		inputFolder = props.get("Input-Folder").toString();
//		progressBar.setValue(progressBar.getValue() + 10);
		inputClasses = props.get("Input-Classes").toString().split(",");
//		progressBar.setValue(progressBar.getValue() + 20);
		outputFolder = props.get("Output-Directory").toString();
//		progressBar.setValue(progressBar.getValue() + 10);
		dotPath = props.get("Dot-Path").toString();
//		progressBar.setValue(progressBar.getValue() + 10);
		phases = props.get("Phases").toString().split(",");
//		progressBar.setValue(progressBar.getValue() + 10);
		System.out.println("CONFIRMED");
		System.out.println(inputFolder);
//		for (String ic : inputClasses) {
//			System.out.println(ic);
//		}
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
	
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
}
