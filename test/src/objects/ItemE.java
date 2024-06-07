package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;


/*permet de voler un objet au pnj */

public class ItemE extends gameObject implements Usable {
    public ItemE(GamePanel gp) {
        super(gp, "ItemE", "E", "/objects/etoile_mario.png", true);
    }

    //Méthode commune aux objets
    @Override
    public void use(Player player) {
    	gp.gameState.afficheVictory(); 
        
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
