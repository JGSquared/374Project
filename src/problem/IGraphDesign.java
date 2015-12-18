package problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface IGraphDesign {
	public void addGraphCode(HashMap<String, String> items);
	public void initializeGraph();
	public void closeGraph();
	public void generateGraph() throws IOException;
}
