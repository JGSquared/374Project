package problem.jfactory;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class JCheckList extends JCheckBox {
	private List<JCheckBox> subCheckBoxes = new ArrayList<>();

	public JCheckList() {
		super();
	}

	public JCheckList(Icon arg0) {
		super(arg0);
	}

	public JCheckList(String arg0) {
		super(arg0);
	}

	public JCheckList(Action arg0) {
		super(arg0);
	}

	public JCheckList(Icon arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public JCheckList(String arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public JCheckList(String arg0, Icon arg1) {
		super(arg0, arg1);
	}

	public JCheckList(String arg0, Icon arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	public List<JCheckBox> getSubCheckBoxes() {
		return subCheckBoxes;
	}

	public void setSubCheckBoxes(List<JCheckBox> subCheckBoxes) {
		this.subCheckBoxes = subCheckBoxes;
	}
	
	public void addSubCheckBox(JCheckBox checkBox) {
		this.subCheckBoxes.add(checkBox);
	}

}
