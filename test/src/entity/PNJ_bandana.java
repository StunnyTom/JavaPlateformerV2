package entity;

import java.awt.Color;
import java.awt.Font;
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
        dialogueTimer.setRepeats(false); // Le timer ne se r�p�te pas
    
    
     // Initialisation de l'objet � donner avec la classe Epee existante
        itemToGive = new Epee(gp); // Utilisation de la classe Epee d�j� d�finie
}
    // G�re la bo�te de dialogue propre � chaque PNJ
    public void drawDialogue(Graphics2D g2) {
    	showInventory(); // Affiche l'inventaire lors de l'interaction
        String text = "Bonjour, je suis Bandana ! Je te donne cette �p�e pour vaincre le monstre.";
        int boxWidth = 220;
        int boxHeight = 50;
        int boxX = screenX - boxWidth + 20 + gp.tileSize / 2; // Centre la bo�te par rapport au PNJ
        int boxY = screenY - boxHeight - 20; // Juste au-dessus du PNJ

        g2.setColor(new Color(0, 0, 0, 150)); // Couleur noire semi-transparente
        g2.fillRect(boxX, boxY, boxWidth, boxHeight); // Dessiner le rectangle
        g2.setColor(Color.WHITE);

        // D�finir une police plus petite
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int x = boxX + 10; // Marge interne pour le texte
        int y = boxY + lineHeight;

        // Diviser le texte en mots pour g�rer le retour � la ligne
        String[] words = text.split(" ");
        String currentLine = "";

        for (String word : words) {
            String testLine = currentLine + word + " ";
            int lineWidth = fm.stringWidth(testLine);

            if (x + lineWidth > boxX + boxWidth - 10) {
                g2.drawString(currentLine, x, y); // Dessine la ligne actuelle
                currentLine = word + " "; // Commence une nouvelle ligne avec le mot actuel
                y += lineHeight; // Passe � la ligne suivante
                x = boxX + 10; // R�initialise la position x pour la nouvelle ligne
            } else {
                currentLine += word + " ";
            }
        }
        // Dessiner la derni�re ligne
        g2.drawString(currentLine, x, y);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2); // Dessine l'image du PNJ
        if (isCollisionWithPlayer) {
            drawDialogue(g2); // Dessine la bo�te de dialogue en cas de collision avec le joueur
        }
    }
 // Appeler cette m�thode quand il y a une collision
    public void triggerDialog() {
        isCollisionWithPlayer = true;
        dialogueTimer.restart(); // Red�marrez le timer chaque fois qu'il y a une collision
        this.AddItemToPlayer(gp.getPlayer());
    }
}


        