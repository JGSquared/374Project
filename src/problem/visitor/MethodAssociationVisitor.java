package problem.visitor;

import java.util.HashMap;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class MethodAssociationVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;

	public MethodAssociationVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public MethodAssociationVisitor(int arg0, MethodVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
		
		String type = Type.getType(desc).getClassName();
		
		this.parsedCode.put("associated" + type, type);
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		if (name.equals("<init>")) {
			this.parsedCode.put("associated" + owner, owner.replaceAll("/", "\\."));
		}
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
