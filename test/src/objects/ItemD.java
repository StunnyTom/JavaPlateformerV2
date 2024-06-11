package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;

//permet de recuperer a vie du monstre
public class ItemD extends gameObject implements Usable {
    public ItemD(GamePanel gp) {
        super(gp, "ItemD", "D", "/objects/Pistolet.png", true);
    }

    @Override
    public void use(Player player) {
        
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
