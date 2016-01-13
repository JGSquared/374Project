package problem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.AbstractGraphCode;
import problem.api.IGraphDesign;
import problem.code.GraphClassCloserCode;
import problem.code.GraphDeclarationCode;
import problem.code.GraphExtensionAndImplementCode;
import problem.code.GraphFieldCode;
import problem.code.GraphMethodCode;
import problem.code.GraphUsesCode;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = new FileProperties();
	private List<AbstractGraphCode> codeGetters = new ArrayList<AbstractGraphCode>();

	@Override
	public void addGraphCode(HashMap<String, String> items) {
		for (int i = 0; i < codeGetters.size(); i++) {
			sb.append(codeGetters.get(i).getCode(items));
		}
	}

	@Override
	public void initializeGraph() {
		sb.append("digraph G{\n");
		sb.append("rankdir=BT;\n");
	}
	
	@Override
	public void closeGraph() {
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
	public void addCodeGetter(AbstractGraphCode getter) {
		this.codeGetters.add(getter);
	}

	@Override
	public void removeCodeGetter(AbstractGraphCode getter) {
		this.codeGetters.remove(getter);		
	}
	
	public List<AbstractGraphCode> getCodeGetters() {
		return this.codeGetters;
	}

	public StringBuilder getGraphStringBuilder() {
		return sb;
	}

	@Override
	public void useDefault() {
		addCodeGetter(new GraphDeclarationCode());
		addCodeGetter(new GraphFieldCode());
		addCodeGetter(new GraphMethodCode());
		addCodeGetter(new GraphClassCloserCode());
		addCodeGetter(new GraphExtensionAndImplementCode());
		addCodeGetter(new GraphUsesCode());
	}

}
