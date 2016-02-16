package problem.code;

import java.util.ArrayList;

import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphMethodCode implements IGraphCode {

	public GraphMethodCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> methods = getters.getMethodNames();

		int access;
		String[] argsArray;
		String returnType;
		ArrayList<String> argTypes;
		for (String name : methods) {
			if (name.equals("<init>")) {
				// Bad method, causes errors in GraphViz
				continue;
			}
			access = getters.getMethodAccess(name);
			argsArray = getters.getMethodArgTypes(name);
			argTypes = new ArrayList<>();
			for (int i = 0; i < argsArray.length; i++) {
				argTypes.add(Helpers.getName(argsArray[i].trim()));
			}
			returnType = Helpers.getName(getters.getMethodReturnType(name));

			sb.append(Helpers.getAccessSymbol(access) + " ");

			sb.append(name + argTypes.toString().replaceAll("\\[", "(").replaceAll("\\]", ")") + " : " + returnType
					+ "\\l");
		}

		return sb.toString();
	}

}
