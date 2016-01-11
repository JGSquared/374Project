package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.AbstractGraphCode;

public class GraphUsesCode extends AbstractGraphCode {

	public GraphUsesCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = new FileProperties();
		List<String> usesList = new ArrayList<String>();
		String name = getName(items.get("className"), "/");
		for (String s : items.keySet()) {
			if (s.contains("associated")) {
				String type = getName(items.get(s), "\\.");
				if (!type.equals("") && !fp.whiteList.contains(type) && !usesList.contains(type) && !type.equals(name)) {
					sb.append(name + " -> " + type
							+ " [arrowhead=\"open\", style=\"solid\"" + "];");
					usesList.add(type);
				}
			}
			else if (s.contains("field")) {
				String field = items.get(s);
				String[] fieldProps = field.split(":");
				String signature = getName(fieldProps[3], "\\.");
				if (!fieldProps[3].equals("EMPTY")) {
					if (!signature.equals("") && !fp.whiteList.contains(signature) && !usesList.contains(signature)) {
						sb.append(name + " -> " + signature
								+ " [arrowhead=\"open\", style=\"solid\"" + "];");
						usesList.add(signature);
					}
				}
			}
		}
		for (String s : items.keySet()) {
			if (s.contains("method")) {
				String method = items.get(s);
				String[] methodProps = method.split(":");
				String methodName = methodProps[1];
				if (methodName.equals("<init>")) {
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
				for (int i = 0; i < argTypes.size(); i++) {
					String argType = argTypes.get(i);
					if (!argType.equals("") && !fp.whiteList.contains(argType) && !usesList.contains(argType)) {
						sb.append(name + " -> " + argType
								+ " [arrowhead=\"open\", style=\"dashed\"" + "];");
						usesList.add(argType);
					}
				}
			}
			else if (s.contains("uses")) {
				String owner = getName(items.get(s), "/");
				//System.out.println("OWNER: " + owner);
				if (!owner.equals("") && !fp.whiteList.contains(owner) && !usesList.contains(owner) && !owner.equals(name)) {
					sb.append(name + " -> " + owner
							+ " [arrowhead=\"open\", style=\"dashed\"" + "];");
					usesList.add(owner);
				}
			}
		}	
		return sb.toString();
	}
}
