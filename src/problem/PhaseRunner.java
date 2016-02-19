package problem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

import problem.api.IPhase;
import problem.code.AbstractCodeWriter;
import problem.code.UMLCodeWriter;

public class PhaseRunner {
	private HashMap<String, IPhase> phases;
	private ConfigProperties props;

	public PhaseRunner() {
		phases = new HashMap<>();
		props = ConfigProperties.getInstance();
	}
	
	public void addPhase(String name, IPhase phase) {
		phases.put(name, phase);
	}
	
	public void run() {
		String[] phaseOrder = props.getPhases();
		for (int i = 0; i < phaseOrder.length; i++) {
			IPhase next = phases.get(phaseOrder[i].trim());
			if (next != null) {
				next.execute();
			}
		}
		
		createGraph();
	}
	
	public void createGraph() {
		new IPhase() {
			
			@Override
			public void execute() {
				try {
					OutputStream outStream = new FileOutputStream(props.getOutputFolder() + "\\" + Constants.OUTFILE_NAME);
					AbstractCodeWriter writer = new UMLCodeWriter(outStream);
					writer.generateGraph();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}.execute();
	}

}
