package problem.pattern;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.ClassDesignParser;
import problem.DotGraphDesign;
import problem.api.IDesignParser;

public class DecoratorPatternTest {
	
	@Test
	public void checkInputStreamReaderDecorator() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.io.InputStreamReader";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Decorator"));
	}
	
	@Test
	public void checkOutputStreamWriterDecorator() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.io.OutputStreamWriter";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Decorator"));
	}
	
	@Test
	public void lab2SolutionDecorator() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[11];
		args[0] = "headfirst.decorator.starbuzz.Beverage";
		args[1] = "headfirst.decorator.starbuzz.CondimentDecorator";
		args[2] = "headfirst.decorator.starbuzz.DarkRoast";
		args[3] = "headfirst.decorator.starbuzz.Decaf";
		args[4] = "headfirst.decorator.starbuzz.Espresso";
		args[5] = "headfirst.decorator.starbuzz.HouseBlend";
		args[6] = "headfirst.decorator.starbuzz.Milk";
		args[7] = "headfirst.decorator.starbuzz.Mocha";
		args[8] = "headfirst.decorator.starbuzz.Soy";
		args[9] = "headfirst.decorator.starbuzz.StarbuzzCoffee";
		args[10] = "headfirst.decorator.starbuzz.Whip";
		dp.parse(args, graphDesigner);
		
		String input = graphDesigner.getGraphStringBuilder().toString();
		int index = input.indexOf("<Decorator");
		int count = 0;
		while (index != -1) {
		    count++;
		    input = input.substring(index + 1);
		    index = input.indexOf("<Decorator");
		}
		
		// Should be 5 Decorator Classes
		assertTrue(count == 5);
		
	}
}
