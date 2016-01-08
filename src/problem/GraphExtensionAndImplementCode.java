package problem;

import java.util.HashMap;

import problem.api.GraphCode;

public class GraphExtensionAndImplementCode extends GraphCode {

	public GraphExtensionAndImplementCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = new FileProperties();
		
		String name = getName(items.get("className"), "/");
		String superName = getName(items.get("extends"), "/");
		String interFacesString = items.get("implements");
		String[] interFaces = interFacesString.substring(1,
				interFacesString.length() - 1).split(",");
		
		if (!superName.equals("") && !fp.whiteList.contains(superName)) {
			sb.append(name + " -> " + superName
					+ " [arrowhead=\"onormal\", style=\"solid\"" + "];");
		}
		if (!interFacesString.equals("[]")) {
			for (String interFace : interFaces) {
				String interFaceName = getName(interFace, "/");
				if (!fp.whiteList.contains(interFaceName)) {
					sb.append(name + " -> " + interFaceName
							+ " [arrowhead=\"onormal\", style=\"dashed\"" + "];");
				}
			}
		}
		
		return sb.toString();
	}

}
