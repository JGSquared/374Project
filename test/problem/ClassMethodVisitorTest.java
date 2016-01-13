package problem;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassMethodVisitor;

public class ClassMethodVisitorTest {

	@Test
	public final void testClassMethodVisitor() {
		ClassMethodVisitor visitor = new ClassMethodVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visitMethod(1, "name", "(ILjava/util/HashMap;)V", "signature", new String[] {"exception"});
		
		HashMap<String, String> parsedCode = visitor.getParsedCode();
		
		assertTrue(parsedCode.keySet().contains("method0"));
	}
	
}
