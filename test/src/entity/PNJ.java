package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import objects.gameObject;
import test.GamePanel;

public class PNJ extends Entity {
    GamePanel gp;
    public boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    public Timer dialogueTimer; // Attribut pour le temps de la bo�te de dialogue
    protected gameObject itemToGive; // Objet � donner utilis� dans les sous-classes

    public PNJ(GamePanel gp, String imageResource, int screenX, int screenY, int padding) {
        super();
        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = "neutre"; // D�finir une valeur par d�faut pour la direction
     
        super.solidAir = new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding); // D�finir la zone de collision
        getImage(imageResource);
    }

    //Cette m�thode charge une image � partir du chemin de ressource sp�cifi� 
    public void getImage(String imageResource) {
        try {
            super.neutre1 = ImageIO.read(getClass().getResourceAsStream(imageResource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ajouter un objet dans l'inventaire
    public void addItemToInventory(Player player) {
        if (itemToGive != null && itemToGive.image != null) {
            boolean alreadyInInventory = false;
            for (gameObject invItem : player.inv) {
                if (invItem.getID().equals(itemToGive.getID())) {
                    alreadyInInventory = true;
                    break;
                }
            }
            // Si l'objet est deja present, message 
            if (!alreadyInInventory) {
                player.inv.add(itemToGive);
                System.out.println("Objet ajout� � l'inventaire : " + itemToGive.getID());
            } else {
                System.out.println("L'objet existe d�j� dans l'inventaire.");
            }
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}