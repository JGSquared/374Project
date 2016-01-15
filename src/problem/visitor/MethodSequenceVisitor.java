package problem.visitor;

import java.util.HashMap;

import javax.sound.midi.Sequence;

import org.objectweb.asm.MethodVisitor;

public class MethodSequenceVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;
	private int callDepth;
	private int counter;

	public MethodSequenceVisitor(int arg0, HashMap<String, String> parsedCode, int callDepth, int counter) {
		super(arg0);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.counter = counter;
	}

	public MethodSequenceVisitor(int arg0, MethodVisitor arg1, HashMap<String, String> parsedCode, int callDepth, int counter) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.counter = counter;
	}
	
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		this.parsedCode.put("sequenceNode" + counter++, type+ ":hidden" );
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		this.parsedCode.put("sequenceNode" + counter++, owner + ":nonHidden");
	}

	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
