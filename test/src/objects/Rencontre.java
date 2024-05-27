package objects;

import entity.Player;
import test.GamePanel;

public class Rencontre extends gameObject implements Usable {
    public Rencontre(GamePanel gp) {
        super(gp, "rencontre", "r", "/objects/potion.png", true);
    }

    @Override
    public void use(Player player) {
        // Vérifier si le joueur a eu une collision avec un PNJ
        if (player.isCollisionWithPNJ()) {
            System.out.println("salut");
           // lastInteractedPnj.showInventory();
            
        } else {
            System.out.println("Pas utilisable");
        }
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}
