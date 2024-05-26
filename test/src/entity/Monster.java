package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import test.GamePanel;

public abstract class Monster extends Entity {
   
    BufferedImage spriteImage;
    private boolean isCollisionWithPlayer = false;
    protected boolean isVisible = true; // Default visibility
   
    protected int stepsCount = 0; // Compteur de pas commun
    protected int maxSteps;   // Maximum de pas avant de changer de direction
    protected boolean movingRight = true; // Direction initiale

    public Monster(GamePanel gp, String imagePath, int padding, int maxSteps) {
        super(gp);
        this.padding = padding;
        this.maxSteps = maxSteps;
     
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

    protected void updateSolidArea() {
        if (solidAir == null) {
            solidAir = new Rectangle();
        }
        solidAir.setLocation(screenX - padding, screenY - padding);
        solidAir.setSize(gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);
    }


    @Override
    public void draw(Graphics2D g2) {
        if (spriteImage != null && isVisible) {
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
}


    
      
	
	