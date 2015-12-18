package problem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT\n");
	}

	@Override
	public void generateGraph() throws IOException {
		OutputStream out = new FileOutputStream("./input_output/graph.ps"); 
		out.write(sb.toString().getBytes());
		out.close();
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
		String name = items.get("className");
		String superName = items.get("extends");
		String interFacesString = items.get("implements");
		String[] interFaces = interFacesString.substring(1, interFacesString.length() - 1).split(",");
		
		if (superName != "") {
			sb.append(name + " -> " + superName + " [arrowhead='onormal', style='solid'" + "];");
		}
		
		for (String interFace: interFaces) {
			sb.append(name + " -> " + interFace.trim() + " [arrowhead='onormal', style='dashed'" + "];");
		}
		
		sb.append("}");
	}
	
}
