package problem.sequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import problem.api.AbstractGraphCode;
import problem.code.GraphSequenceMethodCode;
import problem.visitor.ClassSequenceVisitor;

public class StaticEdgeCaseTest {
	
	@Test
	public void staticEdgeCaseTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();
		
		ClassReader reader = new ClassReader("problem.sequence.StaticEdgeCaseTest");
		ClassSequenceVisitor sequenceVisitor = new ClassSequenceVisitor(Opcodes.ASM5, items, 0, "testMethod", new ArrayList<String>());

		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);
		items = sequenceVisitor.getParsedCode();
		
		AbstractGraphCode sequenceNode = new GraphSequenceMethodCode();
		String generatedCode = sequenceNode.getCode(items);
		String expected = "staticEdgeCaseTest:printStream.println(String)\n";
		Assert.assertEquals(expected, generatedCode.toString());
	}
	
	public static void testMethod() {
		System.out.println("STATIC:");
	}
	
	
}
