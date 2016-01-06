package problem;

import java.util.HashMap;

import problem.api.IGraphCode;

public class GraphExtensionAndImplementCode extends IGraphCode {

	public GraphExtensionAndImplementCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		
		String name = getName(items.get("className"), "/");
		String superName = getName(items.get("extends"), "/");
		String interFacesString = items.get("implements");
		String[] interFaces = interFacesString.substring(1,
				interFacesString.length() - 1).split(",");

		if (superName != "") {
			sb.append(name + " -> " + superName
					+ " [arrowhead=\"onormal\", style=\"solid\"" + "];");
		}
		if (!interFacesString.equals("[]")) {
			for (String interFace : interFaces) {
				String interFaceName = getName(interFace, "/");
				sb.append(name + " -> " + interFaceName
						+ " [arrowhead=\"onormal\", style=\"dashed\"" + "];");
			}
		}
		
		return sb.toString();
	}

}
