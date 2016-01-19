package problem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.IGraphCode;
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
	private List<IGraphCode> codeGetters = new ArrayList<IGraphCode>();

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
	public void useDefault() {
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

}
