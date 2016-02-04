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
		System.out.println(graphDesigner.getGraphStringBuilder().toString());
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Adapter"));
	}
	
	@Test
	public void lab5SolutionDecorator() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[3];
		args[0] = "problem.client.App";
		args[1] = "problem.client.IteratorToEnumerationAdapter";
		args[2] = "problem.lib.LinearTransformer";
		dp.parse(args, graphDesigner);
		
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Adapter"));
//		String input = graphDesigner.getGraphStringBuilder().toString();
//		int index = input.indexOf("<Decorator");
//		int count = 0;
//		while (index != -1) {
//		    count++;
//		    input = input.substring(index + 1);
//		    index = input.indexOf("<Decorator");
//		}
//		
//		Should be 5 Decorator Classes
//		assertTrue(count == 5);
//		
	}
}
