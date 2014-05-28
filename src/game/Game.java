package game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.IconView;
import javax.swing.SwingConstants;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import HighScores.HighScoreManager;

public class Game extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel topPanel;
	private JPanel gridPanel;
	private JPanel secGridPanel;
	private boolean multiMode=false;
	private JFrame makeYourOwnFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
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
	public Game() {
		super("2048");
		//playSound("alrighty.wav");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250,100,580,670);
		//setBounds(100,100,402,464);   SIZE 3
		//setBounds(100,100,626,686);   SIZE 5
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		topPanel=new TopBar();
		((TopBar)(topPanel)).getRestart().addActionListener(this);
		((TopBar)(topPanel)).getGridSize().addActionListener(this);
		((TopBar)(topPanel)).getMultiToggle().addActionListener(this);
		((TopBar)(topPanel)).getSkinCombo().addActionListener(this);
		((TopBar)(topPanel)).getBtnHighscore().addActionListener(this);
		((TopBar)(topPanel)).getUndoBtn().addActionListener(this);
		((TopBar)(topPanel)).getChangeBtn().addActionListener(this);
		((TopBar)(topPanel)).getTglDoubleTrouble().addActionListener(this);
		((TopBar)(topPanel)).getHelpbtn().addActionListener(this);
		contentPane.add(topPanel,BorderLayout.NORTH);
		
		gridPanel=new Board(4,(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
		contentPane.add(gridPanel,BorderLayout.CENTER);
		addKeyListener((KeyListener)gridPanel);
		if(multiMode){
		secGridPanel=new Board(4,(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
		contentPane.add(secGridPanel,BorderLayout.WEST);
		addKeyListener((KeyListener)secGridPanel);
		}
		
		((TopBar)topPanel).setTarget(((Board)gridPanel).getboardSize());
		
		
		this.setResizable(false);
		this.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//restart
		if(e.getSource() instanceof JButton){
			JButton button=(JButton)(e.getSource());
			if(button.getText()=="Restart"){
				restart();
//				this.removeKeyListener((KeyListener)gridPanel);
//				gridPanel.removeAll();
//				this.contentPane.remove(gridPanel);
//				gridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem(),(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
//				addKeyListener((KeyListener)gridPanel);
//				((TopBar)topPanel).lblScore.setText("score: 0");
//				((TopBar)topPanel).lblScore.setText(getContentPane().getHeight()+" wid "+getContentPane().getWidth());
//				this.add(gridPanel,BorderLayout.CENTER);
//				
//				if(secGridPanel instanceof JPanel){
//					this.removeKeyListener((KeyListener)secGridPanel);
//					secGridPanel.removeAll();
//					this.contentPane.remove(secGridPanel);
//					secGridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem(),(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
//					addKeyListener((KeyListener)secGridPanel);
//					this.add(secGridPanel,BorderLayout.WEST);
//				}
//				
//				this.contentPane.revalidate();
//				this.contentPane.repaint();
			}
			else if(button.getText()=="Highscores")
			{
				String curr="";
				if(((Board)gridPanel).getMult())
					curr="M";
				curr+=((Board)gridPanel).getboardSize();
				HighscoresFrame hf=new HighscoresFrame(curr);
				

			}
			else if(button.getText().equals("Undo")){
				((Board)gridPanel).Undo();
				if(secGridPanel instanceof JPanel){
					((Board)secGridPanel).Undo();
				}
			}
			else if(button.getText().equals("Change Tiles")){
					makeYourOwnFrame=new MakeYourOwnFrame((Board)gridPanel,(Board)secGridPanel);
					((Board)gridPanel).setFlush(true);
					if(secGridPanel instanceof JPanel)
					((Board)secGridPanel).setFlush(true);
				}
			
			else if(button.getText().equals("Help")){
				String help ="Help:\n"+
			"You may choose the tile type you wish to play with:\nClassic: The classic tiles.\nChad Gadya: Take part of the holiday festivities with these tiles.\nMake Your Own: Dynamically choose your own tiles (classic tiles are the default).\n\n"+
						"You may also choose the board size (ranging from 3 to 7), each size has a different highscore table.\n"+
						"Highscores: Shows the top ten high scores of a game mode.\n"+
						"Double Trouble: Double the tiles! Double the fun! (creates two tiles each turn instead of one).\n"+
						"Dual: Play on two boards in one game (only possible on sizes 3x3 and 4x4).\n"+
						"Restart: Restarts the game (be careful not to lose your score!).\n"+
						"Undo: Undo your last move.";
				JOptionPane.showMessageDialog(this, help, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		//-------------------
		
		
		
		if(e.getSource()instanceof JComboBox){
			JComboBox comboBox=(JComboBox)(e.getSource());
			//changing grid size
			if(comboBox.getName()=="comboBox_grid"){
				int num=((Board)gridPanel).getNumOfTilesCreated();
			this.removeKeyListener((KeyListener)gridPanel);
			gridPanel.removeAll();
			this.contentPane.remove(gridPanel);
			gridPanel=new Board((int)comboBox.getSelectedItem(),(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
			addKeyListener((KeyListener)gridPanel);
			((TopBar)topPanel).lblScore.setText("score: 0");
			this.add(gridPanel);
			changeSize((int)comboBox.getSelectedItem(),num);
			((TopBar)topPanel).setTarget(((Board)gridPanel).getboardSize());
			this.contentPane.revalidate();
			this.contentPane.repaint();
			}//---------------
			
			else {
				//changing skin
				if (comboBox.getName()=="comboBox_skin") {
				((Board)gridPanel).setSkin(comboBox.getSelectedIndex());
				if(secGridPanel instanceof JPanel){
					((Board)secGridPanel).setSkin(comboBox.getSelectedIndex());
				}
				if(comboBox.getSelectedIndex()==1){
					playSound("Chad Gadya.wav");
					((TopBar)(topPanel)).getChangeBtn().setVisible(false);
				}
				else if(comboBox.getSelectedIndex()==2){
					//addMissingFiles();
					((Board)gridPanel).reload();
					if(((Board)secGridPanel) instanceof JPanel)
						((Board)secGridPanel).reload();
					((TopBar)(topPanel)).getChangeBtn().setVisible(true);
					((TopBar)(topPanel)).getChangeBtn().setVisible(true);
				}
				else{
					((TopBar)(topPanel)).getChangeBtn().setVisible(false);
					((TopBar)(topPanel)).getChangeBtn().setVisible(false);
				}
				this.contentPane.repaint();
			}
				//----------------------
			}
		}
		//--------------------
		//Multi Mode
		if(e.getSource() instanceof JToggleButton){
			JToggleButton toggle=(JToggleButton)(e.getSource());
			if(toggle.getText().equals("Double Trouble")){
				int num;
				if(toggle.isSelected())
					num=2;
				else 
					num=1;
				((Board)gridPanel).setNumOfTilesCreated(num);
				if(secGridPanel instanceof JPanel)
					((Board)secGridPanel).setNumOfTilesCreated(num);
			}
		else{	//Multi mode toggle
			
			int num=((Board)gridPanel).getNumOfTilesCreated();
			restart();
			
			if(toggle.isSelected()){
				multiMode=true;
				((Board)gridPanel).setNumOfTilesCreated(num);
			}
			else 
				multiMode=false;
			((Board)gridPanel).setMulti(multiMode);
			if(secGridPanel instanceof Board){
				((Board)secGridPanel).setMulti(multiMode);
				((Board)secGridPanel).setNumOfTilesCreated(num);
			}
			changeSize((int)((TopBar)topPanel).getGridSize().getSelectedItem(),num);
		}
		}
		//--------------------
	}
	
	private void changeSize(int grid,int tilesNum){
		int height=0,width=0;
		switch (grid) {
		case 3:
			 width=422;//402
			 height=523;//464
			// ((TopBar)topPanel).add(((TopBar)topPanel).getChangeBtn(),BorderLayout.SOUTH);
			break;
		case 4:
			width=580;
			 height=670; 
			break;
		case 5:
			width=626;
			 height=706; 
			break;
		case 6:					
			width=765;
			 height=820; 
			break;
		case 7:							
			width=850;
			 height=820; 
			break;
		default:
			break;
		}
		if(grid>4){
			((TopBar)topPanel).getMultiToggle().setSelected(false);
			 multiMode=false;
			 ((TopBar)topPanel).getMultiToggle().setEnabled(false);
		}
		else {
			 ((TopBar)topPanel).getMultiToggle().setEnabled(true);
		}
		if(secGridPanel instanceof JPanel){
			this.removeKeyListener((KeyListener)secGridPanel);
			this.contentPane.remove(secGridPanel);
			}
		if(multiMode){
			width*=2;
			if(grid==3)
				width+=20;
			if(grid==5)
				width+=60;
			secGridPanel=new Board(grid,(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
			((Board)secGridPanel).setNumOfTilesCreated(tilesNum);
			addKeyListener((KeyListener)secGridPanel);
			contentPane.add(secGridPanel,BorderLayout.WEST);
		}
		((Board)gridPanel).setNumOfTilesCreated(tilesNum);
		setBounds(this.getX(),this.getY(),width,height);
	}

	
	private void restart(){
		int numofTiles =((Board)gridPanel).getNumOfTilesCreated();
		((Board)gridPanel).setGameOver(false);
		this.removeKeyListener((KeyListener)gridPanel);
		gridPanel.removeAll();
		this.contentPane.remove(gridPanel);
		gridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem(),(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
		addKeyListener((KeyListener)gridPanel);
		((TopBar)topPanel).lblScore.setText("score: 0");
		//((TopBar)topPanel).lblScore.setText(getContentPane().getHeight()+" wid "+getContentPane().getWidth());
		this.add(gridPanel,BorderLayout.CENTER);
		((Board)gridPanel).setNumOfTilesCreated(numofTiles);
		if(secGridPanel instanceof JPanel){
			this.removeKeyListener((KeyListener)secGridPanel);
			secGridPanel.removeAll();
			this.contentPane.remove(secGridPanel);
			secGridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem(),(int)(((TopBar)topPanel).getSkinCombo().getSelectedIndex()));
			addKeyListener((KeyListener)secGridPanel);
			this.add(secGridPanel,BorderLayout.WEST);
			((Board)secGridPanel).setNumOfTilesCreated(numofTiles);
			((Board)gridPanel).reload();
			if(secGridPanel !=null)
				((Board)secGridPanel).reload();
			
		}
		
		this.contentPane.revalidate();
		this.contentPane.repaint();

	}
	private void playSound(String sound){
		// open the sound file as a Java input stream
		try{
	    String gongFile = "./src/Sounds/"+sound;
	    InputStream in = new FileInputStream(gongFile);

	    // create an audiostream from the inputstream
	    AudioStream audioStream = new AudioStream(in);

	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);
		}
		catch(Exception e){
			System.out.println("problem playing audio file"+sound);
		}
	}
	
	
	
}
