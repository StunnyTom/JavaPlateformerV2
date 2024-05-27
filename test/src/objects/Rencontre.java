package objects;

import entity.Player;
import test.GamePanel;

public class Rencontre extends gameObject implements Usable {
    public Rencontre(GamePanel gp) {
        super(gp, "rencontre", "r", "/objects/potion.png", true);
    }

    @Override
    public void use(Player player) {
        // Vérifier si le joueur a eu des interactions avec des PNJ
        if (!player.hasPnjInteractions()) {
            System.out.println("Pas utilisable");
            return;
        }

        // Vérifier si l'inventaire du joueur est vide
        if (player.getInv().isEmpty()) {
            System.out.println("Pas d'interaction");
        } else {
            // Afficher le contenu de l'inventaire
            System.out.println("Contenu de l'inventaire:");
            player.getInv().forEach(item -> {
                // Afficher les détails de chaque objet
                System.out.println(item.getNom() + " - rencontré");
            });
        }
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}
