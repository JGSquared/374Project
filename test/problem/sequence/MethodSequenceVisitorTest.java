package problem.sequence;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassSequenceVisitor;

public class MethodSequenceVisitorTest {
	
	@Test
	public final void testMethodSequenceVisitorEmptyEdge() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod");		
		
		assertTrue(visitor.getParsedCode().keySet().isEmpty());
		assertTrue(visitor.getParsedCode().values().isEmpty());
	}
	
	@Test
	public final void testMethodSequenceVisitorMethodInsn() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod");
		MethodVisitor methodVisitor = visitor.visitMethod(1, "TestMethod", "(ILjava/util/HashMap;)V", "signature", new String[] {"exception"});
		methodVisitor.visitMethodInsn(1, "java/util/List", "TestMethod", "(Ljava/lang/Object;JJJ)Z", true);
		
		assertTrue(visitor.getParsedCode().containsKey("sequenceNode0"));
		assertTrue(visitor.getParsedCode().containsValue("java/util/List"));
	}
	
	
}