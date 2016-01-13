package problem;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.visitor.MethodAssociationVisitor;

public class MethodAssociationVisitorTest {
	
	@Test
	public final void testMethodAssociationVisitorField() {
		MethodAssociationVisitor visitor = new MethodAssociationVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visitFieldInsn(1, "TestOwner", "TestName", "JMX.DEFAULT_VALUE_FIELD.");
		
		HashMap<String, String> parsedCode =  visitor.getParsedCode();
		
		assertTrue(parsedCode.keySet().contains("associated0"));
	}
}
