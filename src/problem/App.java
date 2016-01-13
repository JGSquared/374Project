package problem;

import java.io.IOException;

import problem.api.IGraphDesign;

public class App {
	
	public static void main(String[] args) throws IOException {
		MethodDesignParser dp = new MethodDesignParser();
		IGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefault();
		dp.parse(args, graphDesigner);	
	}
	
}
