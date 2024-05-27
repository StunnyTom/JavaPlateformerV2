package objects;

import javax.swing.JOptionPane;

import entity.PNJ;
import entity.PNJ1;
import entity.Player;
import test.GamePanel;

public class Item1 extends gameObject implements Usable{
	public Item1(GamePanel gp) {
        super(gp, "Lunettes", "l", "/objects/lunettes.png", true);
    }

	@Override
	 public void use(Player player) {
	        if (player.lastPNJs.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Aucun PNJ dans la liste.");
	            return;
	        }

	        for (PNJ pnj : player.getLastPNJs()) {
	            ((PNJ1) pnj).selectItemFromInventory();
	        }
	        player.removeInv(this);
	    }

	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return false;
	}
}
