package problem.code;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import problem.Constants;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IGraphCode;
import problem.api.IPatternDetector;
import problem.patterns.SingletonPatternDetector;

public class GraphDeclarationCode implements IGraphCode {
	
	public GraphDeclarationCode() {
		super();
	}

	@Override
	public String getCode(CodeMapGetters getters) {
		StringBuilder sb = new StringBuilder();
		String className = Helpers.getName(getters.getClassName());

		sb.append(className + " [\n");
		sb.append("shape=\"record\",\n");
		
		sb.append("color=\"" + Constants.COLOR_OFFSET + "\",\n");
		
		sb.append("label = \"{");

		int access = getters.getAccess();

		// DONE: Figure out why interface access is 1537
		if (access == 1537) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		
		sb.append(className);
		
		sb.append(Constants.LABEL_OFFSET);
		
		sb.append("|");
		return sb.toString();
	}


}
