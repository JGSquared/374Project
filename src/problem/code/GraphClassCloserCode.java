package problem.code;

import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphClassCloserCode implements IGraphCode {

	public GraphClassCloserCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		return "}\"\n];";
	}
}
