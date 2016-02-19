package problem;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sun.misc.IOUtils;

public class ImageProxy implements Icon {

	ImageIcon imageIcon;
	Thread retrievalThread;
	boolean retrieving = false;
	JFrame frame;
	JLabel label;
	JScrollPane pane;
	JPanel newPanel;
     
	public ImageProxy(JFrame frame) {
		newPanel = new JPanel();
		this.frame = frame;
		label = new JLabel();
		newPanel.add(label);
		pane = new JScrollPane(newPanel);
		pane.setPreferredSize(new Dimension(1200, 900));
		frame.getContentPane().add(pane);
//		frame.getContentPane().setPreferredSize(new Dimension(600, 400));
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
     
	public int getIconWidth() {
		if (imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
			return 800;
		}
	}
 
	public int getIconHeight() {
		if (imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
			return 600;
		}
	}
     
	public void paintIcon(final Component c, Graphics  g, int x,  int y) {
		if (imageIcon != null) {
			System.out.println("HERE");
			label.setIcon(imageIcon);
			frame.revalidate();
			frame.repaint();
		} else {
//			JTextArea text = new JTextArea(0, 0);
//			text.setText("Loading Image, please wait...");
//			frame.getContentPane().add(text);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
//							imageIcon = new ImageIcon(imageURL, "CD Cover");
							
							Runtime rt = Runtime.getRuntime();
							Process pr = rt
									.exec(new String[] {
											"cmd.exe", "/k", "\"" + ConfigProperties.getInstance().getDotPath() + "\" -Tpng" + " " +
													ConfigProperties.getInstance().getOutputFolder() + "\\" + Constants.OUTFILE_NAME + " > "+
													ConfigProperties.getInstance().getOutputFolder() + "\\" + "output.png"});
							
							InputStream in = new FileInputStream(ConfigProperties.getInstance().getOutputFolder() + "\\" + "output.png");
							byte[] data = IOUtils.readFully(in, -1, false);
							imageIcon = new ImageIcon(data);
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
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
