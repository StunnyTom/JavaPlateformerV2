package objects;

import java.awt.Graphics2D;
import entity.Monster;
import entity.Player;
import generation.Generateur;
import test.GamePanel;

//Objet pour tuer le monstre le plus proche
public class Pistolet extends gameObject implements Usable {

    public Pistolet(GamePanel gp) {
        super(gp, "Pistolet", "t", "/objects/pistolet.png", true);
    }

    public void use(Player player) {
        // Trouver le monstre le plus proche (initialisation)
        Monster closestMonster = findClosestMonster(player);
        if (closestMonster != null) { // Si le monstre est différent de null, tu attaques et tu le tues
            System.out.println("Tu attaques le monstre le plus proche avec le pistolet.");
            closestMonster.setVisible(false);
            closestMonster.setCollidable(false);

            // Récupérer de la vie
            player.gainLife();
        } else {
            System.out.println("Aucun monstre ou PNJ à proximité.");
        }
    }

    // Trouve le monstre le plus proche
    private Monster findClosestMonster(Player player) {
        Monster closest = null;
        double minDistance = Double.MAX_VALUE; // Définir une distance maximale grande au début
        int playerX = player.getScreenX();
        int playerY = player.getScreenY();

        // Parcourt tous les générateurs
        for (Generateur gen : gp.Genlist) {
            if (gen instanceof Monster) {
                Monster mon = (Monster) gen;
                int monX = mon.getScreenX();
                int monY = mon.getScreenY();
                double distance = Math.sqrt((monX - playerX) * (monX - playerX) + (monY - playerY) * (monY - playerY));

                if (distance < minDistance) {
                    minDistance = distance;
                    closest = mon;
                }
            }
        }
        return closest;
    }

    @Override
    public boolean isConsumable() {
        return true; 
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
