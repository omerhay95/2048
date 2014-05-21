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

public class Game extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel topPanel;
	private JPanel gridPanel;
	private JPanel secGridPanel;
	private boolean multiMode=false;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,580,650);
		//setBounds(100,100,402,464);   SIZE 3
		//setBounds(100,100,626,686);   SIZE 5
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		gridPanel=new Board(4);
		contentPane.add(gridPanel,BorderLayout.CENTER);
		addKeyListener((KeyListener)gridPanel);
		if(multiMode){
		secGridPanel=new Board(4);
		contentPane.add(secGridPanel,BorderLayout.WEST);
		addKeyListener((KeyListener)secGridPanel);
		}
		topPanel=new TopBar();
		((TopBar)(topPanel)).getRestart().addActionListener(this);
		((TopBar)(topPanel)).getGridSize().addActionListener(this);
		((TopBar)(topPanel)).getMultiToggle().addActionListener(this);
		contentPane.add(topPanel,BorderLayout.NORTH);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//restart
		if(e.getSource() instanceof JButton){
			JButton button=(JButton)(e.getSource());
			if(button.getText()=="Restart"){
				this.removeKeyListener((KeyListener)gridPanel);
				gridPanel.removeAll();
				this.contentPane.remove(gridPanel);
				gridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem());
				addKeyListener((KeyListener)gridPanel);
				((TopBar)topPanel).lblScore.setText("score: 0");
				//((TopBar)topPanel).lblScore.setText(getContentPane().getHeight()+" wid "+getContentPane().getWidth());
				this.add(gridPanel,BorderLayout.CENTER);
				
				if(secGridPanel instanceof JPanel){
					this.removeKeyListener((KeyListener)secGridPanel);
					secGridPanel.removeAll();
					this.contentPane.remove(secGridPanel);
					secGridPanel=new Board((int)((TopBar)topPanel).getGridSize().getSelectedItem());
					addKeyListener((KeyListener)secGridPanel);
					this.add(secGridPanel,BorderLayout.WEST);
				}
				
				this.contentPane.revalidate();
				this.contentPane.repaint();
			}
		}
		//-------------------
		//changing grid size
		if(e.getSource()instanceof JComboBox){
			JComboBox comboBox=(JComboBox)(e.getSource());
			this.removeKeyListener((KeyListener)gridPanel);
			gridPanel.removeAll();
			this.contentPane.remove(gridPanel);
			gridPanel=new Board((int)comboBox.getSelectedItem());
			addKeyListener((KeyListener)gridPanel);
			((TopBar)topPanel).lblScore.setText("score: 0");
			this.add(gridPanel);
			changeSize((int)comboBox.getSelectedItem());
			this.contentPane.revalidate();
			this.contentPane.repaint();
		}
		//--------------------
		//Multi Mode
		if(e.getSource() instanceof JToggleButton){
			JToggleButton multi=(JToggleButton)(e.getSource());
			if(multi.isSelected())
				multiMode=true;
			else 
				multiMode=false;
			changeSize((int)((TopBar)topPanel).getGridSize().getSelectedItem());
		}
		//--------------
		
	}
	
	private void changeSize(int grid){
		int height=0,width=0;
		switch (grid) {
		case 3:
			 width=402;
			 height=464; 
			break;
		case 4:
			width=580;
			 height=650; 
			break;
		case 5:
			width=626;
			 height=686; 
			break;
		case 6:							//to change
			width=626;
			 height=686; 
			break;
		case 7:							//to change
			width=626;
			 height=686; 
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
//			if(grid>4){
//				JOptionPane.showMessageDialog(this, "Multi Mode is not available for grid size greater than 4");
//				((TopBar)topPanel).setToggle();
//			}
//			else{
			width*=2;
			if(grid==3)
				width+=20;
			if(grid==5)
				width+=60;
			secGridPanel=new Board(grid);
			addKeyListener((KeyListener)secGridPanel);
			contentPane.add(secGridPanel,BorderLayout.WEST);
		}
		setBounds(100,100,width,height);
//	}
	}


}
