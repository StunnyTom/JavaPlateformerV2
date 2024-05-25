package tiles;

import java.awt.image.BufferedImage;

public class Tile {
	
	public Tile() {
		// TODO Auto-generated constructor stub
	}
	public BufferedImage image;
	public boolean collision = false; //colision
		
	   
	    public Tile(boolean collision) {
	        this.collision = collision;
	    }

	}


