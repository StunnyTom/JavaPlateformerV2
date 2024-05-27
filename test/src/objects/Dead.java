package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

//Objet qui enlève de la vie au joueur
public class Dead extends gameObject implements Usable {
    
    public Dead(GamePanel gp) {
        super(gp,"Deplacement", "d", "/objects/eclair.png", true);
    }

    //Méthode commune aux objets
    @Override
    public void use(Player player) {
    	System.out.println("deplacement du joueur ");
    }
    

    @Override
    public boolean isConsumable() {
        return true;  // L'éclair est consommé après utilisation
    }
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);
    }
}
