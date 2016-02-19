package problem.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import problem.PhaseRunner;
import problem.jfactory.AbstractCheckListFactory;
import problem.jfactory.JCheckList;
import problem.jfactory.StandardCheckListFactory;

public class ImageUI extends JPanel {
	private PhaseRunner runner;
	private JFrame frame;
	private AbstractCheckListFactory factory;

	public ImageUI(PhaseRunner runner) {
		this.runner = runner;
		this.factory = new StandardCheckListFactory();
		frame = new JFrame("Pattern Detection");
//		frame.setContentPane(this);
		frame.setSize(800, 500);
		createCheckLists();
		createImage();
		this.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void createCheckLists() {
		JPanel newPanel = new JPanel();
		JCheckList decoratorList = this.factory.createCheckList("Decorator");
		JCheckList singletonList = this.factory.createCheckList("Singleton");
		JCheckList compositeList = this.factory.createCheckList("Composite");
		JCheckList adapterList = this.factory.createCheckList("Adapter");
		addCheckList(newPanel, decoratorList);
		addCheckList(newPanel, singletonList);
		addCheckList(newPanel, compositeList);
		addCheckList(newPanel, adapterList);
		newPanel.add(decoratorList);
		for (JCheckBox box : decoratorList.getSubCheckBoxes()) {
			newPanel.add(box);
		}
		frame.setContentPane(newPanel);
		this.revalidate();
		this.repaint();
	}
	
	private void addCheckList(JPanel panel, JCheckList list) {
		if (list == null) {
			return;
		}
		panel.add(list);
		for (JCheckBox box : list.getSubCheckBoxes()) {
			panel.add(box);
		}
	}
	
	private void createImage() {
		
	}

}
