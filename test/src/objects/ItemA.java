package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;


/*permet de voler un objet au pnj */

public class ItemA extends gameObject implements Usable {
    public ItemA(GamePanel gp) {
        super(gp, "ItemA", "c", "/objects/aimant.png", true);
    }

    //Méthode commune aux objets
    @Override
    public void use(Player player) {
        
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
