package problem.code;

import java.util.ArrayList;

import problem.FileProperties;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphSequenceNodeCode implements IGraphCode {
	private static final String KEY_NAME = "sequenceNode";

	public GraphSequenceNodeCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		ArrayList<String> nodeNames = getters.getSequenceNodeKeys();
		ArrayList<String> usedNodes = new ArrayList<>();
		
//		for (String s : items.keySet()) {
//			if (s.contains(KEY_NAME)) {
//				nodeNames.add(s);
//			}
//		}
		
		Helpers.sortListByNum(nodeNames, KEY_NAME.length());
		
		for (int i = 0; i < nodeNames.size(); i++) {
			String nodeKey = nodeNames.get(i);
			String nodeValue = getters.getSequenceNodeValue(nodeKey);
			
			String className = Helpers.getName(nodeValue);
			if (usedNodes.contains(className)) {
				continue;
			}
			
			sb.append(Helpers.getCamelCase(className) + ":" + className + "\n");
			usedNodes.add(className);
		}
		sb.append("\n");
		
		return sb.toString();
	}

}
