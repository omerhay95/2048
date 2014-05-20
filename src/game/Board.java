package game;

import java.util.Random;

import javax.swing.*;

public class Board extends JPanel {
	private final int SIZE;
	public int[][] array;
	private ImageIcon[][] images;
	private int free; //the amount of tiles that are free
	private int score;
	/**
	 * Create the panel.
	 */
	public Board(int size) {
		SIZE =size;
		free = SIZE * SIZE;
		array=new int[SIZE][SIZE];
		score=0;
		//int[][]arr ={{0,2,2,4},{2,2,0,4},{2,4,0,2},{0,4,2,2}};
		//array =arr;
		//free=4;
		createTile();
		createTile();
	
			
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
	
	private void createTile() {
		int index = randomTile();
		int i = getRow(index);
		int j = getCol(index);
		
		array[i][j] = randomValue();
		//update images here
		free--;
	}

	public boolean moveUp() {
		boolean[] isMerged;
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			isMerged =new boolean[SIZE];
			for (int i = 1; i < SIZE; i++) {
				if(array[i][j]!=0){
				//This moves the tiles up
					int rIndex=i-1;
					while (rIndex >=0 && array[rIndex][j]==0){ // as long we are within the limit and the upper tile is empty
						array[rIndex][j]=array[rIndex + 1][j];
						array[rIndex + 1][j]=0;
						rIndex--;
						i--;
						moved = true;
					} 
				//--------------------------------------
					if(i==-1 || i==0)
						i++;
				if(array[i-1][j]!=0) //if the tile above is not empty
				{
					if(array[i][j]==array[i-1][j]&&!(isMerged[i-1]))//if they are equal and can be merged
					{
						array[i-1][j]*=2;
						array[i][j]=0;
						free++;
						isMerged[i-1]=true;
						moved =true;
						score+=array[i-1][j];
					}
				}
			
				}
			}
		}
		if(moved)
			createTile();
		return moved;
	}
	
	public boolean moveDown() {
		boolean[] isMerged;
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			isMerged =new boolean[SIZE];
			for (int i = SIZE - 2; i >= 0; i--) {
				if(array[i][j]!=0){
				//This moves the tiles down
					int rIndex=i+1;
					while (rIndex <= SIZE - 1 && array[rIndex][j]==0){ // as long we are within the limit and the lower tile is empty
						array[rIndex][j]=array[rIndex - 1][j];
						array[rIndex - 1][j]=0;
						rIndex++;
						i++;
						moved = true;
					} 
				//--------------------------------------
					if(i==SIZE || i==SIZE-1)
						i--;
				if(array[i+1][j]!=0) //if the tile below is not empty
				{
					if(array[i][j]==array[i+1][j]&&!(isMerged[i+1]))//if they are equal and can be merged
					{
						array[i+1][j]*=2;
						array[i][j]=0;
						free++;
						isMerged[i+1]=true;
						moved =true;
						score+=array[i+1][j];
					}
				}
			
				}
			}
		}
		if(moved)
			createTile();
		return moved;
	}

	public boolean moveRight() {
		boolean[] isMerged;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			isMerged =new boolean[SIZE];
			for (int j = SIZE - 2; j >= 0; j--) {
				if(array[i][j]!=0){
				//This moves the tiles right
					int cIndex=j+1;
					while (cIndex <= SIZE - 1 && array[i][cIndex]==0){ // as long we are within the limit and the right tile is empty
						array[i][cIndex]=array[i][cIndex-1];
						array[i][cIndex-1]=0;
						cIndex++;
						j++;
						moved = true;
					} 
				//--------------------------------------
					if(j==SIZE || j==SIZE-1)
						j--;
				if(array[i][j+1]!=0) //if the tile below is not empty
				{
					if(array[i][j]==array[i][j+1]&&!(isMerged[j+1]))//if they are equal and can be merged
					{
						array[i][j+1]*=2;
						array[i][j]=0;
						free++;
						isMerged[j+1]=true;
						moved =true;
						score+=array[i][j+1];
					}
				}
			
				}
			}
		}
		if(moved)
			createTile();
		return moved;
	}

	public boolean moveLeft() {
		boolean[] isMerged;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			isMerged =new boolean[SIZE];
			for (int j = 1; j < SIZE; j++) {
				if(array[i][j]!=0){
				//This moves the tiles right
					int cIndex=j-1;
					while (cIndex >= 0 && array[i][cIndex]==0){ // as long we are within the limit and the left tile is empty
						array[i][cIndex]=array[i][cIndex+1];
						array[i][cIndex+1]=0;
						cIndex--;
						j--;
						moved = true;
					} 
				//--------------------------------------
					if(j==-1 || j==0)
						j++;
				if(array[i][j-1]!=0) //if the tile below is not empty
				{
					if(array[i][j]==array[i][j-1]&&!(isMerged[j-1]))//if they are equal and can be merged
					{
						array[i][j-1]*=2;
						array[i][j]=0;
						free++;
						isMerged[j-1]=true;
						moved =true;
						score+=array[i][j-1];
					}
				}
			
				}
			}
		}
		if(moved)
			createTile();
		return moved;
	}
}
