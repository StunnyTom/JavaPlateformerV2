package entity;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import objects.gameObject;
import test.GamePanel;

public abstract class PNJ extends Entity {
    String direction;
    int screenX, screenY;
    int padding; // Ajout du padding comme attribut de la classe
    protected gameObject itemToGive; // Objet � donner utilis� dans les sous-classes
    protected boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    private boolean isVisible = true; // Contr�le la visibilit� du PNJ

    public PNJ(GamePanel gp, String imagePath, int padding) {
        super(gp);
        this.direction = "neutre"; // Valeur par d�faut de la direction
        this.padding = padding; // Initialisation du padding

        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                throw new RuntimeException("Pas acc�s au lieu du pnj: " + imagePath);
            }
            this.setImage(ImageIO.read(is));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public abstract void triggerDialog();
    
    //ajouter un objet dans l'inventaire
    public void AddItemToPlayer(Player player) {
        if (itemToGive != null && itemToGive.getImage() != null) {
            if (!player.getInv().contains(itemToGive)) {
                player.getInv().add(itemToGive);
                System.out.println("Objet ajout� � l'inventaire : " + itemToGive.getID());
                itemToGive = null;  // Assurez-vous que l'objet ne peut �tre donn� qu'une fois
            } else {
                System.out.println("L'objet existe d�j� dans l'inventaire.");
            }
        } else {
            System.out.println("Aucun objet � donner ou image manquante.");
        }
    }

    protected void initializePosition(char identifier) {
        File nameMap = new File(gp.currentMap);
        try {
            Point[] spawnPoints = Entity.findSpawnPoints(identifier, nameMap.getName());
            if (spawnPoints.length > 0) {
                Point x = spawnPoints[0];
                screenX = (int) (gp.tileSize * x.getY());
                screenY = (int) (gp.tileSize * x.getX());
                this.setSolidAir(new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding));
            } else {
                //System.err.println("Aucun point de spawn trouv� pour l'identifiant: " + identifier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
    
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void interact() {
        setVisible(false); // Rendre le PNJ invisible lors de l'interaction
        triggerDialog(); // D�clenche le dialogue si n�cessaire
    }

	public boolean isCollisionWithPlayer() {
		return isCollisionWithPlayer;
	}

	public void setCollisionWithPlayer(boolean isCollisionWithPlayer) {
		this.isCollisionWithPlayer = isCollisionWithPlayer;
	}

}
