package problem;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class ClassFieldVisitorTest {

	@Test
	public final void testClassFieldVisitor() {
		ClassFieldVisitor visitor = new ClassFieldVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visitField(1, "name", "desc", "signature", new Object());
		
		HashMap<String, String> parsedCode = visitor.getParsedCode();
		
		assertTrue(parsedCode.keySet().contains("field0"));
	}

}
