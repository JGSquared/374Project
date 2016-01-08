package problem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.GraphCode;
import problem.api.IGraphDesign;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = new FileProperties();
	private List<GraphCode> codeGetters = new ArrayList<GraphCode>();

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
	public void addCodeGetter(GraphCode getter) {
		this.codeGetters.add(getter);
	}

	@Override
	public void removeCodeGetter(GraphCode getter) {
		this.codeGetters.remove(getter);		
	}
	
	public List<GraphCode> getCodeGetters() {
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
