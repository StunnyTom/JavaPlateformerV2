package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.gameObject;

public class Entity {
	
	//variable
    public int screenX;
    public int screenY; 
    public int l;
    public int L;
	public int speed;
	
	//Inventaire
	public ArrayList<gameObject> inv;
	
	//Gravit�
	public static final double GRAVITY = 0.03; // Constante de gravit�
    public double ySpeed = 0; // Vitesse verticale
	
	public BufferedImage neutre1, neutre2, up1, up2, right1, right2, left1, left2, down; //les images
	public String direction;
	
	//fait une animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//pour les collision 
	public Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	public boolean collisionOn = false;
	
	public void addInv(gameObject o) {
		if (inv.size() < 9) {
			this.inv.add(o);
		} else {
			System.out.println("Impossible : inventaire plein");
		}
	}

}