package problem.visitor;

import java.util.HashMap;

import org.objectweb.asm.MethodVisitor;

public class MethodSequenceVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;

	public MethodSequenceVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public MethodSequenceVisitor(int arg0, MethodVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}

}
