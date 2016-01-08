package problem;

import org.junit.Assert;
import org.junit.Test;

import problem.api.AbstractGraphCode;

public class DotGraphDesignTest {
	DotGraphDesign graph;

	@Test
	public final void testaddCodeGetter() {
		graph = new DotGraphDesign();
		AbstractGraphCode getter = new GraphFieldCode();
		
		graph.addCodeGetter(getter);
		
		Assert.assertTrue(graph.getCodeGetters().contains(getter));
	}
	
	@Test
	public final void testRemoveCodeGetter() {
		graph = new DotGraphDesign();
		AbstractGraphCode getter1 = new GraphFieldCode();
		AbstractGraphCode getter2 = new GraphFieldCode();
		graph.addCodeGetter(getter1);
		graph.addCodeGetter(getter2);
		graph.removeCodeGetter(getter2);
		
		Assert.assertTrue(!graph.getCodeGetters().contains(getter2));
		Assert.assertTrue(graph.getCodeGetters().contains(getter1));
	}
	
	@Test
	public final void testGraphInitializer() {
		graph = new DotGraphDesign();
		graph.initializeGraph();
		String expected = "digraph G{\nrankdir=BT;\n";
		Assert.assertTrue(graph.getGraphStringBuilder().toString().contains(expected));
	}
	
	@Test
	public final void testGraphCloser() {
		graph = new DotGraphDesign();
		graph.closeGraph();
		String expected = "}";
		Assert.assertTrue(graph.getGraphStringBuilder().toString().contains(expected));
	}
}