package objects;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Player;
public class Key extends gameObject implements Usable {
    public Key(String id) {
        super(true); // La cl� a une collision
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
        System.out.println("Cl� utilis�e: " + id);
        player.addKey(this.id);  // Incr�menter le compteur de cl�s dans le joueur
    }

    @Override
    public boolean isConsumable() {
        return true;  // La cl� est consommable pour qu'elle soit retir�e une fois utilis�e
    }

    public String getDescription() {
        return "Obtiens 8 cl�s pour finir le jeu";
    }
}
