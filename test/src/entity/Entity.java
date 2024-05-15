package entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.sun.tools.javac.Main;

import generation.Generateur;
import objects.gameObject;

public class Entity extends Generateur {
	
	//variable 
    protected int l;
    protected int L;
	protected int speed;
	
	//Inventaire
	private ArrayList<gameObject> inv;
	
	//Gravit�
	protected static final double GRAVITY = 0.06; // Constante de gravit�
	protected double ySpeed = 0; // Vitesse verticale
	
	//fait une animation
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	
	protected int padding;
	
	//pour les collision 
	private Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	protected boolean collisionOn = false;
	
	private final int taille_inv = 9;
	
	public void addInv(gameObject o) {
		if (getInv().size() < taille_inv) {
			this.getInv().add(o);
		} else {
			System.out.println("Impossible : inventaire plein");
		}
	}
	
	
	//creation par default
	

	public ArrayList<gameObject> getInv() {
		return inv;
	}

	public void setInv(ArrayList<gameObject> inv) {
		this.inv = inv;
	}

	public Rectangle getSolidAir() {
		return solidAir;
	}

	public void setSolidAir(Rectangle solidAir) {
		this.solidAir = solidAir;
	}
}