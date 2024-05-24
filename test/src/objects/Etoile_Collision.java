package objects;

import entity.Player;

public class Etoile_Collision extends gameObject implements Usable {
	
    
    public Etoile_Collision() {
        super("plus de collision", "w", "/objects/etoile_mario.png", true);
    }


    public void use(Player player) {
        player.setCollisionEnabled(false);  // D�sactiver les collisions

        // Utiliser un timer pour r�activer les collisions apr�s 5 secondes (5000 millisecondes)
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    player.setCollisionEnabled(true);  // R�activer les collisions
                }
            },
            20000
        );

        System.out.println("Collision d�sactiv�e temporairement.");
    }
    
    @Override
    public boolean isConsumable() { 
        return true; // la potion est utilisable qu'une fois 
    }

   
   

}
