package problem.code;

import java.util.ArrayList;

import problem.FileProperties;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphSequenceMethodCode implements IGraphCode {
	private static final String KEY_NAME = "sequenceMethod";

	public GraphSequenceMethodCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		ArrayList<String> methodKeys = getters.getSequenceMethodKeys();

		Helpers.sortListByNum(methodKeys, KEY_NAME.length());

		for (int i = 0; i < methodKeys.size(); i++) {
			String methodKey = methodKeys.get(i);
			String caller = Helpers.getName(getters.getSequenceMethodCallee(methodKey));
			String callee = Helpers.getName(getters.getSequenceMethodCallee(methodKey));
			String method = getters.getSequenceMethodName(methodKey);
			ArrayList<String> argTypes = new ArrayList<String>();
			String[] types = getters.getSequenceMethodArgs(methodKey);

			for (int j = 0; j < types.length; j++) {
				argTypes.add(Helpers.getName(types[j].trim()));
			}
			
			sb.append(Helpers.getCamelCase(caller) + ":" + Helpers.getCamelCase(callee) + "." + method);
			if (argTypes.size() > 0) {
				sb.append(argTypes.toString().replaceAll("\\[", "(").replaceAll("\\]", ")"));
			} else {
				sb.append("()");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

}
