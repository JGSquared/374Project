package problem;

import java.io.IOException;
import java.util.HashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.visitor.ClassAssociationVisitor;
import problem.visitor.ClassDeclarationVisitor;
import problem.visitor.ClassFieldVisitor;
import problem.visitor.ClassMethodVisitor;
import problem.visitor.ClassUsesVisitor;


public class ClassDesignParser implements IDesignParser {
	private HashMap<String, String> parsedCode;
	
	@Override
	public void parse(String[] args, IGraphDesign graphDesigner) throws IOException{
//		graphDesigner.initializeGraph();
		
		for(String className : args) {
			parsedCode = new HashMap<>();
			
			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, parsedCode);
			
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, parsedCode);
			
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, parsedCode);
			
			ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, parsedCode);
			
			ClassVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, parsedCode);
			
			reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
			
			graphDesigner.addGraphCode(parsedCode);
		}
	
//		graphDesigner.closeGraph();
//		graphDesigner.generateGraph();
	}

}
