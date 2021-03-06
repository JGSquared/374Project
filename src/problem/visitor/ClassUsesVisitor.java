package problem.visitor;

import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import problem.visitor.MethodUsesVisitor;

public class ClassUsesVisitor extends ClassVisitor {
	private HashMap<String, String> parsedCode;

	public ClassUsesVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public ClassUsesVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodUsesVisitor returnVisitor = new MethodUsesVisitor(Opcodes.ASM5, toDecorate, this.parsedCode);
		
		return returnVisitor;
	}

}
