package problem;

import java.util.HashMap;

import problem.api.GraphCode;

public class GraphClassCloserCode extends GraphCode {

	public GraphClassCloserCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		return "}\"\n];";
	}
}
