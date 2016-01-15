package problem.code;

import java.util.ArrayList;
import java.util.HashMap;

import problem.FileProperties;
import problem.api.AbstractGraphCode;

public class GraphSequenceMethodCode extends AbstractGraphCode {
	private static final String KEY_NAME = "sequenceMethod";

	public GraphSequenceMethodCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = new FileProperties();
		ArrayList<String> methodKeys = new ArrayList<>();

		for (String s : items.keySet()) {
			if (s.contains(KEY_NAME)) {
				methodKeys.add(s);
			}
		}

		sortListByNum(methodKeys, KEY_NAME.length());

		for (int i = 0; i < methodKeys.size(); i++) {
			String methodKey = methodKeys.get(i);
			String methodValue = items.get(methodKey);

			String[] methodParams = methodValue.split(":");
			String caller = getName(methodParams[0], "/");
			String callee = getName(methodParams[1], "/");
			String method = methodParams[2];
			String args;
			String[] splitTypes = null;
			ArrayList<String> argTypes = new ArrayList<String>();
			if (methodParams.length > 3) {
				args = methodParams[3];
				args = args.replaceAll("\\[", "");
				args = args.replaceAll("\\]", "");
				splitTypes = args.split(",");
				for (int j = 0; i < splitTypes.length; i++) {
					argTypes.add(getName(splitTypes[i].trim(), "\\."));
				}
			}
			sb.append(getCamelCase(caller) + ":" + getCamelCase(callee) + "."
					+ method);
			if (splitTypes != null) {
				sb.append(argTypes.toString().replaceAll("\\[", "(")
						.replaceAll("\\]", ")"));
			} else {
				sb.append("()");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

}
