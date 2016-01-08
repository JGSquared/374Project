package problem;

import java.util.HashMap;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class MethodAssociationVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;
	private int associatedCount = 0;

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
		
		this.parsedCode.put("associated" + this.associatedCount++, type);
	}
	
	@Override
	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
		String type = "";
		if (signature != null) { 
			type = Type.getType(signature).getClassName();
			this.parsedCode.put("associated" + this.associatedCount++, type);
		}
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}