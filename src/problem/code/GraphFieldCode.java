package problem.code;

import java.util.HashMap;

import problem.Helpers;
import problem.api.IGraphCode;

public class GraphFieldCode implements IGraphCode {

	public GraphFieldCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		for (String s : items.keySet()) {
			if (s.contains("field")) {
				String field = items.get(s);
				String[] fieldProperties = field.split(":");
				int access = Integer.parseInt(fieldProperties[0]);
				String name = fieldProperties[1];
				if (name.equals("<init>")) {
					// Bad field, causes errors in GraphViz
					continue;
				}
				String type = Helpers.getName(fieldProperties[2], "\\.");

				sb.append(Helpers.getAccessSymbol(access) + " ");

				sb.append(name + " : " + type + "\\l");
			}
		}
		sb.append("|");
		
		return sb.toString();
	}

}
