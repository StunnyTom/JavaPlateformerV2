package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import test.GamePanel;

public class ParentsObjects {

    public BufferedImage image;
    public String name;
    public boolean collision = false; 
    public int objectX, objectY;
    
    //collision objet
    public Rectangle solidAir = new Rectangle(1,1,1,1); // chaque objet aura un rectangle invisible valeur par default
    // tous les objets auront la meme taille
    public int solidAirDefaultX = 0;
    public int solidAirDefaultY = 0;

    // On dessine les objets à l'écran    
    public void draw(Graphics2D g2, GamePanel gp) {
        // Calcul de la position de l'objet sur l'écran en soustrayant la position du joueur
        int screenX = objectX - gp.screenWidth / 2; // il ne faut pas prendre en compte la position du joueur 
        int screenY = objectY - gp.screenHeight / 2;

        // Dessine l'image de l'objet seulement si elle se trouve à l'intérieur de la fenêtre visible.
        if(screenX + gp.tileSize > 0 && screenX - gp.tileSize < gp.screenWidth &&
           screenY + gp.tileSize > 0 && screenY - gp.tileSize < gp.screenHeight) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

}
