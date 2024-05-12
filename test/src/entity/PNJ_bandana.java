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

public class PNJ_bandana extends PNJ { 
	
    	public PNJ_bandana(GamePanel gp) {
        super(gp, "/img_npj/npj1_neutre.png", 955, 240, 15); //image position du joueur et padding
        dialogueTimer = new Timer(900, e -> isCollisionWithPlayer = false);
        dialogueTimer.setRepeats(false); // le timer ne se répète pas
    
        // Initialisation de l'objet à donner
        this.itemToGive = new gameObject(false);
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/epe.png"));
            itemToGive.image = itemImage;
            itemToGive.setID("1"); // Un identifiant unique pour cet objet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //boite de dialogue propre a chaque
    public void drawDialogue(Graphics2D g2) {
        String text = "Bonjour, je suis Bandana ! Je te donne cette épé pour vaincre le monstre.";
        int boxWidth = 220;
        int boxHeight = 50;
        int boxX = screenX - boxWidth + 20  + gp.tileSize / 2; // Centre la boîte par rapport au PNJ
        int boxY = screenY - boxHeight - 20; // Juste au-dessus du PNJ

        g2.setColor(new Color(0, 0, 0, 150)); // Couleur noire semi-transparente
        g2.fillRect(boxX, boxY, boxWidth, boxHeight); // Dessiner le rectangle
        g2.setColor(Color.WHITE);

        // Découpe et affiche le texte ligne par ligne
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int x = boxX + 3; // Marge interne pour le texte
        int y = boxY + lineHeight;

        for (String line : text.split(" ")) {
            String testLine = line + " ";
            int lineWidth = fm.stringWidth(testLine);
            if (x + lineWidth > boxX + boxWidth - 10) {
                // Retour à la ligne si le mot dépasse la largeur de la boîte
                x = boxX + 10; // Réinitialise la position x pour la ligne suivante
                y += lineHeight; // Passe à la ligne suivante
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
            drawDialogue(g2); // Dessiner la boîte de dialogue si collision
        }
    }  
}