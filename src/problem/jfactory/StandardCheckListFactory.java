package problem.jfactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;

import problem.ClassStorage;
import problem.Helpers;
import problem.api.IClass;

public class StandardCheckListFactory extends AbstractCheckListFactory {
	private List<IClass> classes;

	public StandardCheckListFactory() {
		classes = ClassStorage.getInstance().getClasses();
	}

	@Override
	public JCheckList createCheckList(String patternLabel) {
		final JCheckList checkList = new JCheckList(patternLabel);
		for (IClass c : classes) {
			if (c.getPatternLabel().equals(patternLabel)) {
				JCheckBox checkBox = new JCheckBox(Helpers.getName(c.getClassName()));
				checkBox.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
						// Might be an unnecessary listener
						JCheckBox box = (JCheckBox) e.getSource();
						if (box.isSelected()) {
							box.setSelected(false);
						} else {
							box.setSelected(true);
						}
					}
				});
				checkList.addSubCheckBox(checkBox);
			}
		}
		if (!checkList.getSubCheckBoxes().isEmpty()) {
			checkList.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for (JCheckBox checkBox : checkList.getSubCheckBoxes()) {
						checkBox.doClick();
					}
				}
			});
			return checkList;
		}
		
		// Pattern not found
		return null;
	}

}
