package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;

public class ItemC extends gameObject implements Usable {
    public ItemC(GamePanel gp) {
        super(gp, "ItemA", "T", "/objects/Potion.png", true);
    }

    @Override
    public void use(Player player) {
        player.teleportToInitialPosition();
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
