package problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.AbstractGraphCode;
import problem.api.IGraphDesign;
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
		//TODO
		sb.append("\n");
	}

	@Override
	public void closeGraph() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateGraph() throws IOException {
		// TODO Auto-generated method stub

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
	}

}
