package problem.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.FileProperties;
import problem.Helpers;
import problem.api.IGraphCode;

public class GraphUsesCode implements IGraphCode {

	public GraphUsesCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		List<String> usesList = new ArrayList<String>();
		String name = Helpers.getName(items.get("className"), "/");
		for (String s : items.keySet()) {
			if (s.contains("associated")) {
				String type = Helpers.getName(items.get(s), "\\.");
				if (!Helpers.isClassNameValid(type)) {
					continue;
				}
				if (!type.equals("") && !fp.whiteList.contains(type) && !usesList.contains(type) && !type.equals(name)) {
					sb.append(name + " -> " + type
							+ " [arrowhead=\"open\", style=\"solid\", label=\"" + Constants.ARROW_OFFSET + "\"" + "];");
					usesList.add(type);
				}
			}
			else if (s.contains("field")) {
				String field = items.get(s);
				String[] fieldProps = field.split(":");
				String signature = Helpers.getName(fieldProps[3], "\\.");
				if (!Helpers.isClassNameValid(signature)) {
					continue;
				}
				if (!fieldProps[3].equals("EMPTY")) {
					if (!signature.equals("") && !fp.whiteList.contains(signature) && !usesList.contains(signature)) {
						sb.append(name + " -> " + signature
								+ " [arrowhead=\"open\", style=\"solid\", label=\"" + Constants.ARROW_OFFSET+ "\"" + "];");
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
					argTypes.add(Helpers.getName(splitArgs[i].trim(), "\\."));
				}
				for (int i = 0; i < argTypes.size(); i++) {
					String argType = argTypes.get(i);
					if (!Helpers.isClassNameValid(argType)) {
						continue;
					}
					if (!argType.equals("") && !fp.whiteList.contains(argType) && !usesList.contains(argType)) {
						sb.append(name + " -> " + argType
								+ " [arrowhead=\"open\", style=\"dashed\"" + "];");
						usesList.add(argType);
					}
				}
			}
			else if (s.contains("uses")) {
				String owner = Helpers.getName(items.get(s), "/");
				if (!Helpers.isClassNameValid(owner)) {
					continue;
				}
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
