package problem;

import java.io.IOException;
import java.util.HashMap;

import problem.api.AbstractGraphCode;
import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.code.GraphSequenceNodeCode;

public class App {
	
	public static void main(String[] args) throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		IGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefault();
		dp.parse(args, graphDesigner);
	}
	
}
