package problem;

import java.io.File;
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
		initializeGraph();
		addDeclarationCode(items);
		addFieldCode(items);
		addMethodCode(items);
		addExtensionAndImplementsCode(items);
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT;\n");
	}

	@Override
	public void generateGraph() throws IOException {
		OutputStream out = new FileOutputStream("./input_output/graph.gv"); 
		out.write(sb.toString().getBytes());
		out.close();
		
//		String command = "C:\\ProgramFiles (x86)\\Graphviz2.38\\bin\\dot.exe";
		
	}
	
	public void addDeclarationCode(HashMap<String, String> items) {
		String className = getName(items.get("className"), "/");
		
		sb.append(className + " [\n");
		sb.append("shape=\"record\",\n");
		sb.append("label = \"{");
		
		int access = Integer.parseInt(items.get("access"));
		
		if (access == Opcodes.ACC_INTERFACE) {
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		
		sb.append(className + "|");
	}
	
	public void addFieldCode(HashMap<String, String> items) {
		for(String s: items.keySet()) {
			if (s.contains("field")) {
				String field = items.get(s);
				String[] fieldProperties = field.split(":");
				int access = Integer.parseInt(fieldProperties[0]);
				String name = fieldProperties[1];
				if (name.equals("<init>")) {
					//Bad field, causes errors in GraphViz
					continue;
				}
				String type = getName(fieldProperties[2], "\\.");
				
				sb.append(getAccessSymbol(access) + " ");
				
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
				if (name.equals("<init>")) {
					//Bad method, causes errors in GraphViz
					continue;
				}
				String argTypesString = methodProps[2];				
				argTypesString = argTypesString.replaceAll("\\[","");
				argTypesString = argTypesString.replaceAll("\\]", "");
				
				String[] splitArgs = argTypesString.split(",");
				ArrayList<String> argTypes = new ArrayList<String>();
				for (int i = 0; i < splitArgs.length; i++) {
					argTypes.add(getName(splitArgs[i].trim(), "\\."));
				}
				
				String returnType = getName(methodProps[3], "\\.");
				
				sb.append(getAccessSymbol(access) + " ");
				
				sb.append(name + argTypes.toString().replaceAll("\\[","(").replaceAll("\\]", ")") + " : " + returnType + "\\l");
			}
		}
		
		sb.append("}\"\n];");
	}
	
	private void addExtensionAndImplementsCode(HashMap<String, String> items) {
		String name = getName(items.get("className"), "/");
		String superName = getName(items.get("extends"), "/");
		String interFacesString = items.get("implements");
		String[] interFaces = interFacesString.substring(1, interFacesString.length() - 1).split(",");
		
		if (superName != "") {
			sb.append(name + " -> " + superName + " [arrowhead=\"onormal\", style=\"solid\"" + "];");
		}
		if (!interFacesString.equals("[]")) {
			for (String interFace: interFaces) {
				sb.append(name + " -> " + interFace.replaceAll("/", ".") + " [arrowhead=\"onormal\", style=\"dashed\"" + "];");
			}
		}
		sb.append("}");
	}
	
	private String getAccessSymbol(int access) {
		//returns the proper symbol (e.g. '+', '-', etc.) given an access int
		switch(access) {
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
	
	private String getName(String path, String separator) {
		// takes a path to a class name (e.g. problem/DotGraphDesign) and returns the name with no path (e.g. DotGraphDesign)
		String[] paths = path.split(separator);
		
		return paths[paths.length - 1];
	}
	
}
