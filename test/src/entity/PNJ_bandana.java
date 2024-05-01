package entity;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import test.GamePanel;

public class PNJ_bandana extends Entity {
    GamePanel gp;
    public boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    
    public PNJ_bandana(GamePanel gp) {
    	super();
        this.gp = gp;
        direction = "neutre";
        screenX = 955; // position horizontale
        screenY = 240; // position verticale
		int padding = 15; // marge autour du PNJ

        // Définir la zone de collision
        super.solidAir = new Rectangle(screenX - padding, screenY - padding, gp.tileSize + 2 * padding, gp.tileSize + 2 * padding);
        getImage();
    }

    public void getImage() {
        try {
            super.neutre1 = ImageIO.read(getClass().getResourceAsStream("/img_npj/npj1_neutre.png"));
            super.neutre2 = ImageIO.read(getClass().getResourceAsStream("/img_npj/npj1_neutre.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawDialogue(Graphics2D g2) {
        String text = "Bonjour, je suis Bandana ! Je te donne cette plume pour sauter plus haut.";
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
