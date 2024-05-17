package objects;

import entity.Player;

public class Apple extends gameObject implements Usable {

    public Apple() {
        super("Vie", "v", "/objects/apple.png", true);
    }

    @Override
    public void use(Player player) {
        if (player.getLives() < player.getMaxLives()) {
        	 player.setLives(player.getLives() + 1); // Directement modifier les vies du joueur
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
