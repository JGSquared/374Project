package problem.visitor;

import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor{
	private HashMap<String, String> parsedCode;
	private int fieldCounter = 0;

	public ClassFieldVisitor(int arg0, HashMap<String, String> parsedCode) {
		super(arg0);
		this.parsedCode = parsedCode;
	}

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, HashMap<String, String> parsedCode) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

		String type = Type.getType(desc).getClassName();
		String parsedSignature = "EMPTY";
		if (signature != null) {
			parsedSignature = Type.getType(signature).getClassName();
		}
		this.parsedCode.put("field" + this.fieldCounter, access + ":" + name + ":" + type + ":" + parsedSignature);
		
		this.fieldCounter++;
		
		return toDecorate;
	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
