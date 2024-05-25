package objects;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Player;
import test.GamePanel;
public class Key extends gameObject implements Usable {
    public Key(GamePanel gp) {
        super(gp, true); // La cl� a une collision
        this.nom = "Key";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use(Player player) {
        System.out.println("Cl� utilis�e: " + id);
        player.addKey(this.id);  // Incr�menter le compteur de cl�s dans le joueur
    }

    @Override
    public boolean isConsumable() {
        return true;  // La cl� est consommable pour qu'elle soit retir�e une fois utilis�e
    }

    public String getDescription() {
        return "Obtiens 8 cl�s pour finir le jeu";
    }
    
    public void draw(Graphics2D g2) {
    	//System.out.println(this.getID());
    	super.draw(g2);
    }
}
