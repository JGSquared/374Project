package problem;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void addGraphCode(HashMap<String, String> items) {
		addDeclarationCode(items);
		addFieldCode(items);
		addMethodCode(items);
		addExtensionAndImplementsCode(items);
		System.out.println(sb.toString());
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT\n");
	}

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
		
	}
	
	public void addDeclarationCode(HashMap<String, String> items) {
		sb.append(items.get("className") + " [\n");
		sb.append("shape='record',\n");
		sb.append("label = '{");
		
		int access = Integer.parseInt(items.get("access"));
		
		if (access == Opcodes.ACC_INTERFACE) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		
		sb.append(items.get("className") + "|");
	}
	
	public void addFieldCode(HashMap<String, String> items) {
		for(String s: items.keySet()) {
			if (s.contains("field")) {
				String field = items.get(s);
				String[] fieldProperties = field.split(":");
				int access = Integer.parseInt(fieldProperties[0]);
				String name = fieldProperties[1];
				String type = fieldProperties[2];
				
				//TODO: put switch in separate method
				switch(access) {
					case Opcodes.ACC_PUBLIC: 
						sb.append("+ ");
						break;
					case Opcodes.ACC_PRIVATE:
						sb.append("- ");
						break;
					case Opcodes.ACC_PROTECTED:
						sb.append("# ");
						break;
					default:
						sb.append(" ");
						break;
				}
				
				sb.append(name + " : " + type + "\\l");
			}
		}
		sb.append("|");
	}
	
	private void addMethodCode(HashMap<String, String> items) {
		for(String s : items.keySet()) {
			if (s.contains("method")) {
				String method = items.get(s);
				String[] methodProps = method.split(":");
				
				int access = Integer.parseInt(methodProps[0]);
				String name = methodProps[1];
				String argTypesString = methodProps[2];
				argTypesString = argTypesString.replaceAll("\\[","\\(");
				argTypesString = argTypesString.replaceAll("\\]", "\\)");
				String returnType = methodProps[3];
				
				//TODO: put switch in separate method
				switch(access) {
				case Opcodes.ACC_PUBLIC: 
					sb.append("+ ");
					break;
				case Opcodes.ACC_PRIVATE:
					sb.append("- ");
					break;
				case Opcodes.ACC_PROTECTED:
					sb.append("# ");
					break;
				default:
					sb.append(" ");
					break;
				}
				
				sb.append(name + argTypesString + " : " + returnType + "\\l");
			}
		}
		
		sb.append("}'\n];");
	}
	
	private void addExtensionAndImplementsCode(HashMap<String, String> items) {
		
	}
	
}
