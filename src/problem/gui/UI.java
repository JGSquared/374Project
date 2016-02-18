package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class UI extends JPanel{
	private static final long serialVersionUID = -3778143600831095610L;
	private static final String loadButton = "Load Config";
	private static final String analyze = "Analyze";
	
	JFrame frame;
	
	public UI() {
		JFrame frame = new JFrame("Pattern Detection");
		frame.setContentPane(this);
		frame.setSize(500, 500);
		
		createButtons();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void createButtons() {
		JButton loadButton = new JButton();
		loadButton.setText(UI.loadButton);
		loadButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cool");
			}
		});
		this.add(loadButton);
		
		JButton analyzeButton = new JButton();
		analyzeButton.setText(UI.analyze);
		analyzeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				loadBar();
			}
		});
		this.add(analyzeButton);
	}
	
	public void loadBar() {
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(100);
		this.add(progressBar);
	}
}
