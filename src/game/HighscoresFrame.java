package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import HighScores.HighScoreManager;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

public class HighscoresFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
private JTextArea content;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HighscoresFrame frame = new HighscoresFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HighscoresFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		 content = new JTextArea("");
		 content.setEditable(false);
		contentPane.add(content, BorderLayout.CENTER);
		
		
		
		this.setVisible(true);
		
	}
	
	public HighscoresFrame(String curr){
		this();
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"3x3 Classic",  "4x4 Classic", "5x5", "6x6", "7x7","3x3 Dual","4x4 Dual"}));
		if(curr.indexOf('M')<0)
			comboBox.setSelectedIndex(Integer.parseInt(curr)-3);
		else{
			comboBox.setSelectedIndex(Integer.parseInt(curr.substring(1)) +2);
		}
		comboBox.addActionListener(this);
		contentPane.add(comboBox, BorderLayout.NORTH);
		HighScoreManager hsm = new HighScoreManager(curr);
		content.setText(hsm.getHighscoreString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox){
			JComboBox comboBox=(JComboBox)(e.getSource());
			String newTable="";
			if(comboBox.getSelectedIndex() >4)
				newTable+="M";
			newTable+=comboBox.getSelectedItem().toString().substring(0,1);
			HighScoreManager hsm = new HighScoreManager(newTable);
			content.setText(hsm.getHighscoreString());
			
		}
		
	}

}
