package problem.api;

import java.io.IOException;

public interface IDesignParser {
	
	public void parse(String[] args, IGraphDesign graphDesigner) throws IOException;

}
