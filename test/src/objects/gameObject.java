package objects;

import java.awt.image.BufferedImage;

import generation.Generateur;

public class gameObject extends Generateur {

	protected String nom;
    private boolean collision = false; // collision
    protected String id;
    protected BufferedImage image;

    public gameObject(boolean collision) {
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setID(String id) {
        this.setId(id);
    }

    public String getNom() {
        return this.nom;
    }
    
    public String setNom(String nom) {
        return this.nom = nom;
   }
   
    public boolean isCollision() {
        return collision;
    }
    
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean nullObj() {
    	return id == null || this.id.equals("0");
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	//maybe pas utile
	 public void onRemove() {
	    	
	 }
}
