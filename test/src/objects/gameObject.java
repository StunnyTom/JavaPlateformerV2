package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import generation.Generateur;
import test.GamePanel;

import java.io.IOException;

//Classe qui gère les interactions et attributs communs aux objets
public class gameObject extends Generateur {
    protected BufferedImage image;
    protected boolean collision = false;
    protected String nom;

    // Ajouter un constructeur qui accepte un boolean pour la collision
    public gameObject(GamePanel gp, boolean collision) {
    	super(gp);
        this.collision = collision;
    }

    // Éventuellement, un constructeur plus complet pour initialiser tous les attributs
    public gameObject(GamePanel gp, String nom, String id, String imagePath, boolean collision) {
    	super(gp);
        this.nom = nom;
        this.id = id;
        this.collision = collision;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isNullObject() {
        return id == null || id.equals("0");
    }

	
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }
    @Override
    public String toString() {
        return "Nom: " + nom;
    }

}
