package problem;

import java.util.Arrays;
import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;


public class ClassDeclarationVisitor extends ClassVisitor{
	private HashMap<String, String> parsedCode;

	public ClassDeclarationVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.parsedCode.put("className", name);
		this.parsedCode.put("access", access + "");
		this.parsedCode.put("extends", superName);
		this.parsedCode.put("implements", Arrays.toString(interfaces));
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
