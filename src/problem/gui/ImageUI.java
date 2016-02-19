package problem.gui;

import java.awt.BorderLayout;
import java.awt.image.ImageConsumer;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import problem.ImageComponent;
import problem.ImageProxy;
import problem.PhaseRunner;
import problem.jfactory.AbstractCheckListFactory;
import problem.jfactory.JCheckList;
import problem.jfactory.StandardCheckListFactory;

public class ImageUI extends JPanel {
	private PhaseRunner runner;
	private JFrame frame;
	private AbstractCheckListFactory factory;
	ImageComponent imageComponent;
	Icon icon;

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
//		newPanel.add(decoratorList);
		for (JCheckBox box : decoratorList.getSubCheckBoxes()) {
			newPanel.add(box, BorderLayout.NORTH);
		}
		frame.setContentPane(newPanel);
		icon = new ImageProxy(frame);
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
		JPanel newPanel = new JPanel();
		imageComponent = new ImageComponent(icon);
		newPanel.add(imageComponent);
		frame.getContentPane().add(newPanel);
		this.revalidate();
		this.repaint();
	}

}
