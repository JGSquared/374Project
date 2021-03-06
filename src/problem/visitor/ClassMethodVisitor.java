package problem.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor{
	private HashMap<String, String> parsedCode;
	private int methodCounter = 0;

	public ClassMethodVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		String returnType = Type.getReturnType(desc).getClassName();
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		
		List<String> stypes = new ArrayList<String>();
		
		for (Type t: argTypes) {
			stypes.add(t.getClassName());
		}
		
		this.parsedCode.put("method" + this.methodCounter, access + ":" + name + ":"
				+ stypes.toString() + ":" + returnType);
		this.methodCounter++;		
		
		return toDecorate;
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}

}
