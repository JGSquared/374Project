package problem.sequence;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassSequenceVisitor;

public class MethodSequenceVisitorTest {

	@Test
	public final void testMethodSequenceVisitorTypeInsn() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod", 0);
		MethodVisitor methodVisitor = visitor.visitMethod(1, "TestMethod", "(ILjava/util/HashMap;)V", "signature", new String[] {"exception"});
		methodVisitor.visitTypeInsn(1, "TestType");
		
		assertTrue(visitor.getParsedCode().containsKey("sequenceNode0"));
		assertTrue(visitor.getParsedCode().containsValue("TestType:hidden"));
	}
	
	@Test
	public final void testMethodSequenceVisitorEmptyEdge() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod", 0);		
		
		assertTrue(visitor.getParsedCode().keySet().isEmpty());
		assertTrue(visitor.getParsedCode().values().isEmpty());
	}
	
	@Test(expected=NullPointerException.class)
	public final void testMethodSequenceVisitorBadValuesEdge() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod", 0);
		
		//This method visitor should not be a MethodSequence Visitor
		MethodVisitor methodVisitor = visitor.visitMethod(1, "EdgeCase", "(ILjava/util/HashMap;)V", "signature", new String[] {"exception"});
		
		methodVisitor.visitTypeInsn(1, "TestType");
		
		assertTrue(visitor.getParsedCode().keySet().isEmpty());
		assertTrue(visitor.getParsedCode().values().isEmpty());
	}
	
	@Test
	public final void testMethodSequenceVisitorMethodInsn() {
		ClassSequenceVisitor visitor = new ClassSequenceVisitor(Opcodes.ASM5, new HashMap<String, String>(), 2, "TestMethod", 0);
		MethodVisitor methodVisitor = visitor.visitMethod(1, "TestMethod", "(ILjava/util/HashMap;)V", "signature", new String[] {"exception"});
		methodVisitor.visitMethodInsn(1, "java/util/List", "TestMethod", "(Ljava/lang/Object;JJJ)Z", true);
		
		assertTrue(visitor.getParsedCode().containsKey("sequenceNode0"));
		assertTrue(visitor.getParsedCode().containsValue("java/util/List:nonHidden"));
	}
	
	
}