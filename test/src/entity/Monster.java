package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import test.GamePanel;

//Classe abstraite pour gérer les méthodes communes aux monstres
public abstract class Monster extends Entity {
   
    BufferedImage spriteImage;
    private boolean isCollisionWithPlayer = false;
    protected boolean isVisible = true; // Default visibility
    private boolean isCollidable = true; // Default collidability

    protected int stepsCount = 0; // Compteur de pas commun
    protected int maxSteps;   // Maximum de pas avant de changer de direction
    protected boolean movingRight = true; // Direction initiale
    
    protected int health;  // Points de vie du monstre

    public Monster(GamePanel gp, String imagePath, int padding, int maxSteps, int health) {
        super(gp);
        this.padding = padding;
        this.maxSteps = maxSteps;
        this.health = health;  // Initialiser les points de vie
     
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
            	 throw new RuntimeException("Pas accès au lieu du monstre: " + imagePath);
            }
            spriteImage = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load image: " + imagePath, e);
        }
    }
    
    //Méthode abstraite qui gère l'interaction du monstre avec le joueur
    public abstract void checkPlayerInteraction();

    protected void updateMovement(int speed) {
        if (movingRight) {
            if (stepsCount < maxSteps) {
                screenX += speed;
                stepsCount++;
            } else {
                movingRight = false;
                stepsCount = 0;
            }
        } else {
            if (stepsCount < maxSteps) {
                screenX -= speed;
                stepsCount++;
            } else {
                movingRight = true;
                stepsCount = 0;
            }
        }
        updateSolidArea();
    }
     
    public void inflictDamage(int damage) {
        health -= damage;  // Réduit la santé de 1
        if (health <= 0) {
            die();  // Gère la mort du monstre si la santé atteint 0 ou passe en dessous
        }
    }

    public int getHealth() {
        return health;  // Return the current health of the monster
    }


    public void die() {
        setVisible(false);  // Rendre le monstre invisible ou le supprimer de la scène
    }

    protected void updateSolidArea() {
        if (solidAir == null) {
            solidAir = new Rectangle();
        }
        solidAir.setLocation(screenX - padding, screenY - padding);
        solidAir.setSize(gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);
    }


    @Override
    public void draw(Graphics2D g2) {
        if (this.isVisible) {
            g2.drawImage(spriteImage, getScreenX(), getScreenY(), gp.tileSize, gp.tileSize, null);
        }
    }

    
    public boolean isCollisionWithPlayer() {
        return isCollisionWithPlayer;
    }

    public void setCollisionWithPlayer(boolean isCollisionWithPlayer) {
        this.isCollisionWithPlayer = isCollisionWithPlayer;
    }

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean isVisible) {
	    this.isVisible = isVisible;
	}

	public void setCollidable(boolean isCollidable) {
	    this.isCollidable = isCollidable;
	}

	public boolean getIsVisible() {
	    return isVisible;
	}

	public boolean getIsCollidable() {
	    return isCollidable;
	}



}