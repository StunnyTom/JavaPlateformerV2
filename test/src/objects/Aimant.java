package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

public class Aimant extends gameObject implements Usable{
	 public Aimant(GamePanel gp) {
	        super(gp, "Aimant", "a", "/objects/aimant.png", true);
	    }
	 
	 @Override
	    public void use(Player player) {
		 		System.out.println("je vais te voler un objet");
	    }

	    @Override
	    public boolean isConsumable() {
	        return true;
	    }
	    
	    public void draw(Graphics2D g2) {
	    	super.draw(g2);
	    }
	}

