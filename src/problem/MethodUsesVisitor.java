package problem;

import java.util.HashMap;

import org.objectweb.asm.MethodVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class MethodUsesVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;
	private int usesCounter = 0;

	public MethodUsesVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public MethodUsesVisitor(int arg0, MethodVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		
		this.parsedCode.put("uses" + this.usesCounter, owner);
	}

}
