package problem;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.api.IGraphCode;
import problem.code.GraphUsesCode;
import problem.visitor.ClassAssociationVisitor;
import problem.visitor.ClassDeclarationVisitor;
import problem.visitor.ClassFieldVisitor;
import problem.visitor.ClassMethodVisitor;
import problem.visitor.ClassUsesVisitor;

public class ConcreteTest {
	
	@Test
	public final void testAssociation() throws IOException {
		IGraphCode codeGetter = new GraphUsesCode();
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("problem.DotGraphDesign");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		for (String s : items.values()) {
			assertTrue(!s.equals("java.util.List"));
		}
		
		GraphUsesCode guc = new GraphUsesCode();
		String generatedCode = guc.getCode(items);
		
		assertTrue(generatedCode.contains("DotGraphDesign -> AbstractGraphCode"
				+ " [arrowhead=\"open\", style=\"solid\"" + "];"));
	}
}
