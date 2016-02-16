package problem.code;

import java.util.ArrayList;

import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphFieldCode implements IGraphCode {

	public GraphFieldCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> fields = getters.getFieldNames();
		int access;
		String type;
		for (String name : fields) {
			if (name.equals("<init")) {
				// Bad field, causes errors in GraphViz
				continue;
			}
			access = getters.getFieldAccess(name);
			type = Helpers.getName(getters.getFieldType(name));
			
			sb.append(Helpers.getAccessSymbol(access) + " ");
			sb.append(name + " : " + type + "\\l");
		}

		sb.append("|");
		
		return sb.toString();
	}

}
