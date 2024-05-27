package objects;

import javax.swing.JOptionPane;

import entity.PNJ;
import entity.PNJ1;
import entity.Player;
import test.GamePanel;

public class Item4 extends gameObject implements Usable{
	public Item4(GamePanel gp) {
        super(gp, "Plante", "r", "/objects/plant.png", true);
    }

	@Override
	 public void use(Player player) {
	       player.removeInv(this);
	    }

	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return false;
	}
}