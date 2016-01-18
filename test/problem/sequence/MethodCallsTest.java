package problem.sequence;

import java.io.IOException;
import java.util.ArrayList;
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
		ArrayList<String> methodCallList = new ArrayList<String>();
		methodCallList.add("problem.api.AbstractGraphCode");
		ClassReader reader = new ClassReader("problem.DotGraphDesign");
		ClassSequenceVisitor sequenceVisitor = new ClassSequenceVisitor(Opcodes.ASM5, items, 0, "addCodeGetter", methodCallList);
		
		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);

		String expected = "[add]";
		Assert.assertEquals(expected, sequenceVisitor.getMethodCalls().toString());
		
		
	}
}
