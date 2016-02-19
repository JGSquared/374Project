package problem;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ImageProxy {
	Thread retrievalThread;
	boolean retrieving = false;
     
	public ImageProxy() {}
     
	public void paintIcon(JPanel c, Graphics  g) {
		JTextArea text = new JTextArea(0, 0);
		text.setText("Loading Image, please wait...");
		c.add(text, BorderLayout.CENTER);
		if (!retrieving) {
			retrieving = true;

			retrievalThread = new Thread(new Runnable() {
				public void run() {
					try {
						// Go through phases here
//						String[] phases = ConfigProperties.getInstance().getPhases();
//						for (String phase : phases) {
//							
//						}
						
						
						c.revalidate();
						c.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			retrievalThread.start();
		}
	}
	
//	public void generateGraph() throws IOException {
//		File newOutFile = new File("./input_output/newOutput");
//		OutputStream out = new FileOutputStream(newOutFile);
//		out.write(sb.toString().getBytes());
//		out.close();
//
//		Runtime rt = Runtime.getRuntime();
//		Process pr = rt
//				.exec(new String[] {
//						"cmd.exe", "/k", "\"" + fp.graphVizPath + "\" " + fp.flags + " " + fp.fileIn + " > " + fp.fileOut});
//
//	}
//	JTextArea text = new JTextArea(0, 0);
//	text.setText("Loading Image, please wait...");
//	c.add(text, BorderLayout.CENTER);
}