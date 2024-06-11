package objects;

import entity.Player;
import entity.PNJ;
import test.GamePanel;
import java.awt.Graphics2D;

/**
 * ItemA permet de voir l'inventaire d'un PNJ que le joueur a déjà rencontré.
 */
public class ItemA extends gameObject implements Usable {
    // Constructeur pour ItemA
    public ItemA(GamePanel gp) {
        super(gp, "ItemA", "c", "/objects/aimant.png", true);
    }

    /**
     * Utilise l'objet pour afficher l'inventaire du dernier PNJ rencontré par le joueur.
     * @param player Le joueur qui utilise l'objet.
     */
    @Override
    public void use(Player player) {
        // Vérifier si le joueur a rencontré des PNJ
        if (!player.getLastPNJs().isEmpty()) {
            // Afficher l'inventaire du dernier PNJ rencontré
            PNJ lastPNJ = player.getLastPNJs().get(player.getLastPNJs().size() - 1);
            lastPNJ.showInventory();
        } else {
            System.out.println("Aucun PNJ rencontré pour l'instant.");
        }
    }

    /**
     * Détermine si l'objet est consommable.
     * @return vrai, puisque cet objet peut être utilisé une seule fois pour afficher l'inventaire.
     */
    @Override
    public boolean isConsumable() {
        return true;
    }

    /**
     * Dessine l'objet sur l'écran.
     * @param g2 Contexte graphique sur lequel dessiner l'objet.
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
