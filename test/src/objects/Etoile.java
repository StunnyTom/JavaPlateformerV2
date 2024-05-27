package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

//Objet qui rend invincible et permet de tuer n'importe quel ennemi
public class Etoile extends gameObject implements Usable {
	
    
    public Etoile(GamePanel gp) {
        super(gp, "Victoire", "i", "/objects/etoile_mario.png", true);
    }

    
    public void use(Player player) {
    	 gp.gameState.afficheVictory();
    }

    
    @Override
    public boolean isConsumable() { 
        return true; // la potion est utilisable qu'une fois 
    }

   
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }

}
