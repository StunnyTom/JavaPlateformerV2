package objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import test.GamePanel;

public class Item_Actif extends gameObject {

    public Item_Actif(GamePanel gp) {
        super(false); // Remplacez 'false' par 'true' si cet objet doit avoir une collision
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/obj/cle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nom = "cle"; // Nom de l'objet
    }
}
