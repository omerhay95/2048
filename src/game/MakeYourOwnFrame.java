package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MakeYourOwnFrame extends JFrame implements MouseListener{

	private JPanel contentPane;
	private JPanel topPane;
	private JFileChooser chooser;
	private JTextField txtValue;
	private JButton btnChooseFile;
	private JButton btnSave;
	private int toSaveVal=chooser.CANCEL_OPTION;
	private  final String ICONS_LOCATION	=System.getProperty("user.home") +"/AppData/Roaming/Make Your Own";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeYourOwnFrame frame = new MakeYourOwnFrame();
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
	public MakeYourOwnFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		chooser=new JFileChooser();
		//contentPane.add(chooser,BorderLayout.CENTER);
		topPane = new JPanel();
		JLabel lblEnterTheValue = new JLabel("Enter the value of the tile you want to change:");
		lblEnterTheValue.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.setLayout(new FlowLayout());
		topPane.add(lblEnterTheValue);
		contentPane.add(topPane,BorderLayout.CENTER);
		
		txtValue = new JTextField();
		topPane.add(txtValue);
		txtValue.setColumns(10);
		
		btnChooseFile = new JButton("Choose File");
		btnChooseFile.addMouseListener(this);
		topPane.add(btnChooseFile);
		
		btnSave = new JButton("Save");
		btnSave.addMouseListener(this);
		topPane.add(btnSave);
		
		this.setVisible(true);
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JButton){
			JButton button=((JButton)e.getSource());
			if(button.getText().equals("Choose File")){
				toSaveVal=chooser.showOpenDialog(this);
				
				
				
			}
			else if(button.getText().equals("Save")){
				if(toSaveVal==chooser.APPROVE_OPTION){
					File toCopy=chooser.getSelectedFile();
					File file=new File(ICONS_LOCATION+"/tileTemp.Png");
					try {
						copyFileUsingStream(toCopy, file);
					} catch (Exception e2) {
						System.err.println("error copying the file");
					}
					
					
					int val=-1;
					try{
						val=Integer.parseInt(txtValue.getText());
					}
					catch (Exception ex){
						JOptionPane.showMessageDialog(this, "the tile value must be a number", "error", JOptionPane.ERROR_MESSAGE);
					}
					if(val>=0){
					
						 try{//save the image
					    		File f = new File(ICONS_LOCATION);
					            f.mkdirs();
					            f.createNewFile();
					            f = file;
					            f.renameTo(new File(ICONS_LOCATION+"/tile"+val+".Png"));
					            f.createNewFile();
					            JOptionPane.showMessageDialog(this, "The Tile Was Successfully Saved", "Success", JOptionPane.INFORMATION_MESSAGE);
					            txtValue.setText(ICONS_LOCATION+"/tile"+val+".Png");
						 }
					    	catch(Exception exc){
					    		
					    	}
					}
				}
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
