package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;


public class Potion extends gameObject implements Usable {
	
    private static final int POTION_EFFECT_DURATION = 10000; // une dur�e de 10 secondes 
    private static final double BOOSTED_JUMP_SPEED = -5; // permet de sauter plus haut 

    public Potion(GamePanel gp) {
        super(gp, "potion", "p", "/objects/potion.png", true);
    }


    @Override
    public void use(Player player) { // l'effet sur le joueur 
        System.out.println("tu peux sauter plus haut pendant 10 secondes");
        player.setPotionEffect(true, System.currentTimeMillis());
    }
    
    @Override
    public boolean isConsumable() { 
        return true; // la potion est utilisable qu'une fois 
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