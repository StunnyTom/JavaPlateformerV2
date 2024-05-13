package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import objects.gameObject;
import test.GamePanel;

public abstract class PNJ extends Entity {
    GamePanel gp;
    String direction;
    BufferedImage spriteImage;
    int screenX, screenY;
    int padding; // Ajout du padding comme attribut de la classe
    protected gameObject itemToGive; // Objet à donner utilisé dans les sous-classes
    public boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur

    public PNJ(GamePanel gp, String imagePath, int padding) {
        super();
        this.gp = gp;
        this.direction = "neutre"; // Valeur par défaut de la direction
        this.padding = padding; // Initialisation du padding

        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                throw new RuntimeException("Pas accès au lieu du pnj: " + imagePath);
            }
            this.spriteImage = ImageIO.read(is);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //ajouter un objet dans l'inventaire
    public void addItemToInventory(Player player) {
        if (itemToGive != null && itemToGive.image != null) {
            if (!player.inv.contains(itemToGive)) {
                player.inv.add(itemToGive);
                System.out.println("Objet ajouté à l'inventaire : " + itemToGive.getID());
                itemToGive = null;  // Assurez-vous que l'objet ne peut être donné qu'une fois
            } else {
                System.out.println("L'objet existe déjà dans l'inventaire.");
            }
        } else {
            System.out.println("Aucun objet à donner ou image manquante.");
        }
    }



    protected void initializePosition(char identifier) {
        File nameMap = new File(gp.currentMap);
        try {
            Point x = Entity.findSpawnPoints(identifier, nameMap.getName())[0];
            screenX = (int) (gp.tileSize * x.getY());
            screenY = (int) (gp.tileSize * x.getX());
            this.solidAir = new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (spriteImage != null) {
            g2.drawImage(spriteImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
       
    }
}
