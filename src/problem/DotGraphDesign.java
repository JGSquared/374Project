package problem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.IGraphCode;
import problem.api.IGraphDesign;
import problem.api.IPatternDetector;
import problem.code.GraphClassCloserCode;
import problem.code.GraphDeclarationCode;
import problem.code.GraphExtensionAndImplementCode;
import problem.code.GraphFieldCode;
import problem.code.GraphMethodCode;
import problem.code.GraphUsesCode;
import problem.patterns.AdapterPatternDetector;
import problem.patterns.DecoratorPatternDetector;
import problem.patterns.SingletonPatternDetector;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = FileProperties.getInstance();
	private List<IGraphCode> codeGetters = new ArrayList<IGraphCode>();
	private List<HashMap<String, String>> classCode = new ArrayList<>();
	private List<IPatternDetector> patternDetectors = new ArrayList<IPatternDetector>();

	@Override
	public void addGraphCode(HashMap<String, String> items) {
		for (int i = 0; i < codeGetters.size(); i++) {
			sb.append(codeGetters.get(i).getCode(items));
		}
		this.classCode.add(items);
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT;\n");
	}
	
	@Override
	public void closeGraph() {
		for (IPatternDetector detector : this.patternDetectors) {
			detector.detectPattern(classCode, sb);
		}
		int colorOffset;
		while ((colorOffset = sb.indexOf(Constants.COLOR_OFFSET)) != -1) {
			sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), "");
		}
		int labelOffset;
		while ((labelOffset = sb.indexOf(Constants.LABEL_OFFSET)) != -1) {
			sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), "");
		}
		int arrowOffset;
		while ((arrowOffset = sb.indexOf(Constants.ARROW_OFFSET)) != -1) {
			sb.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(), "");
		}
		sb.append("}");
	}

	@Override
	public void generateGraph() throws IOException {
		OutputStream out = new FileOutputStream(fp.fileIn);
		out.write(sb.toString().getBytes());
		out.close();

		Runtime rt = Runtime.getRuntime();
		Process pr = rt
				.exec(new String[] {
						"cmd.exe", "/k", "\"" + fp.graphVizPath + "\" " + fp.flags + " " + fp.fileIn + " > " + fp.fileOut});

	}

	@Override
	public void addCodeGetter(IGraphCode getter) {
		this.codeGetters.add(getter);
	}

	@Override
	public void removeCodeGetter(IGraphCode getter) {
		this.codeGetters.remove(getter);		
	}
	
	public List<IGraphCode> getCodeGetters() {
		return this.codeGetters;
	}

	public StringBuilder getGraphStringBuilder() {
		return sb;
	}

	@Override
	public void useDefaultCodeGetters() {
		IGraphCode item1 = new GraphDeclarationCode();
		IGraphCode item2 = new GraphFieldCode();
		IGraphCode item3 = new GraphMethodCode();
		IGraphCode item4 = new GraphClassCloserCode();
		IGraphCode item5 = new GraphExtensionAndImplementCode();
		IGraphCode item6 = new GraphUsesCode();
		addCodeGetter(item1);
		addCodeGetter(item2);
		addCodeGetter(item3);
		addCodeGetter(item4);
		addCodeGetter(item5);
		addCodeGetter(item6);		
	}

	@Override
	public void addPatternDetector(IPatternDetector detector) {
		this.patternDetectors.add(detector);
	}

	@Override
	public void removePatternDetector(IPatternDetector detector) {
		this.patternDetectors.remove(detector);
	}

	@Override
	public void useDefaultPatternDetectors() {
		addPatternDetector(new SingletonPatternDetector());
		addPatternDetector(new DecoratorPatternDetector());
		addPatternDetector(new AdapterPatternDetector());
	}

}
