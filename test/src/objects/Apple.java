package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

//Objet pour rendre de la vie au joueur
public class Apple extends gameObject implements Usable {

    public Apple(GamePanel gp) {
        super(gp, "Vie", "v", "/objects/apple.png", true);
    }

    //Méthode commune aux objets
    @Override
    public void use(Player player) {
        // Vérifie si le joueur a moins de vies maximales
        if (player.getLives() < player.getMaxLives()) {
            player.setLives(player.getLives() + 1); // Ajoute une vie
            System.out.println("Vies après utilisation de la pomme: " + player.getLives());
        } else {
            System.out.println("Tu ne peux pas utiliser cet objet, tu as toutes tes vies");
            
        }
    }

    @Override
    public boolean isConsumable() {
        return true;
    }
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }
}
