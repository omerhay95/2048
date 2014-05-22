package game;

import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.css.RGBColor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private final int SIZE;
	public int[][] array;
	private JLabel[][] labels;
	private int free; //the amount of tiles that are free
	private int score;
	private boolean win=false;
	private boolean won=false;
	private JTable table;
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
		skins[2]="Other";
		tilesSkin=skin;
		
		
		free = SIZE * SIZE;
		array=new int[SIZE][SIZE];
		score=0;
		//int[][]arr ={{8,2,8,4},{2,8,16,4},{2,4,8,2},{4,8,2,8}};
		//array =arr;
		//free=0;
		
		
		createTile(array);
		createTile(array);
		this.setBorder(new EmptyBorder(10, 10, 10, 10) );
	toGUI();
	this.setBackground(Color.getHSBColor((float)0.08, (float)0.17, (float)0.55));
	this.setVisible(true);
			
		}
	public int GetScore(){
		return score;
	}
	public void setSkin(int newSkin){
		if(newSkin<skins.length){
			tilesSkin=newSkin;
			toGUI();
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

	public boolean moveUp(int[][]arr) {
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
	
	public boolean moveDown(int[][]arr) {
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

	public boolean moveRight(int[][]arr) {
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

	public boolean moveLeft(int[][]arr) {
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

	private void toGUI() {
		//images=new ImageIcon[SIZE];
		removeAll();
		setLayout(new GridLayout(SIZE, SIZE, 10, 10));
		
		ImageIcon image;
		labels =new JLabel[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				//if(array[i][j]!=0){
				image =new ImageIcon("./src/"+skins[tilesSkin] +"/tile"+array[i][j]+".png");
				labels[i][j]=new JLabel(image);
				//}
			//	else {
			//		labels[i][j]=new JLabel();
			//	}
				//labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black,2));
				this.add(labels[i][j]);
				
				
			}
		}
		this.revalidate();
		repaint();
	
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
		if(moved)
			createTile(array);
		TopBar.lblScore.setText("score: "+score);
		TopBar.lblScore.setText("target: "+Math.pow(2, SIZE+7));
		toGUI();
		if(flag)
			checkEndGame();
	
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
				JOptionPane.showMessageDialog(this, "Game Over");
			}
			score=rscore;
			free=rfree;
		}
	}
	private void win(){
		if(win&&!won){
			won=true;
			JOptionPane.showMessageDialog(this, "Congratulations! You Have Won The Game!");
		}

	}
	private int[][] deepCopyMat(int[][]m){
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
}
