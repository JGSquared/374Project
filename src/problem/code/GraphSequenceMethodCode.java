package problem.code;

import java.util.ArrayList;
import java.util.HashMap;

import problem.FileProperties;
import problem.Helpers;
import problem.api.IGraphCode;

public class GraphSequenceMethodCode implements IGraphCode {
	private static final String KEY_NAME = "sequenceMethod";

	public GraphSequenceMethodCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		ArrayList<String> methodKeys = new ArrayList<>();

		for (String s : items.keySet()) {
			if (s.contains(KEY_NAME)) {
				methodKeys.add(s);
			}
		}

		Helpers.sortListByNum(methodKeys, KEY_NAME.length());

		for (int i = 0; i < methodKeys.size(); i++) {
			String methodKey = methodKeys.get(i);
			String methodValue = items.get(methodKey);

			String[] methodParams = methodValue.split(":");
			String caller = Helpers.getName(methodParams[0], "/");
			String callee = Helpers.getName(methodParams[1], "/");
			String method = methodParams[2];
			String args = methodParams[3]
					.replaceAll("\\[", "")
					.replaceAll("\\]", "");
			ArrayList<String> argTypes = new ArrayList<String>();
			if (!args.equals("")) {
				String[] types = args.split(",");
				for (int j = 0; j < types.length; j++) {
					argTypes.add(Helpers.getName(types[j].trim(), "\\."));
				}
			}
			sb.append(Helpers.getCamelCase(caller) + ":" + Helpers.getCamelCase(callee) + "."
					+ method);
			if (argTypes.size() > 0) {
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
