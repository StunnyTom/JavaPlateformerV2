package entity;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import objects.gameObject;
import test.GamePanel;

public class PNJ_bandana  extends PNJ { 
	private gameObject itemToGive;
    	public PNJ_bandana(GamePanel gp) {
            super(gp, "/img_npj/npj1_neutre.png", 955, 240, 15);
           
        dialogueTimer = new Timer(900, e -> isCollisionWithPlayer = false);
        dialogueTimer.setRepeats(false); // le timer ne se r�p�te pas
    
    	//METTRE DANS LA CLASSE INVENTAIRE
        // Initialisation de l'objet � donner
        this.itemToGive = new gameObject(false);
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/epe.png"));
            itemToGive.image = itemImage;
            itemToGive.setID("1"); // Un identifiant unique pour cet objet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    	//METTRE DANS LA CLASSE INVENTAIRE
    	public void addItemToInventory(Player player) {
    	    gameObject item = this.itemToGive;
    	    if (item != null && item.image != null) {
    	        // V�rifier si l'inventaire contient d�j� cet objet
    	        boolean alreadyInInventory = false;
    	        for (gameObject invItem : player.inv) {
    	            if (invItem.getID().equals(item.getID())) {
    	                alreadyInInventory = true;
    	                break;
    	            }
    	        }
    	        // Ajouter l'objet seulement s'il n'est pas d�j� dans l'inventaire
    	        if (!alreadyInInventory) {
    	            player.inv.add(item);
    	           System.out.println("Objet ajout� � l'inventaire : " + item.getID());
    	        } else {
    	            //System.out.println("L'objet existe d�j� dans l'inventaire.");
    	        }
    	    } 
    	}

        
    //boite de dialogue propre a chaque
    public void drawDialogue(Graphics2D g2) {
        String text = "Bonjour, je suis Bandana ! Je te donne cette �p� pour vainqre le monstre.";
        int boxWidth = 220;
        int boxHeight = 50;
        int boxX = screenX - boxWidth + 20  + gp.tileSize / 2; // Centre la bo�te par rapport au PNJ
        int boxY = screenY - boxHeight - 20; // Juste au-dessus du PNJ

        g2.setColor(new Color(0, 0, 0, 150)); // Couleur noire semi-transparente
        g2.fillRect(boxX, boxY, boxWidth, boxHeight); // Dessiner le rectangle
        g2.setColor(Color.WHITE);

        // D�coupe et affiche le texte ligne par ligne
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int x = boxX + 3; // Marge interne pour le texte
        int y = boxY + lineHeight;

        for (String line : text.split(" ")) {
            String testLine = line + " ";
            int lineWidth = fm.stringWidth(testLine);
            if (x + lineWidth > boxX + boxWidth - 10) {
                // Retour � la ligne si le mot d�passe la largeur de la bo�te
                x = boxX + 10; // R�initialise la position x pour la ligne suivante
                y += lineHeight; // Passe � la ligne suivante
            }
            g2.drawString(testLine, x, y);
            x += lineWidth;
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
        
        // Dessine l'image sur le rectangle bleu
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        if (isCollisionWithPlayer) {
            drawDialogue(g2); // Dessiner la bo�te de dialogue si collision
        }
    }  
}