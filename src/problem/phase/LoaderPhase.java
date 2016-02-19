package problem.phase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import problem.ClassDesignParser;
import problem.ConfigProperties;
import problem.DotGraphDesign;
import problem.Helpers;
import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.api.IPhase;

public class LoaderPhase implements IPhase {

	@Override
	public void execute() {
		ConfigProperties props = ConfigProperties.getInstance();
		ArrayList<String> classes = new ArrayList<>();
		listf(props.getInputFolder(), classes);
		for (String s : props.getInputClasses()) {
			classes.add(s);
		}
		String[] args = (String[]) classes.toArray();
		IDesignParser dp = new ClassDesignParser();
		IGraphDesign graphDesigner = new DotGraphDesign();
		try {
			dp.parse(args, graphDesigner);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void listf(String directoryName, ArrayList<String> classes) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            classes.add(Helpers.getPackageFromPath(file.getAbsolutePath(), directoryName));
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), classes);
	        }
	    }
	}

}
