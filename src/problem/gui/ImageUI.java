package problem.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import problem.ClassStorage;
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
	private List<JCheckList> lists = new ArrayList<>();
	ImageComponent imageComponent;
	Icon icon;
	JProgressBar progressBar;

	public ImageUI(PhaseRunner runner, JProgressBar progressBar) {
		this.progressBar = progressBar;
		this.runner = runner;
		this.factory = new StandardCheckListFactory();
		frame = new JFrame("Pattern Detection");
		// frame.setContentPane(this);
		frame.setSize(1500, 1200);
		createCheckLists();
		createImage();
		startLiveUpdateThread();
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
		progressBar.setValue(progressBar.getValue() + 15);
		addCheckList(newPanel, decoratorList);
		progressBar.setValue(progressBar.getValue() + 15);
		addCheckList(newPanel, singletonList);
		progressBar.setValue(progressBar.getValue() + 15);
		addCheckList(newPanel, compositeList);
		progressBar.setValue(progressBar.getValue() + 15);
		addCheckList(newPanel, adapterList);
		progressBar.setValue(progressBar.getValue() + 15);
//		newPanel.add(decoratorList);
//		for (JCheckBox box : decoratorList.getSubCheckBoxes()) {
//			newPanel.add(box, BorderLayout.NORTH);
//		}
		frame.setContentPane(newPanel);
		icon = new ImageProxy(frame, runner);
		progressBar.setValue(progressBar.getValue() + 15);
//		this.frame.setVisible(true);
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
		this.lists.add(list);
	}

	private void createImage() {
		JPanel newPanel = new JPanel();
		imageComponent = new ImageComponent(icon);
		newPanel.add(imageComponent);
		frame.getContentPane().add(newPanel);
		this.revalidate();
		this.repaint();
//		while (true) {
//			if (((ImageProxy) icon).isCompleted()) {
//				break;
//			}
//		}
		progressBar.setValue(progressBar.getValue() + 10);
	}

	private void startLiveUpdateThread() {
		Thread thread = new Thread(new Runnable() {
			private List<String> previousChecked = new ArrayList<>();
			private List<String> currentChecked = new ArrayList<>();

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						this.currentChecked = getCheckedList();
						if (!this.currentChecked.equals(this.previousChecked)) {
							ClassStorage.getInstance().setClassesToLabel(this.currentChecked);
							setChanged();
							this.previousChecked = this.currentChecked;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	private List<String> getCheckedList() {
		List<String> result = new ArrayList<>();
		for (JCheckList list : this.lists) {
			for (JCheckBox box : list.getSubCheckBoxes()) {
				if (box.isSelected()) {
					result.add(box.getText());
				}
			}
		}
		return result;
	}

	private void setChanged() {
//		this.runner.createGraph();
		((ImageProxy) icon).setChanged();
	}

}
