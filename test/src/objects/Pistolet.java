package objects;

import java.awt.Graphics2D;
import entity.Monster;
import entity.Player;
import generation.Generateur;
import test.GamePanel;

public class Pistolet extends gameObject implements Usable {
    
    public Pistolet(GamePanel gp) {
        super(gp, "Pistolet", "t", "/objects/pistolet.png", true);
    }

    public void use(Player player) {
    	//trouver le monstre le plus proche (initialisation)
        Monster closestMonster = findClosestMonster(player);
        if (closestMonster != null) { //si le monstre est differents de nul, tu attaques et tu le tue
            System.out.println("Tu attaques le monstre le plus proche avec le pistolet.");
            closestMonster.setVisible(false);
        } 
        /* pour interagir avec le pnj le plus proche 
         * else{
            PNJ closestPNJ = findClosestPNJ(player);
            if (closestPNJ != null) {
                System.out.println("Vous interagissez avec le PNJ le plus proche.");
                closestPNJ.interact();
            } 
            */
        else {
                System.out.println("Aucun monstre ou PNJ à proximité.");
            }
        //}
    }

    //trouve le monstre le plus proche
    private Monster findClosestMonster(Player player) {
        Monster closest = null;
        double minDistance = Double.MAX_VALUE; // Définir une distance maximale grande au début
        int playerX = player.getScreenX();
        int playerY = player.getScreenY();

        // parcourt tous les générateurs
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
    /* trouver le pnj le plus proche 
    private PNJ findClosestPNJ(Player player) {
        PNJ closest = null;
        double minDistance = Double.MAX_VALUE;
        int playerX = player.getScreenX();
        int playerY = player.getScreenY();

        for (Generateur gen : gp.Genlist) {
            if (gen instanceof PNJ) {
            	PNJ npc = (PNJ) gen;
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
*/
    
    @Override
    public boolean isConsumable() {
        return true; 
    }
    
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
