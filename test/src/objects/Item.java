package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import test.GamePanel;

public class Item extends gameObject {
    GamePanel gp;
    BufferedImage itemImage;
    int screenX, screenY;

    public Item(GamePanel gp, String imagePath) {
        super(false); // Les items n'ont pas de collision par défaut
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                throw new RuntimeException("Unable to load item image: " + imagePath);
            }
            this.itemImage = ImageIO.read(is);
            this.image = this.itemImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int x, int y) {
        this.screenX = x;
        this.screenY = y;
    }

    public void draw(Graphics2D g2) {
        if (itemImage != null) {
            g2.drawImage(itemImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
