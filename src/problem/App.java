package problem;

import java.io.IOException;
import java.util.HashMap;

import problem.api.AbstractGraphCode;
import problem.api.IGraphDesign;
import problem.code.GraphSequenceNodeCode;

public class App {
	
	public static void main(String[] args) throws IOException {
//		MethodDesignParser dp = new MethodDesignParser();
//		IGraphDesign graphDesigner = new DotGraphDesign();
//		
//		graphDesigner.useDefault();
//		dp.parse(args, graphDesigner);
		
		MethodDesignParser dp = new MethodDesignParser();
		IGraphDesign graphDesigner = new SequenceGraphDesign();
		
		graphDesigner.useDefault();
		dp.parse(args, graphDesigner);
	}
	
}
