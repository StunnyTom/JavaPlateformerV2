package entity;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.swing.Timer;
import objects.Epee;
import test.GamePanel;

public class PNJ_bandana extends PNJ {
    protected Timer dialogueTimer;
    
    public PNJ_bandana(GamePanel gp) {
        super(gp,"/img_npj/npj1_neutre.png", 15);
        initializePosition('b'); // 'p' pour le point de spawn de Test_Bandana
        dialogueTimer = new Timer(900, e -> isCollisionWithPlayer = false);
        dialogueTimer.setRepeats(false); // Le timer ne se répète pas
    
    
     // Initialisation de l'objet à donner avec la classe Epee existante
        itemToGive = new Epee(gp); // Utilisation de la classe Epee déjà définie
}

    // Gère la boîte de dialogue propre à chaque PNJ
    public void drawDialogue(Graphics2D g2) {
        String text = "Bonjour, je suis Bandana ! Je te donne cette épée pour vaincre le monstre.";
        int boxWidth = 220;
        int boxHeight = 50;
        int boxX = screenX - boxWidth + 20 + gp.tileSize / 2; // Centre la boîte par rapport au PNJ
        int boxY = screenY - boxHeight - 20; // Juste au-dessus du PNJ

        g2.setColor(new Color(0, 0, 0, 150)); // Couleur noire semi-transparente
        g2.fillRect(boxX, boxY, boxWidth, boxHeight); // Dessiner le rectangle
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int x = boxX + 3; // Marge interne pour le texte
        int y = boxY + lineHeight;

        for (String line : text.split(" ")) {
            String testLine = line + " ";
            int lineWidth = fm.stringWidth(testLine);
            if (x + lineWidth > boxX + boxWidth - 10) {
                x = boxX + 10; // Réinitialise la position x pour la ligne suivante
                y += lineHeight; // Passe à la ligne suivante
            }
            g2.drawString(testLine, x, y);
            x += lineWidth;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2); // Dessine l'image du PNJ
        if (isCollisionWithPlayer) {
            drawDialogue(g2); // Dessine la boîte de dialogue en cas de collision avec le joueur
        }
    }
 // Appeler cette méthode quand il y a une collision
    public void triggerDialogue() {
        isCollisionWithPlayer = true;
        dialogueTimer.restart(); // Redémarrez le timer chaque fois qu'il y a une collision
    }
}


        