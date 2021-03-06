package problem;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassFieldVisitor;

public class ClassFieldVisitorTest {

	@Test
	public final void testClassFieldVisitor() {
		ClassFieldVisitor visitor = new ClassFieldVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visitField(1, "name", "desc", "signature", new Object());
		
		HashMap<String, String> parsedCode = visitor.getParsedCode();
		
		assertTrue(parsedCode.keySet().contains("field0"));
	}

}
