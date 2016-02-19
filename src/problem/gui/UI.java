package problem.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import problem.ConfigProperties;
import problem.PhaseRunner;

public class UI extends JPanel{
	private static final long serialVersionUID = -3778143600831095610L;
	private static final String loadButton = "Load Config";
	private static final String analyze = "Analyze";
	private String fileSelected;
	
	JFrame frame;
	JProgressBar progressBar;
	PhaseRunner runner;
	
	public UI(PhaseRunner runner) {
		this.runner = runner;
		frame = new JFrame("Pattern Detection");
		frame.setContentPane(this);
		frame.setSize(500, 250);
		this.setLayout(new BorderLayout());
		createButtons();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void createButtons() {
		JPanel buttonPanel = new JPanel();
		JButton loadButton = new JButton();
		loadButton.setText(UI.loadButton);
		loadButton.setPreferredSize(new Dimension(200, 50));
		loadButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(loadButton);
				fileSelected = fc.getSelectedFile().toString();
				System.out.println("SELECTED");
			}
		});
		buttonPanel.add(loadButton);
		JButton analyzeButton = new JButton();
		analyzeButton.setText(UI.analyze);
		analyzeButton.setPreferredSize(new Dimension(200, 50));
		analyzeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ConfigProperties.getInstance().setupConfig(fileSelected);
					runner.run();
					clearScreen();
					createDisplay();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonPanel.add(analyzeButton);
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        ConfigProperties.getInstance().setProgressBar(progressBar);
        this.add(buttonPanel, BorderLayout.CENTER);
		this.add(progressBar, BorderLayout.SOUTH);
	}
	
	public void clearScreen() {
		this.removeAll();
		this.repaint();
	}
	
	public void createDisplay() {
		frame.setVisible(false);
		ImageUI nextUI = new ImageUI(runner);
//		JPanel imagePanel = new JPanel();
//		imagePanel.setLayout(new BorderLayout());
//		ImageProxy image = new ImageProxy();
//		image.paintIcon(imagePanel, this.getGraphics());
//		this.add(imagePanel, BorderLayout.CENTER);
//		this.revalidate();
//		this.repaint();
	}
	
}
