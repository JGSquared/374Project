package problem;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;




public class DotGraphDesignTest {
	DotGraphDesign graph;

	@Test
	public final void testaddDeclarationCode() {
		graph = new DotGraphDesign();
		HashMap<String, String> items = new HashMap<String, String>();
		items.put("className", "ClassTest");
		items.put("access", "1");
		graph.addDeclarationCode(items);
		String expected = "ClassTest [\nshape=\"record\","
				+ "\nlabel = \"{ClassTest|";
		String actual = graph.getGraphStringBuilder().toString();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public final void testaddFieldCode() {
		graph = new DotGraphDesign();
		HashMap<String, String> items = new HashMap<String, String>();
		items.put("className", "ClassTest");
		items.put("field" + 0, 1 + ":" + "fieldTest" + ":" + "typeTest");
		graph.addFieldCode(items);
		String expected = "+ fieldTest : typeTest\\l|";
		String actual = graph.getGraphStringBuilder().toString();
		Assert.assertEquals(expected, actual);
		
	}
	
}