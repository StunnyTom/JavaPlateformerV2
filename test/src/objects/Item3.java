package objects;

import javax.swing.JOptionPane;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entity.PNJ;
import entity.PNJ1;
import entity.Player;
import test.GamePanel;
import tiles.MapTransition;

public class Item3 extends gameObject implements Usable{
	public Item3(GamePanel gp) {
        super(gp, "T�l�porteur", "T", "/objects/tp.png", true);
    }

	@Override
    public void use(Player player) {
        List<Point> castelPositions = gp.getTileM().getVisibleCastelTilePositions();

        if (castelPositions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune tuile 'castel' visible � l'�cran.");
            return;
        }

        Random random = new Random();
        Point randomPosition = castelPositions.get(random.nextInt(castelPositions.size()));

        player.setScreenX(randomPosition.x);
        player.setScreenY(randomPosition.y);

        JOptionPane.showMessageDialog(null, "Vous avez �t� t�l�port� � un lieu de changement de monde.");
    }

	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return false;
	}
}
