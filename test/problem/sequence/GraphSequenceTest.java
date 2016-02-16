package problem.sequence;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import problem.api.CodeMapGetters;
import problem.api.IGraphCode;
import problem.code.GraphSequenceMethodCode;
import problem.code.GraphSequenceNodeCode;
import problem.code.GraphUsesCode;
import problem.visitor.ClassSequenceVisitor;

public class GraphSequenceTest {
	
	@Test
	public final void graphSequenceNodeCodeTest() throws IOException {
		IGraphCode codeGetter = new GraphUsesCode();
		HashMap<String, String> items = new HashMap<String, String>();
		CodeMapGetters getters = new CodeMapGetters(items);
		StringBuilder parsedCode = new StringBuilder();
		StringBuilder expected = new StringBuilder();
		
		ArrayList<String> methodCallList = new ArrayList<String>();
		methodCallList.add("java.util.List");

		ClassReader reader = new ClassReader("java.util.Collections");
		ClassSequenceVisitor sequenceVisitor = new ClassSequenceVisitor(Opcodes.ASM5, items, 1, "shuffle", methodCallList);
		
		
		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);
		items = sequenceVisitor.getParsedCode();
		
		IGraphCode sequenceNode = new GraphSequenceNodeCode();
		String generatedCode = sequenceNode.getCode(getters);
		
		assertTrue(generatedCode.contains("collections:Collections"));
		
	}
	
	@Test
	public final void graphSequenceNodeCodeZeroDepthEdgeTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();
		CodeMapGetters getters = new CodeMapGetters(items);
		
		ArrayList<String> methodCallList = new ArrayList<String>();
		methodCallList.add("java.util.List");

		ClassReader reader = new ClassReader("java.util.Collections");
		ClassSequenceVisitor sequenceVisitor = new ClassSequenceVisitor(Opcodes.ASM5, items, 0, "shuffle", methodCallList);
		
		
		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);
		items = sequenceVisitor.getParsedCode();
		
		IGraphCode sequenceNode = new GraphSequenceMethodCode();
		String generatedCode = sequenceNode.getCode(getters);
		assertTrue(!generatedCode.contains("random:system.nanoTime"));
		
		HashMap<String, String> items1 = new HashMap<String, String>();

		ClassReader reader1 = new ClassReader("java.util.Collections");
		ClassSequenceVisitor sequenceVisitor1 = new ClassSequenceVisitor(Opcodes.ASM5, items1, 1, "shuffle", methodCallList);
		
		
		reader1.accept(sequenceVisitor1, ClassReader.EXPAND_FRAMES);
		items1 = sequenceVisitor1.getParsedCode();
		
		IGraphCode sequenceNode1 = new GraphSequenceMethodCode();
		String generatedCode1 = sequenceNode1.getCode(getters);
		assertTrue(generatedCode1.contains("random:system.nanoTime"));
		
	}
}