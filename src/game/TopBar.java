package game;

import javax.swing.*;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.ObjectInputStream.GetField;
import java.util.Set;

public class TopBar extends JPanel{

	public static JLabel lblScore;
	 private JButton restart;
	 private JComboBox comboBox_grid;
	 private JToggleButton tglMulti;
	 private JComboBox comboBox_skin;
	 private JButton btnHighscores;
	 private JPanel panel;
	 private JPanel panelCenter;
	 public static JLabel lblTarget;
	 private JSeparator separator;
	 private JSeparator separator_1;
	 private JButton btnUndo;
	 private JButton btnChangeTiles;
	/**
	 * Create the panel.
	 */
	public TopBar() {
		//setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setLayout(new BorderLayout());
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
	
		tglMulti = new JToggleButton("Dual");
		tglMulti.setFocusable(false);
		panelCenter.add(tglMulti);
		
		comboBox_grid = new JComboBox();
		comboBox_grid.addItem(3);
		comboBox_grid.addItem(4);
		comboBox_grid.addItem(5);
		comboBox_grid.addItem(6);
		comboBox_grid.addItem(7);
		comboBox_grid.setSelectedIndex(1);
		comboBox_grid.setFocusable(false);
		comboBox_grid.setName("comboBox_grid");
		panelCenter.add(comboBox_grid);
		
		lblTarget = new JLabel("Target");
		lblTarget.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblTarget);
		
		separator_1 = new JSeparator();
		panel.add(separator_1);
		
		separator = new JSeparator();
		panel.add(separator);
		lblScore=new JLabel("score: 0");
		lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblScore);
		restart=new JButton("Restart");
		restart.setFocusable(false);
		//restart.setPreferredSize(new Dimension(50, 50));
		panelCenter.add(restart);
		
		comboBox_skin = new JComboBox();
		comboBox_skin.setName("comboBox_skin");
		comboBox_skin.setModel(new DefaultComboBoxModel(new String[] {"Classic", "Chad Gadya", "Make Your Own"}));
		comboBox_skin.setFocusable(false);
		panelCenter.add(comboBox_skin);
		
		btnHighscores = new JButton("Highscores");
		btnHighscores.setHorizontalAlignment(SwingConstants.RIGHT);
		btnHighscores.setFocusable(false);
		panel.add(btnHighscores);
		
		btnUndo = new JButton("Undo");
		btnUndo.setFocusable(false);
		btnUndo.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnUndo);
		
		btnChangeTiles = new JButton("Change Tiles");
		btnChangeTiles.setFocusable(false);
		panel.add(btnChangeTiles);
		btnChangeTiles.setVisible(false);
		

	}
	public void setTarget(int size){
		lblTarget.setText("Target: "+(int)(Math.pow(2, size+7)));
	}
	public JButton getBtnHighscore(){
		return btnHighscores;
	}
	public JLabel getScore(){
		return lblScore;
	}
	public JLabel getTarget(){
		return lblTarget;
	}
	public JButton getChangeBtn(){
		return btnChangeTiles;
	}
public JButton getRestart(){
	return restart;
}
public JComboBox getGridSize(){
	return comboBox_grid;
}
public JToggleButton getMultiToggle(){
	return tglMulti;
}
public JComboBox getSkinCombo(){
	return comboBox_skin;
}
public void setToggle(){
	tglMulti.setSelected(false);
}
public JButton getUndoBtn(){
	return btnUndo;
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
