package problem;

import java.util.ArrayList;
import java.util.HashMap;

import problem.api.IGraphCode;

public class GraphMethodCode extends IGraphCode {

	public GraphMethodCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		for(String s : items.keySet()) {
			if (s.contains("method")) {
				String method = items.get(s);
				String[] methodProps = method.split(":");

				int access = Integer.parseInt(methodProps[0]);
				String name = methodProps[1];
				if (name.equals("<init>")) {
					// Bad method, causes errors in GraphViz
					continue;
				}
				String argTypesString = methodProps[2];
				argTypesString = argTypesString.replaceAll("\\[", "");
				argTypesString = argTypesString.replaceAll("\\]", "");

				String[] splitArgs = argTypesString.split(",");
				ArrayList<String> argTypes = new ArrayList<String>();
				for (int i = 0; i < splitArgs.length; i++) {
					argTypes.add(getName(splitArgs[i].trim(), "\\."));
				}

				String returnType = getName(methodProps[3], "\\.");

				sb.append(getAccessSymbol(access) + " ");

				sb.append(name
						+ argTypes.toString().replaceAll("\\[", "(")
								.replaceAll("\\]", ")") + " : " + returnType
						+ "\\l");
			}
		}
		
		return sb.toString();
	}

}
