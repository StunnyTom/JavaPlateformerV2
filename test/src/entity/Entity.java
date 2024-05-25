package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import generation.Generateur;
import objects.gameObject;
import test.GamePanel;

public class Entity extends Generateur {
	
	public Entity(GamePanel gp) {
		super(gp);
	}

	//variable 
    protected int l;
    protected int L;
	protected int speed;
	
	//Inventaire
	protected ArrayList<gameObject> inv;
	
	//Gravit�
	protected static final double GRAVITY = 0.06; // Constante de gravit�
	protected double ySpeed = 0; // Vitesse verticale
	
	//fait une animation
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	
	protected int padding;
	
	//pour les collision 
	protected Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	protected boolean collisionOn = false;
	
	private final int taille_inv = 10;
	
	public void addInv(gameObject collOb) {
		if (getInv() == null) {
			inv = new ArrayList<>();
		} if (getInv().size() < taille_inv) {
			if (!inv.contains(collOb)) {
				this.getInv().add(collOb);
	            System.out.println("Objet ajouté à l'inventaire.");
	        }else {
	          //  System.out.println("Objet déjà dans l'inventaire.");
	        }
		}else {
	        System.out.println("Impossible : inventaire plein");
	    }
}

	 public void removeInv(gameObject obj) {
	        if (obj != null) {
	        	getInv().remove(obj);
	        }
	    }


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