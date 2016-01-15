package problem.code;

import java.util.ArrayList;
import java.util.HashMap;

import problem.FileProperties;
import problem.api.AbstractGraphCode;

public class GraphSequenceNodeCode extends AbstractGraphCode {
	private static final String KEY_NAME = "sequenceNode";

	public GraphSequenceNodeCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = new FileProperties();
		ArrayList<String> nodeNames = new ArrayList<>();
		ArrayList<String> usedNodes = new ArrayList<>();
		
		for (String s : items.keySet()) {
			if (s.contains(KEY_NAME)) {
				nodeNames.add(s);
			}
		}
		
		sortListByNum(nodeNames, KEY_NAME.length());
		
		for (int i = 0; i < nodeNames.size(); i++) {
			String nodeKey = nodeNames.get(i);
			String nodeValue = items.get(nodeKey);
			
			String[] nodeParams = nodeValue.split(":");
			String className = getName(nodeParams[0], "/");
			if (usedNodes.contains(className)) {
				continue;
			}
			String isHidden = nodeParams[1];
			
			if (isHidden.equals("hidden")) {
				sb.append("/");
			}
			sb.append(getCamelCase(className) + ":" + className + "\n");
			usedNodes.add(className);
		}
		sb.append("\n");
		
		return sb.toString();
	}

}
