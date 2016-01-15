package problem.visitor;

import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassSequenceVisitor extends ClassVisitor {
	private HashMap<String, String> parsedCode;
	private int callDepth;
	private String methodName;
	private int counter; 
	private String className;
	
	public ClassSequenceVisitor(int arg0, HashMap<String, String> parsedCode,
			int callDepth, String methodName, int counter) {
		super(arg0);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.methodName = methodName;
		this.counter = counter;
	}

	public ClassSequenceVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode,
			int callDepth, String methodName, int counter) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.methodName = methodName;
		this.counter = counter;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		if (name.equals(methodName)) {
			MethodSequenceVisitor sequenceVisitor = new MethodSequenceVisitor(Opcodes.ASM5, toDecorate, parsedCode, callDepth, counter, className);
			return sequenceVisitor;
		}
		return toDecorate;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		this.className = name;
	}

	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
