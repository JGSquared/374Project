package problem.code;

import java.util.HashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import problem.Constants;
import problem.Helpers;
import problem.api.IGraphCode;
import problem.api.IPatternDetector;
import problem.patterns.SingletonPatternDetector;

public class GraphDeclarationCode implements IGraphCode {
	
	public GraphDeclarationCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		String className = Helpers.getName(items.get("className"), "/");

		sb.append(className + " [\n");
		sb.append("shape=\"record\",\n");
		
		sb.append("color=\"" + Constants.COLOR_OFFSET + "\",\n");
		
		sb.append("label = \"{");

		int access = Integer.parseInt(items.get("access"));

		if (access == (Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC)) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		
		sb.append(className);
		
		sb.append(Constants.LABEL_OFFSET);
		
		sb.append("|");
		return sb.toString();
	}


}
