package game;

import javax.swing.ImageIcon;

public class Tile {

	private int value;
	private String image;
	
	public Tile(int value){
		this.value=value;
		image="./src/tile"+value+".png";
	}
}
