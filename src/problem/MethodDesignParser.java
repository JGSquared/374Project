package problem;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.visitor.ClassSequenceVisitor;

public class MethodDesignParser implements IDesignParser {
	private HashMap<String, String> parsedCode;
	private int callDepth;
	private static final int DEFAULT_DEPTH = 5;
	private int counter = 0;
	@Override
	public void parse(String[] args, IGraphDesign graphDesigner)
			throws IOException {
		// TODO Auto-generated method stub
		if (args.length > 2) {
			throw new IOException("Too many arguments\n");
		}
		graphDesigner.initializeGraph();

		parsedCode = new HashMap<>();
		
		String[] methodSig = args[0].split(",");
		String className = methodSig[0];
		String methodName = methodSig[1];
		if (methodSig.length > 2) {
			String[] paramTypes = Arrays.copyOfRange(methodSig, 2,
					methodSig.length);
		}
		
		callDepth = DEFAULT_DEPTH;
		if (args[1] != null) {
			callDepth = Integer.parseInt(args[1]);
		}
		
		className = className.replaceAll("\\.", "/");
		this.parsedCode.put("sequenceNode" + counter++, className + ":nonhidden");
		
		ClassReader reader = new ClassReader(className);
		
		ClassVisitor classVisitor = new ClassSequenceVisitor(Opcodes.ASM5, parsedCode, callDepth, methodName, counter);
		
		reader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
		
		graphDesigner.addGraphCode(parsedCode);

	}
	
	public HashMap<String, String> getParsedCode() {
		return this.parsedCode;
	}

}
