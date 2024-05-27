package objects;

import javax.swing.JOptionPane;

import entity.PNJ;
import entity.PNJ1;
import entity.Player;
import test.GamePanel;

public class Item5 extends gameObject implements Usable{
	public Item5(GamePanel gp) {
        super(gp, "Diamant", "d", "/objects/diamant.png", true);
    }

	@Override
	 public void use(Player player) {
	        gp.gameState.afficheVictory();
	    }

	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return false;
	}
}
