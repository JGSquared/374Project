package problem.code;

import java.util.ArrayList;
import java.util.List;

import problem.Constants;
import problem.FileProperties;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphUsesCode implements IGraphCode {

	public GraphUsesCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		List<String> usedList = new ArrayList<String>();
		String name = Helpers.getName(getters.getClassName());
		
		ArrayList<String> associatedList = getters.getClassAssociates();
		ArrayList<String> fieldList = getters.getFieldNames();
		ArrayList<String> methodList = getters.getMethodNames();
		ArrayList<String> usesList = getters.getClassUses();
		
		String type;
		for (String associated : associatedList) {
			type = Helpers.getName(associated);
			if (!Helpers.isClassNameValid(type)) {
				continue;
			}
			if (!type.equals("") && !fp.whiteList.contains(type) && !usedList.contains(type) && !type.equals(name)) {
				sb.append(name + " -> " + type
						+ " [arrowhead=\"open\", style=\"solid\", label=\"" + Constants.ARROW_OFFSET + "\"" + "];");
				usedList.add(type);
			}
		}
		
		String signature;
		for (String fieldName : fieldList) {
			signature = Helpers.getName(getters.getFieldSignature(fieldName));
			if (!Helpers.isClassNameValid(signature)) {
				continue;
			}
			if (!signature.equals("EMPTY")) {
				if (!signature.equals("") && !fp.whiteList.contains(signature) && !usedList.contains(signature)) {
					sb.append(name + " -> " + signature
							+ " [arrowhead=\"open\", style=\"solid\", label=\"" + Constants.ARROW_OFFSET+ "\"" + "];");
					usedList.add(signature);
				}
			}
		}
		
		String[] argsArray;
		ArrayList<String> argTypes;
		for (String methodName : methodList) {
//			if (methodName.equals("<init>")) {
//				// Bad method, causes errors in GraphViz
//				continue;
//			}

			argsArray = getters.getMethodArgTypes(methodName);
			argTypes = new ArrayList<String>();
			for (int i = 0; i < argsArray.length; i++) {
				argTypes.add(Helpers.getName(argsArray[i].trim()));
			}
			for (int i = 0; i < argTypes.size(); i++) {
				String argType = argTypes.get(i);
				if (!Helpers.isClassNameValid(argType)) {
					continue;
				}
				if (!argType.equals("") && !fp.whiteList.contains(argType) && !usedList.contains(argType)) {
					sb.append(name + " -> " + argType
							+ " [arrowhead=\"open\", style=\"dashed\"" + "];");
					usedList.add(argType);
				}
			}
		}
		
		String owner;
		for (String uses : usesList) {
			owner = Helpers.getName(uses);
			if (!Helpers.isClassNameValid(owner)) {
				continue;
			}
			if (!owner.equals("") && !fp.whiteList.contains(owner) && !usedList.contains(owner) && !owner.equals(name)) {
				sb.append(name + " -> " + owner
						+ " [arrowhead=\"open\", style=\"dashed\"" + "];");
				usedList.add(owner);
			}
		}
			
		return sb.toString();
	}
}
