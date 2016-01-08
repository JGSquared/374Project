package problem;

import java.util.HashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import problem.api.GraphCode;

public class GraphDeclarationCode extends GraphCode {
	
	public GraphDeclarationCode() {
		super();
	}

	@Override
	public String getCode(HashMap<String, String> items) {
		StringBuilder sb = new StringBuilder();
		String className = getName(items.get("className"), "/");

		sb.append(className + " [\n");
		sb.append("shape=\"record\",\n");
		sb.append("label = \"{");

		int access = Integer.parseInt(items.get("access"));

		if (access == Opcodes.ACC_INTERFACE) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}

		sb.append(className + "|");
		
		return sb.toString();
	}


}
