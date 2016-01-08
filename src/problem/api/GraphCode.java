package problem.api;

import java.util.HashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public abstract class GraphCode {
	
	public GraphCode() {
		
	}
	
	public abstract String getCode(HashMap<String, String> items);
	
	protected String getName(String path, String separator) {
		// takes a path to a class name (e.g. problem/DotGraphDesign) and
		// returns the name with no path (e.g. DotGraphDesign)
		String[] paths = path.split(separator);

		return paths[paths.length - 1];
	}
	
	protected String getAccessSymbol(int access) {
		// returns the proper symbol (e.g. '+', '-', etc.) given an access int
		switch (access) {
		case Opcodes.ACC_PUBLIC:
			return "+";
		case Opcodes.ACC_PRIVATE:
			return "-";
		case Opcodes.ACC_PROTECTED:
			return "#";
		default:
			return "";
		}
	}
}
