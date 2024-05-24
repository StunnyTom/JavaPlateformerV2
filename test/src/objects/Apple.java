package objects;

import entity.Player;

public class Apple extends gameObject implements Usable {

    public Apple() {
        super("Vie", "v", "/objects/apple.png", true);
    }

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
}
