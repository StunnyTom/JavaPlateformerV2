package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import test.GamePanel;

public class PNJ_bandana extends Entity {
    GamePanel gp;
    
    public PNJ_bandana(GamePanel gp) {
    	super();
        this.gp = gp;
        
        direction = "neutre";
        // Position fixe pour le PNJ_bandana
        screenX = 955; // position horizontale
        screenY = 240; // position verticale
		int padding = 15; // Ajouter 10 pixels de marge autour du PNJ

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
    }  
}
