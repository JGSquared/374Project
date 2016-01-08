package problem;

import java.util.HashMap;

import problem.api.AbstractGraphCode;

public class GraphClassCloserCode extends AbstractGraphCode {

	public GraphClassCloserCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		return "}\"\n];";
	}
}
