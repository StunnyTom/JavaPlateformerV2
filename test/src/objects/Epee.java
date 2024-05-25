package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

public class Epee extends gameObject implements Usable {

    public Epee(GamePanel gp) {
        super(gp,"Epée", "e", "/objects/epe.png", true);
    }

    @Override
    public void use(Player player) {
        // Implémentation spécifique de l'utilisation de l'épée
    }
    
    @Override
    public boolean isConsumable() {
        return false; // L'épée n'est pas consommée après utilisation
    }
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }
 
}
