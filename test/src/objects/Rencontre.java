package objects;

import entity.Player;
import entity.PNJ;
import test.GamePanel;

public class Rencontre extends gameObject implements Usable {
    public Rencontre(GamePanel gp) {
        super(gp, "rencontre", "r", "/objects/potion.png", true);
    }

    @Override
    public void use(Player player) {
        // Vérifier si le joueur a eu une collision avec un PNJ
        if (player.isCollisionWithPNJ()) {
            PNJ pnj = player.getLastInteractedPnj();
            if (pnj != null) {
                System.out.println("Inventaire du PNJ:");
                pnj.getInventory().forEach(item -> {
                    System.out.println(item.getNom() + " - " + item.getID());
                });
            } else {
                System.out.println("Aucun PNJ n'a été rencontré récemment.");
            }
        } else {
            System.out.println("Pas utilisable");
        }
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}
