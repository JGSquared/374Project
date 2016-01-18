package problem.visitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import problem.MethodDesignParser;
import problem.SequenceGraphDesign;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class MethodSequenceVisitor extends MethodVisitor {
	private HashMap<String, String> parsedCode;
	private int callDepth;
	private String className;

	public MethodSequenceVisitor(int arg0, HashMap<String, String> parsedCode,
			int callDepth, String className) {
		super(arg0);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.className = className;
	}

	public MethodSequenceVisitor(int arg0, MethodVisitor arg1, HashMap<String, String> parsedCode,
			int callDepth, String className) {
		super(arg0, arg1);
		this.parsedCode = parsedCode;
		this.callDepth = callDepth;
		this.className = className;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		
		this.parsedCode.put("sequenceNode" + MethodDesignParser.count++, owner);

		Type[] argTypes = Type.getArgumentTypes(desc);
		ArrayList<String> stypes = new ArrayList<String>();
		
		for (Type t: argTypes) {
			stypes.add(t.getClassName());
		}
		
		this.parsedCode.put("sequenceMethod" + MethodDesignParser.count++, className + ":" + owner + ":" + name + ":" + stypes.toString());
		
		if (callDepth != 0) {
			ClassReader reader;
			try {
				reader = new ClassReader(owner);
				ClassVisitor classVisitor = new ClassSequenceVisitor(Opcodes.ASM5, parsedCode, callDepth - 1, name, stypes);
				
				reader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}
}
