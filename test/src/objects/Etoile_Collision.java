package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

public class Etoile_Collision extends gameObject implements Usable {
	
    
    public Etoile_Collision(GamePanel gp) {
        super(gp, "plus de collision", "w", "/objects/etoile_mario.png", true);
    }


    public void use(Player player) {
        player.setCollisionEnabled(false);  // Désactiver les collisions

        // Utiliser un timer pour réactiver les collisions après 5 secondes (5000 millisecondes)
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    player.setCollisionEnabled(true);  // Réactiver les collisions
                }
            },
            20000
        );

        System.out.println("Collision désactivée temporairement.");
    }
    
    @Override
    public boolean isConsumable() { 
        return true; // la potion est utilisable qu'une fois 
    }

   
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }

}
