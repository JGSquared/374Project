package problem.sequence;

import org.junit.Assert;
import org.junit.Test;

import problem.DotGraphDesign;
import problem.SequenceGraphDesign;
import problem.api.AbstractGraphCode;
import problem.code.GraphFieldCode;
import problem.code.GraphSequenceNodeCode;

public class SequenceGraphDesignTest {
	SequenceGraphDesign graph;

	@Test
	public final void testaddCodeSequenceNode() {
		graph = new SequenceGraphDesign();
		AbstractGraphCode sequenceNodeCode = new GraphSequenceNodeCode();
		
		graph.addCodeGetter(sequenceNodeCode);
		
		Assert.assertTrue(graph.getCodeGetters().contains(sequenceNodeCode));
	}
	
	@Test
	public final void testRemoveCodeSequenceNode() {
		graph = new SequenceGraphDesign();
		AbstractGraphCode sequenceNodeCode1 = new GraphSequenceNodeCode();
		AbstractGraphCode sequenceNodeCode2 = new GraphSequenceNodeCode();
		graph.addCodeGetter(sequenceNodeCode1);
		graph.addCodeGetter(sequenceNodeCode2);
		graph.removeCodeGetter(sequenceNodeCode2);
		
		Assert.assertTrue(!graph.getCodeGetters().contains(sequenceNodeCode2));
		Assert.assertTrue(graph.getCodeGetters().contains(sequenceNodeCode1));
		
		graph.removeCodeGetter(sequenceNodeCode1);
		Assert.assertTrue(!graph.getCodeGetters().contains(sequenceNodeCode2));
	}
}