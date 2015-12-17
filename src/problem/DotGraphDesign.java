package problem;

import java.util.HashMap;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void addGraphCode(HashMap<String, String> items) {
		
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT\n");
	}

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
		
	}

}
