package game;

import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.css.RGBColor;

import com.sun.xml.internal.ws.api.ResourceLoader;

import HighScores.HighScoreManager;
import sun.audio.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class Board extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private final int SIZE;
	public int[][] array;
	private int [][] lastMove;
	private JLabel[][] labels;
	private JLabel[][] lastTiles;
	private int free; //the amount of tiles that are free
	private int lastFree;
	private int score;
	private int lastScore;
	private boolean win=false;
	private boolean won=false;
	private static boolean gameOver=false;
	private static boolean isMulti=false;
	private boolean flush=false;
	private int newTiles=1;
	int tilesSkin;
	String[]skins;
	/**
	 * Create the panel.
	 */
	public Board(int size,int skin) {
		super();
		SIZE =size;
		setLayout(new GridLayout(SIZE, SIZE, 10, 10));
		
		//addKeyListener(this);
		skins=new String[3];
		skins[0]="Classic";
		skins[1]="Chad Gadya";
		skins[2]="Make Your Own";
		tilesSkin=skin;
		
		
		free = SIZE * SIZE;
		array=new int[SIZE][SIZE];
		score=0;
		//int[][]arr ={{8,2,8,4},{2,8,16,4},{2,4,8,2},{4,8,2,8}};
		//array =arr;
		//free=0;
		
		
		createTile(array);
		createTile(array);
		updateUndo();
		this.setBorder(new EmptyBorder(10, 10, 10, 10) );
	toGUI();
	this.setBackground(Color.getHSBColor((float)0.08, (float)0.17, (float)0.55));
	this.setVisible(true);
			
		}
	public void setGameOver(boolean isOver){
		this.gameOver=isOver;
	}
	public void setMulti(boolean isMulti){
		this.isMulti=isMulti;
	}
	public boolean getMult(){
		return isMulti;
	}
	public int getboardSize(){
		return SIZE;
	}
	public int GetScore(){
		return score;
	}
	public void setFlush(boolean newFlush){
		this.flush=newFlush;
	}
	public void setSkin(int newSkin){
		if(newSkin<skins.length){
			tilesSkin=newSkin;
			toGUI();
		}
	}
	public void setNumOfTilesCreated(int num){
		newTiles=num;
	}
	public int getNumOfTilesCreated(){
		return newTiles;
	}
	public void Undo(){
		if(!gameOver){
		array=deepCopyMat(lastMove);
		if(lastTiles!=null)
			labels=deepCopyLabels(lastTiles);
		if(labels!=null)
			toGUI();
		free=lastFree;
		score=lastScore;
		TopBar.lblScore.setText("score: "+score);
		if(gameOver)
			gameOver=false;
	}
	}
	
	
	private int randomTile(){
		int[] freeTiles = freeTiles();
		Random rnd = new Random();
		int index = rnd.nextInt(free);
		return freeTiles[index];

	}
	private int[] freeTiles() {
		int[] result = new int[free];
		int index = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if(array[i][j] ==0)
				{
					result[index] = SIZE*i + j;
					index++;
				}
			}
		}
		return result;
	}
	private int getRow(int index) {
		return index / SIZE;
	}
	private int getCol(int index) {
		return index % SIZE;
	}
	private int randomValue() {
		Random rnd =new Random();
		if(rnd.nextInt(100)+1 <= 25)
			return 4;
		else 
			return 2;
		
	}
	
	private void createTile(int[][]m) {
		int index = randomTile();
		int i = getRow(index);
		int j = getCol(index);
		
		m[i][j] = randomValue();
		//update images here
		free--;
	}

	private JLabel[][] deepCopyLabels(JLabel[][]m){
		if(m==null)
			return null;
		JLabel[][]res=new JLabel[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				res[i][j]=m[i][j];
			}
		}
		return res;
	}
	
	private boolean moveUp(int[][]arr) {
		boolean[] isMerged;
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			isMerged =new boolean[SIZE];
			for (int i = 1; i < SIZE; i++) {
				if(arr[i][j]!=0){
				//This moves the tiles up
					int rIndex=i-1;
					while (rIndex >=0 && arr[rIndex][j]==0){ // as long we are within the limit and the upper tile is empty
						arr[rIndex][j]=arr[rIndex + 1][j];
						arr[rIndex + 1][j]=0;
						rIndex--;
						i--;
						moved = true;
						
						
					} 
				//--------------------------------------
					if(i==-1 || i==0)
						i++;
				if(arr[i-1][j]!=0) //if the tile above is not empty
				{
					if(arr[i][j]==arr[i-1][j]&&!(isMerged[i-1]))//if they are equal and can be merged
					{
						arr[i-1][j]*=2;
						if(arr[i-1][j]==(int)(Math.pow(2, SIZE+7)))
							win=true;
						arr[i][j]=0;
						free++;
						isMerged[i-1]=true;
						moved =true;
						score+=arr[i-1][j];
					}
				}
			
				}
			}
			
			
		}
		//if(moved)
		//	createTile(arr);
		return moved;
	}
	
	private boolean moveDown(int[][]arr) {
		boolean[] isMerged;
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			isMerged =new boolean[SIZE];
			for (int i = SIZE - 2; i >= 0; i--) {
				if(arr[i][j]!=0){
				//This moves the tiles down
					int rIndex=i+1;
					while (rIndex <= SIZE - 1 && arr[rIndex][j]==0){ // as long we are within the limit and the lower tile is empty
						arr[rIndex][j]=arr[rIndex - 1][j];
						arr[rIndex - 1][j]=0;
						rIndex++;
						i++;
						moved = true;
					} 
				//--------------------------------------
					if(i==SIZE || i==SIZE-1)
						i--;
				if(arr[i+1][j]!=0) //if the tile below is not empty
				{
					if(arr[i][j]==arr[i+1][j]&&!(isMerged[i+1]))//if they are equal and can be merged
					{
						arr[i+1][j]*=2;
						if(arr[i+1][j]==(int)(Math.pow(2, SIZE+7)))
							win=true;
						arr[i][j]=0;
						free++;
						isMerged[i+1]=true;
						moved =true;
						score+=arr[i+1][j];
					}
				}
			
				}
			}
		}
	//	if(moved)
	//		createTile(arr);
		return moved;
	}

	private boolean moveRight(int[][]arr) {
		boolean[] isMerged;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			isMerged =new boolean[SIZE];
			for (int j = SIZE - 2; j >= 0; j--) {
				if(arr[i][j]!=0){
				//This moves the tiles right
					int cIndex=j+1;
					while (cIndex <= SIZE - 1 && arr[i][cIndex]==0){ // as long we are within the limit and the right tile is empty
						arr[i][cIndex]=arr[i][cIndex-1];
						arr[i][cIndex-1]=0;
						cIndex++;
						j++;
						moved = true;
					} 
				//--------------------------------------
					if(j==SIZE || j==SIZE-1)
						j--;
				if(arr[i][j+1]!=0) //if the tile below is not empty
				{
					if(arr[i][j]==arr[i][j+1]&&!(isMerged[j+1]))//if they are equal and can be merged
					{
						arr[i][j+1]*=2;
						if(arr[i][j+1]==(int)(Math.pow(2, SIZE+7)))
							win=true;
						arr[i][j]=0;
						free++;
						isMerged[j+1]=true;
						moved =true;
						score+=arr[i][j+1];
					}
				}
			
				}
			}
		}
	//	if(moved)
	//		createTile(arr);
		return moved;
	}

	private boolean moveLeft(int[][]arr) {
		boolean[] isMerged;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			isMerged =new boolean[SIZE];
			for (int j = 1; j < SIZE; j++) {
				if(arr[i][j]!=0){
				//This moves the tiles right
					int cIndex=j-1;
					while (cIndex >= 0 && arr[i][cIndex]==0){ // as long we are within the limit and the left tile is empty
						arr[i][cIndex]=arr[i][cIndex+1];
						arr[i][cIndex+1]=0;
						cIndex--;
						j--;
						moved = true;
					} 
				//--------------------------------------
					if(j==-1 || j==0)
						j++;
				if(arr[i][j-1]!=0) //if the tile below is not empty
				{
					if(arr[i][j]==arr[i][j-1]&&!(isMerged[j-1]))//if they are equal and can be merged
					{
						arr[i][j-1]*=2;
						if(arr[i][j-1]==(int)(Math.pow(2, SIZE+7)))
							win=true;
						arr[i][j]=0;
						free++;
						isMerged[j-1]=true;
						moved =true;
						score+=arr[i][j-1];
					}
				}
			
				}
			}
		}
	//	if(moved)
	//		createTile(arr);
		return moved;
	}
	public void updateGUI(){
		toGUI();
	}
	
	private void toGUI() {
		
		//reloadClass();
		
		//images=new ImageIcon[SIZE];
		removeAll();
		setLayout(new GridLayout(SIZE, SIZE, 10, 10));
		
		
		
		ImageIcon image;
		labels =new JLabel[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if(tilesSkin==2){
						File file=new File(System.getProperty("user.home") +"/AppData/Roaming/Make Your Own"+"/tile"+array[i][j]+".png");
						if(file.exists()){
						image=new ImageIcon(System.getProperty("user.home") +"/AppData/Roaming/Make Your Own"+"/tile"+array[i][j]+".png");
						}
						else{
							image =new ImageIcon("./src/Classic/tile"+array[i][j]+".png");
						}
//						image.getImage().flush();
//					image=new ImageIcon(System.getProperty("user.home") +"/AppData/Roaming/Make Your Own"+"/tile"+array[i][j]+".png");
					if(flush){
						image.getImage().flush();
					}
				}else{
					
					image =new ImageIcon("./src/"+skins[tilesSkin] +"/tile"+array[i][j]+".png");
				labels[i][j]=new JLabel(image);
				this.add(labels[i][j]);
				
				
			}
		}
		if(flush){
			flush=false;
		}
		this.revalidate();
		repaint();
	
	}
	}

