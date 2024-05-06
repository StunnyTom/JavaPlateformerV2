package entity;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import test.GamePanel;
import test.KeyHandler;

public class PNJ_Magalor extends PNJ {
	
	private boolean inDialogue = false;
    private int selectedOption = 0;  // Track selected dialogue option

	
    public PNJ_Magalor(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 755, 385, 50);
       
    }
 // Add method to toggle dialogue state
    public void toggleDialogue() {
        inDialogue = !inDialogue;
    }
 
    
    // Method to handle dialogue interaction
    public void updateDialogue(KeyHandler keyHandler) {
        if (inDialogue) {
            if (keyHandler.onePressed) selectedOption = 1;
            if (keyHandler.twoPressed) selectedOption = 2;
            if (selectedOption != 0) {
                System.out.println("Player selected option: " + selectedOption);
                toggleDialogue();  // Close dialogue after selection
                selectedOption = 0;  // Reset selection
            }
        }
    }
    //boite de dialogue propre a chaque
    public void drawDialogue(Graphics2D g2) {
        String text = "Salut toi, comment tu vas ? "
        		+ " 1. Oui 2. Non";
        
        int boxWidth = 220;
        int boxHeight = 50;
        int boxX = screenX - boxWidth + 20  + gp.tileSize / 2; // Centre la boîte par rapport au PNJ
        int boxY = screenY - boxHeight - 20; // Juste au-dessus du PNJ

        g2.setColor(new Color(0, 0, 0, 150)); // Couleur noire semi-transparente
        g2.fillRect(boxX, boxY, boxWidth, boxHeight); // Dessiner le rectangle
        g2.setColor(Color.WHITE);

        // Obtenir les métriques de la police pour centrer le texte
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = boxX + (boxWidth - textWidth) / 2;
        int textY = boxY + boxHeight / 2 + fm.getAscent() / 2;
        
        g2.drawString(text, textX, textY);
     
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
                toggleDialogue();
                drawDialogue(g2);
            }
        }  
}
