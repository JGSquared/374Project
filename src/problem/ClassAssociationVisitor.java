package problem;

import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAssociationVisitor extends ClassVisitor {
	private HashMap<String, String> parsedCode;

	public ClassAssociationVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public ClassAssociationVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodAssociationVisitor associationVisitor = new MethodAssociationVisitor(Opcodes.ASM5, toDecorate, this.parsedCode);
				
		return associationVisitor;
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
