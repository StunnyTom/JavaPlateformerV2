package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

import test.GamePanel;

public class PNJ extends Entity {
    GamePanel gp;
    public boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    public Timer dialogueTimer; // attribut pour le temps de la boite

    public PNJ(GamePanel gp, String imageResource, int screenX, int screenY,  int padding) {
        super();
        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = "neutre"; // Définir une valeur par défaut pour la direction
       
        super.solidAir = new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);   // Définir la zone de collision
        getImage(imageResource);
    }


    public void getImage(String imageResource) {
        try {
            super.neutre1 = ImageIO.read(getClass().getResourceAsStream(imageResource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "neutre":
                if (spriteNum == 1) {
                    image = neutre1;
                } else if (spriteNum == 2) {
                    image = neutre2;
                }
                break;
        }

        // Dessine l'image sur le rectangle 
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); 

}

}
