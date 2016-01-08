package problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import headfirst.factory.pizzaaf.NYPizzaIngredientFactory;
import headfirst.factory.pizzaaf.PizzaIngredientFactory;
import problem.api.AbstractGraphCode;

public class TrickFactoryPatternTest {
	
	@Test
	public final void testAssociation() throws IOException {
		AbstractGraphCode codeGetter = new GraphUsesCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder parsedCode = new StringBuilder();
		StringBuilder expected = new StringBuilder();

		ClassReader reader = new ClassReader("headfirst.factory.pizzaaf.NYPizzaIngredientFactory");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphUsesCode guc = new GraphUsesCode();
		String generatedCode = guc.getCode(items);
		
		assertTrue(generatedCode.contains("FreshClams"));
		assertTrue(!generatedCode.contains("Veggies"));
		assertTrue(generatedCode.contains("Mushroom"));
		
	}
}
