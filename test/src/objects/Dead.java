package objects;

import entity.Player;

public class Dead extends gameObject implements Usable {
    
    public Dead() {
        super("Mort", "d", "/objects/eclair.png", true);
    }

    @Override
    public void use(Player player) {
        if (player.getLives() > 0) {
            player.setLives(player.getLives() - 1);  // R�duire les vies du joueur
            System.out.println("Vies restantes apr�s interaction avec l'�clair: " + player.getLives());
            if (player.getLives() == 0) {
                player.getGamePanel().gameState.afficheGameOver();  // Terminer le jeu si le joueur n'a plus de vies
            }
        }
    }

    @Override
    public boolean isConsumable() {
        return true;  // L'�clair est consomm� apr�s utilisation
    }
}
