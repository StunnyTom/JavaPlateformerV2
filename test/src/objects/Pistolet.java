package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

public class Pistolet extends gameObject implements Usable {

    public Pistolet(GamePanel gp) {
        super(gp,"Pistolet", "t", "/objects/pistolet.png", true);
    }

    @Override
    public void use(Player player) {
    	 System.out.println("tu attaques l'ennemie");
    }
    
    @Override
    public boolean isConsumable() {
        return true; 
    }
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }
 
}
