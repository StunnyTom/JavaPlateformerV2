package objects;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class gameObject {
    protected String nom;
    protected String id;
    protected BufferedImage image;
    protected boolean collision;

    // Ajouter un constructeur qui accepte un boolean pour la collision
    public gameObject(boolean collision) {
        this.collision = collision;
    }

    // Éventuellement, un constructeur plus complet pour initialiser tous les attributs
    public gameObject(String nom, String id, String imagePath, boolean collision) {
        this.nom = nom;
        this.id = id;
        this.collision = collision;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Assurez-vous que les méthodes suivantes sont correctement définies :
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
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

    // Renommée pour correspondre à votre utilisation :
    public boolean isNullObject() {
        return id == null || id.equals("0");
    }

	


}
