package objects;

import entity.Player;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Potion extends gameObject implements Usable {
	
    private static final int POTION_EFFECT_DURATION = 10000; // une durée de 10 secondes 
    private static final double BOOSTED_JUMP_SPEED = -5; // permet de sauter plus haut 

    public Potion() {
        super(true); // La potion a une collision
        this.nom = "Potion"; // nom 
        this.id = "p"; //id
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/potion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    @Override
    public String getDescription() {
        return "tu peux sauter pluvsvsdvsss haut pendant 10 secondes";
    }
}