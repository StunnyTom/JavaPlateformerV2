package objects;

import java.awt.Graphics2D;
import entity.Player;
import test.GamePanel;

/* Cette potion permet de sauter + haut 
 * Implementation du code qui permet de l'utiliser 2 fois avant de disparaitre
 */

public class Potion extends gameObject implements Usable {
	
    private static final int POTION_EFFECT_DURATION = 10000; // une durée de 10 secondes 
    private static final double BOOSTED_JUMP_SPEED = -5; // permet de sauter plus haut 
    private int usesRemaining = 2; // Nombre d'utilisations initiales de la potion
    
    public Potion(GamePanel gp) {
        super(gp, "potion", "p", "/objects/potion.png", true);
    }


    @Override
    public void use(Player player) { // l'effet sur le joueur 
    	 if (usesRemaining > 0) {
    	        System.out.println("tu peux sauter plus haut pendant 10 secondes");
    	        player.setPotionEffect(true, System.currentTimeMillis());
    	        usesRemaining--; // Décrémente le compteur d'utilisations
    	        if (usesRemaining == 0) {
    	            System.out.println("La potion est maintenant épuisée.");
    	        }
    	    } else {
    	        System.out.println("La potion est épuisée et ne peut plus être utilisée.");
    	    
    	    }
    }
    
    @Override
    public boolean isConsumable() { 
    	  return usesRemaining == 0;// la potion est utilisable qu'une fois
    }

    public static int getPotionEffectDuration() {
        return POTION_EFFECT_DURATION;
    }

    public static double getBoostedJumpSpeed() {
        return BOOSTED_JUMP_SPEED;
    }
    
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }

}