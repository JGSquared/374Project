package problem.code;

import java.util.HashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

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
		IPatternDetector detector = new SingletonPatternDetector(items);
		boolean isSingleton = detector.isPattern();
		StringBuilder sb = new StringBuilder();
		String className = Helpers.getName(items.get("className"), "/");

		sb.append(className + " [\n");
		sb.append("shape=\"record\",\n");
		if (isSingleton) {
			sb.append("color=\"blue\",\n");
		}
		sb.append("label = \"{");

		int access = Integer.parseInt(items.get("access"));

		if (access == Opcodes.ACC_INTERFACE) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		
		sb.append(className);
		if (detector.isPattern()) {
			sb.append("\\n\\<\\<Singleton\\>\\>");
		}
		sb.append("|");
		return sb.toString();
	}


}
