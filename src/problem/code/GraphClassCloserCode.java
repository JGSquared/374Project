package problem.code;

import java.util.HashMap;

import problem.api.IGraphCode;

public class GraphClassCloserCode implements IGraphCode {

	public GraphClassCloserCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		return "}\"\n];";
	}
}
