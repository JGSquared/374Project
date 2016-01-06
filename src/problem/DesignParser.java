package problem;

import java.io.IOException;
import java.util.HashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.api.IGraphDesign;


public class DesignParser {
	private HashMap<String, String> parsedCode;
	
	public void parse(String[] args, IGraphDesign graphDesigner) throws IOException{
		graphDesigner.initializeGraph();
		
		for(String className : args) {
			parsedCode = new HashMap<>();
			
			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, parsedCode);
			
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, parsedCode);
			
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, parsedCode);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			
			graphDesigner.addGraphCode(parsedCode);
		}
	
		graphDesigner.closeGraph();
		graphDesigner.generateGraph();
	}

	public static void main(String[] args) throws IOException {
		DesignParser dp = new DesignParser();
		IGraphDesign graphDesigner = new DotGraphDesign();
		graphDesigner.addCodeGetter(new GraphDeclarationCode());
		graphDesigner.addCodeGetter(new GraphFieldCode());
		graphDesigner.addCodeGetter(new GraphMethodCode());
		graphDesigner.addCodeGetter(new GraphClassCloserCode());
		graphDesigner.addCodeGetter(new GraphExtensionAndImplementCode());
		dp.parse(args, graphDesigner);	
	}

}
