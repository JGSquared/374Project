package problem.code;

import problem.FileProperties;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;

public class GraphExtensionAndImplementCode implements IGraphCode {

	public GraphExtensionAndImplementCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		FileProperties fp = FileProperties.getInstance();
		
		String name = Helpers.getName(getters.getClassName());
		String superName = Helpers.getName(getters.getClassExtends());
		String[] interFaces = getters.getClassImplements();
		if (!superName.equals("") && !fp.whiteList.contains(superName) && Helpers.isClassNameValid(superName)) {
			sb.append(name + " -> " + superName
					+ " [arrowhead=\"onormal\", style=\"solid\"" + "];");
		}
		if (interFaces.length > 0) {
			for (String interFace : interFaces) {
				String interFaceName = Helpers.getName(interFace);
				if (!Helpers.isClassNameValid(interFaceName)) {
					continue;
				}
				if (!fp.whiteList.contains(interFaceName)) {
					sb.append(name + " -> " + interFaceName
							+ " [arrowhead=\"onormal\", style=\"dashed\"" + "];");
				}
			}
		}
		
		return sb.toString();
	}

}
