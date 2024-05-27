package entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import generation.Generateur;
import objects.gameObject;
import test.GamePanel;

public class Entity extends Generateur {
	//Entité pour gérer le joueur, les pnj et les monstres
	
	//Constructeur de base
	public Entity(GamePanel gp) {
		super(gp);
	}

	//variable 
    protected int l;
    protected int L;
	protected int speed;
	
	//Inventaire
	protected ArrayList<gameObject> inv;
	
	//Gravité
	protected static final double GRAVITY = 0.06; // Constante de gravit�
	protected double ySpeed = 0; // Vitesse verticale
	
	//fait une animation 
	//protected int spriteCounter = 0;
	//protected int spriteNum = 1;
	
	protected int padding;
	
	//pour les collision 
	protected Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	protected boolean collisionOn = false;
	
	private final int taille_inv = 5;
	
	
	//Ajout d'objet à l'inventaire
	public void addInv(gameObject collOb) {
		if (getInv() == null) {
			inv = new ArrayList<>();
		} if (getInv().size() < taille_inv) { //Gestion de la taille
			if (!inv.contains(collOb)) {
				this.getInv().add(collOb);
	            //System.out.println("Objet ajouté à l'inventaire.");
	        }else {
	          //  System.out.println("Objet déjà dans l'inventaire.");
	        }
		}else {
	        System.out.println("Impossible : inventaire plein");
	    }
	}

	//Enlever un objet de l'inventaire de l'entité
	 public void removeInv(gameObject obj) {
	        if (obj != null) {
	        	getInv().remove(obj);
	        }
	    }
	 
	 //Méthode pour initialiser la position au chargement d'une map
	 protected void initializePosition(char identifier) {
	        File nameMap = new File(gp.currentMap);
	        try {
	            Point[] spawnPoints = findSpawnPoints(identifier, nameMap.getName());
	            if (spawnPoints.length > 0) {
	                Point x = spawnPoints[0];
	                screenX = (int) (gp.tileSize * x.getY());
	                screenY = (int) (gp.tileSize * x.getX());
	                setSolidAir(new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding));
	            } else {
	                System.err.println("No spawn point found for identifier: " + identifier);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	//Getters et Setters de plusieurs attributs

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