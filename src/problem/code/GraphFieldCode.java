package problem.code;

import java.util.HashMap;

import problem.api.AbstractGraphCode;

public class GraphFieldCode extends AbstractGraphCode {

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
				String type = getName(fieldProperties[2], "\\.");

				sb.append(getAccessSymbol(access) + " ");

				sb.append(name + " : " + type + "\\l");
			}
		}
		sb.append("|");
		
		return sb.toString();
	}

}
