package problem.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface IGraphDesign {
	public void addGraphCode(HashMap<String, String> items);
	public void initializeGraph();
	public void closeGraph();
	public void generateGraph() throws IOException;
	public void addCodeGetter(GraphCode getter);
	public void removeCodeGetter(GraphCode getter);
	public void useDefault();
}
