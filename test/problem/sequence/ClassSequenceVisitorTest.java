package problem.sequence;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassSequenceVisitor;

public class ClassSequenceVisitorTest {

	@Test
	public final void testClassSequenceVisitor() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod", 0);
		
		//Make sure visitMethod is properly returning a MethodSequencVisitor
		assertTrue(visitor.visitMethod(1, "TestMethod", "(ILjava/util/HashMap;)V", "signature",
				new String[] {"exception"}).toString().contains("MethodSequenceVisitor"));
	}
	
}