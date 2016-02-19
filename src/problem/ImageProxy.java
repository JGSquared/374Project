package problem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import sun.misc.IOUtils;

public class ImageProxy implements Icon {

	ImageIcon imageIcon;
	Thread retrievalThread;
	boolean retrieving = false;
	JFrame frame;
     
	public ImageProxy(JFrame frame) {
		this.frame = frame;
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
			imageIcon.paintIcon(c, g, x, y);
			frame.getContentPane().add(c);
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
