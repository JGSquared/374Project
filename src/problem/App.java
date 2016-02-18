package problem;

import java.io.IOException;

import problem.gui.UI;

public class App {
	
	public static void main(String[] args) throws IOException {
		
//		IDesignParser dp = new ClassDesignParser();
//		IGraphDesign graphDesigner = new DotGraphDesign();
//		
//		graphDesigner.useDefaultCodeGetters();
//		graphDesigner.useDefaultPatternDetectors();
//		dp.parse(args, graphDesigner);
//		
//		for (Pattern p : PatternStorage.getPatterns()) {
//			System.out.println("Class name: " + p.getClassName());
//			System.out.println("Pattern: " + p.getLabel());
//			for (Pattern p2 : p.getRelatedClasses()) {
//				System.out.println("Related class: " + p2.getClassName());
//			}
//		}
		
		UI ui = new UI();
	}
	
}
