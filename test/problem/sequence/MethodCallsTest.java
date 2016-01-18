package problem.sequence;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import problem.visitor.ClassSequenceVisitor;

public class MethodCallsTest {

	@Test
	public void queryMethodCallsTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();
		
		ClassReader reader = new ClassReader("problem.DotGraphDesign");
		ClassSequenceVisitor sequenceVisitor = new ClassSequenceVisitor(Opcodes.ASM5, items, 0, "addCodeGetter");
		
		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);

		String expected = "[add]";
		System.out.println(sequenceVisitor.getMethodCalls().toString());
		Assert.assertEquals(expected, sequenceVisitor.getMethodCalls().toString());
		
		
	}
}
