package objects;

import entity.Player;
import generation.Generateur;

import java.awt.Graphics2D;

import entity.PNJ;
import test.GamePanel;

public class Aimant extends gameObject implements Usable {
    public Aimant(GamePanel gp) {
        super(gp, "Aimant", "c", "/objects/aimant.png", true);
    }

    @Override
    public void use(Player player) {
        System.out.println("Utilisation de l'Aimant pour voler un objet.");
        PNJ target = findClosestPNJ(player); // Impl�mentez cette m�thode pour trouver le PNJ le plus proche
        if (target != null) {
            gameObject stolenItem = target.getStolenItem();
            if (stolenItem != null) {
                player.getInv().add(stolenItem);
                System.out.println("Objet vol� et ajout� � l'inventaire : " + stolenItem.getNom());
            } else {
                System.out.println("Aucun objet � voler.");
            }
        } else {
            System.out.println("Aucun PNJ � proximit� pour voler un objet.");
        }
    }

    private PNJ findClosestPNJ(Player player) {
        PNJ closest = null;
        double minDistance = Double.MAX_VALUE; // D�finir une distance maximale grande au d�but
        int playerX = player.getScreenX();
        int playerY = player.getScreenY();

        // parcourt tous les g�n�rateurs
        for (Generateur gen : gp.Genlist) {
            if (gen instanceof PNJ) { // Assurez-vous que le g�n�rateur est une instance de PNJ
                PNJ pnj = (PNJ) gen;
                int pnjX = pnj.getScreenX();
                int pnjY = pnj.getScreenY();
                double distance = Math.sqrt((pnjX - playerX) * (pnjX - playerX) + (pnjY - playerY) * (pnjY - playerY));

                if (distance < minDistance) {
                    minDistance = distance;
                    closest = pnj;
                }
            }
        }
        return closest;
    }


    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
