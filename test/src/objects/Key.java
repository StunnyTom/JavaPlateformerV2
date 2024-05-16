package objects;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Player;

public class Key extends gameObject implements Usable {
    public Key(String id) {
        super(true); // La clé a une collision
        this.nom = "Key";
        this.id = id;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use(Player player) {
        System.out.println("Clé utilisée: " + id);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    public String getDescription() {
        return "Obtiens 8 clés pour finir le jeu";
    }
}
