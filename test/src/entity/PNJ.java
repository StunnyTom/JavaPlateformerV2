package entity;

import java.awt.Rectangle;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

import test.GamePanel;

public class PNJ extends Entity {
    GamePanel gp;
    public boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    public Timer dialogueTimer; // attribut pour le temps de la boite

    protected String dialogue;
    
    public PNJ(GamePanel gp, String imageResource, int screenX, int screenY,  int padding) {
        super();
        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = "neutre"; // Définir une valeur par défaut pour la direction
       
        super.solidAir = new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);   // Définir la zone de collision
        getImage(imageResource);
    }

    public PNJ(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void getImage(String imageResource) {
        try {
            super.neutre1 = ImageIO.read(getClass().getResourceAsStream(imageResource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
}

}