public void reload() {
		
		//reloadClass();
		
		//images=new ImageIcon[SIZE];
		removeAll();
		setLayout(new GridLayout(SIZE, SIZE, 10, 10));
		
		
		
		ImageIcon image;
		labels =new JLabel[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if(tilesSkin==2){
					File file=new File(System.getProperty("user.home") +"/AppData/Roaming/Make Your Own"+"/tile"+array[i][j]+".png");
					if(file.exists()){
					image=new ImageIcon(System.getProperty("user.home") +"/AppData/Roaming/Make Your Own"+"/tile"+array[i][j]+".png");
					}
					else{
						image =new ImageIcon("./src/Classic/tile"+array[i][j]+".png");
					}
					image.getImage().flush();
				}else
					image =new ImageIcon("./src/"+skins[tilesSkin] +"/tile"+array[i][j]+".png");
				labels[i][j]=new JLabel(image);
				this.add(labels[i][j]);
				
				
			}
		}
		this.revalidate();
		repaint();
	
	}
	
	
	private void reloadClass(){
		Class<?> myClass=Board.class;
	    URL[] urls={ myClass.getProtectionDomain().getCodeSource().getLocation() };
	    ClassLoader delegateParent = myClass.getClassLoader().getParent();
	    try(URLClassLoader cl=new URLClassLoader(urls, delegateParent)) {
	      Class<?> reloaded=cl.loadClass(myClass.getName());
	      System.out.printf("reloaded my class: Class@%x%n", reloaded.hashCode());
	      System.out.println("Different classes: "+(myClass!=reloaded));
	    }
	    catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		boolean flag=false;
		boolean moved=false;
		int[][]last=deepCopyMat(array);
		JLabel[][]lastLbl=deepCopyLabels(labels);
		int lastFree=free;
		int lastScore=score;
		if(!gameOver){
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			moved=moveUp(array);
			flag=true;
			break;
		case KeyEvent.VK_DOWN:
			moved=moveDown(array);
			flag=true;
			break;
		case KeyEvent.VK_LEFT:
			moved=moveLeft(array);
		flag=true;
		break;
		case KeyEvent.VK_RIGHT:
			moved=moveRight(array);
			flag=true;
			break;
		default:
			break;
		}
		if(moved){
			playSound("move.wav");
			for (int i = 0; i < newTiles; i++) 
				if(free>0)
				createTile(array);
			lastMove=last;
			if(lastLbl!=null)
			lastTiles=lastLbl;
			this.lastFree=lastFree;
			this.lastScore=lastScore;
		}
		TopBar.lblScore.setText("score: "+score);
		toGUI();
		if(flag)
			checkEndGame();
		}
	}
	private void updateUndo(){
		lastMove=deepCopyMat(array);
		if(labels!=null)
			lastTiles=deepCopyLabels(labels);
		lastFree=free;
		lastScore=score;
	}
	private void checkEndGame(){
		gameOver();
		win();
	}
	private void gameOver(){
		if(free==0){
			int rscore=score;
			int rfree=free;
			int[][]checkMoves=deepCopyMat(array);
			if(!(moveUp(checkMoves)||moveDown(checkMoves)||moveLeft(checkMoves)||moveRight(checkMoves))){
				gameOver=true;
				String table="";
			String name=JOptionPane.showInputDialog(this, "Good Game, but it's over now. Please enter your name");
			if(name!=null){
				if(name.length()==0)
					name="Player";
			HighScoreManager hsm;
			if(isMulti)
				table+="M";
			 //hsm=new HighScoreManager("M"+SIZE);
			//else {
			//	hsm=new HighScoreManager(""+SIZE);
			//}
				table+=SIZE+"";
				hsm=new HighScoreManager(table);
			hsm.addScore(name, rscore);
			HighscoresFrame hf=new HighscoresFrame(table);//show highscores
			}
			}
			score=rscore;
			free=rfree;
		}
	}
	private void win(){
		if(win&&!won){
			won=true;
			playSound("victory.wav");
			JOptionPane.showMessageDialog(this, "Congratulations! You Have Won The Game!");
		}

	}
	private int[][] deepCopyMat(int[][]m){
		if(m==null)
			return null;
		int[][]res=new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				res[i][j]=m[i][j];
			}
		}
//		for (int i = 0; i < SIZE; i++) {
//			res[i]=m[i].clone();
//		}
		return res;
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
			System.out.println("prob");
		}
	}
	
	private InputStream load(String path){
		InputStream input=ResourceLoader.class.getResourceAsStream(path);
		
		if(input==null){
			input=ResourceLoader.class.getResourceAsStream("/"+path);
		}
		
		return input;
	}
}
