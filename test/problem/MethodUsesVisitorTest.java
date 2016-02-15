package problem;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.visitor.MethodUsesVisitor;

public class MethodUsesVisitorTest {
	
	@Test
	public final void testMethodUsesVisitor() {
		MethodUsesVisitor visitor = new MethodUsesVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visitMethodInsn(1, "TestOwner", "TestName", "TestDesc", true);
		
		HashMap<String, String> parsedCode =  visitor.getParsedCode();

		assertTrue(parsedCode.keySet().contains("uses0"));
	}
}
