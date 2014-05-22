package game;

import javax.swing.*;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class TopBar extends JPanel{

	public static JLabel lblScore;
	 private JButton restart;
	 private JComboBox comboBox;
	 private JToggleButton tglMulti;
	 private JComboBox comboBox_type;
	/**
	 * Create the panel.
	 */
	public TopBar() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tglMulti = new JToggleButton("Dual");
		tglMulti.setFocusable(false);
		add(tglMulti);
		
		comboBox = new JComboBox();
		comboBox.addItem(3);
		comboBox.addItem(4);
		comboBox.addItem(5);
		comboBox.addItem(6);
		comboBox.addItem(7);
		comboBox.setSelectedIndex(1);
		comboBox.setFocusable(false);
		add(comboBox);
		lblScore=new JLabel("score: 0");
		this.add(lblScore);
		restart=new JButton("Restart");
		restart.setFocusable(false);
		//restart.setPreferredSize(new Dimension(50, 50));
		this.add(restart);
		
		comboBox_type = new JComboBox();
		comboBox_type.setModel(new DefaultComboBoxModel(new String[] {"Classic", "Chad Gadya!!!", "Other"}));
		comboBox_type.setFocusable(false);
		add(comboBox_type);
		
	

	}
public JButton getRestart(){
	return restart;
}
public JComboBox getGridSize(){
	return comboBox;
}
public JToggleButton getMultiToggle(){
	return tglMulti;
}
public void setToggle(){
	tglMulti.setSelected(false);
}
	
	 public static BufferedImage resizeImage(final Image image, int width, int height) {
	        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        final Graphics2D graphics2D = bufferedImage.createGraphics();
	        graphics2D.setComposite(AlphaComposite.Src);
	        graphics2D.drawImage(image, 0, 0, width, height, null);
	        graphics2D.dispose();
	 
	        return bufferedImage;
	    }
}
