package problem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.AbstractGraphCode;
import problem.api.IGraphDesign;
import problem.code.GraphSequenceMethodCode;
import problem.code.GraphSequenceNodeCode;

public class SequenceGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = new FileProperties();
	private List<AbstractGraphCode> codeGetters = new ArrayList<AbstractGraphCode>();

	@Override
	public void addGraphCode(HashMap<String, String> items) {
		for (int i = 0; i < codeGetters.size(); i++) {
			sb.append(codeGetters.get(i).getCode(items));
		}
		System.out.println(sb.toString());
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
						"cmd.exe", "/k", "" + fp.sdEditPath + " -o " + fp.fileOut + " -t " + outputFile[outputFile.length - 1] + " --threaded=false " + fp.fileIn});

	}

	@Override
	public void addCodeGetter(AbstractGraphCode getter) {
		this.codeGetters.add(getter);
	}

	@Override
	public void removeCodeGetter(AbstractGraphCode getter) {
		this.codeGetters.remove(getter);
	}

	@Override
	public void useDefault() {
		addCodeGetter(new GraphSequenceNodeCode());
		addCodeGetter(new GraphSequenceMethodCode());
	}

}
