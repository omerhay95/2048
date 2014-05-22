package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import HighScores.HighScoreManager;

public class HighscoresFrame extends JFrame {

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
		
		HighScoreManager hsm = new HighScoreManager(curr);
		content.setText(hsm.getHighscoreString());
	}

}
