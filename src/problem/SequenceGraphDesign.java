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
import problem.code.GraphSequenceMethodCode;
import problem.code.GraphSequenceNodeCode;

public class SequenceGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = FileProperties.getInstance();
	private List<IGraphCode> codeGetters = new ArrayList<IGraphCode>();

	@Override
	public void addGraphCode(HashMap<String, String> items) {
		for (int i = 0; i < codeGetters.size(); i++) {
			sb.append(codeGetters.get(i).getCode(null));
		}
	}

	@Override
	public void initializeGraph() {
		return;
	}

	@Override
	public void closeGraph() {
		return;

	}

	@Override
	public void generateGraph() throws IOException {
		OutputStream out = new FileOutputStream(fp.fileIn);
		out.write(sb.toString().getBytes());
		out.close();

		Runtime rt = Runtime.getRuntime();
		String[] outputFile = fp.fileOut.split("\\.");
		Process pr = rt
				.exec(new String[] {
						"cmd.exe", "", "" + fp.sdEditPath + " -o " + fp.fileOut + " -t " + outputFile[outputFile.length - 1] + " " + fp.fileIn});

	}

	@Override
	public void addCodeGetter(IGraphCode getter) {
		this.codeGetters.add(getter);
	}
	
	public List<IGraphCode> getCodeGetters() {
		return this.codeGetters;
	}

	@Override
	public void removeCodeGetter(IGraphCode getter) {
		this.codeGetters.remove(getter);
	}

	@Override
	public void useDefaultCodeGetters() {
		IGraphCode item1 = new GraphSequenceNodeCode();
		IGraphCode item2 = new GraphSequenceMethodCode();
		addCodeGetter(item1);
		addCodeGetter(item2);
	}

	@Override
	public void addPatternDetector(IPatternDetector detector) {
		// TODO: Unused exception?
		return;
	}

	@Override
	public void removePatternDetector(IPatternDetector detector) {
		// TODO: Unused exception?
		return;
	}

	@Override
	public void useDefaultPatternDetectors() {
		// TODO: Unused exception?
		return;
	}

}
