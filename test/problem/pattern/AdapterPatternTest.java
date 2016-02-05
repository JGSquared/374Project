package problem.pattern;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import problem.ClassDesignParser;
import problem.DotGraphDesign;
import problem.api.IDesignParser;

public class AdapterPatternTest {
	
	@Test
	public void checkMouseAdapter() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.awt.event.MouseAdapter";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Adapter"));
	}
	
	@Test
	public void checkAdapterExample() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[5];
		args[0] = "problem.client.App";
		args[1] = "problem.client.IteratorToEnumerationAdapter";
		args[2] = "problem.lib.LinearTransformer";
		args[3] = "java.util.Iterator";
		args[4] = "java.util.Enumeration";
		
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Adapter"));
	}
}
