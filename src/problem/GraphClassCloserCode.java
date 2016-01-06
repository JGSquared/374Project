package problem;

import java.util.HashMap;

import problem.api.IGraphCode;

public class GraphClassCloserCode extends IGraphCode {

	public GraphClassCloserCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		return "}\"\n];";
	}

}
