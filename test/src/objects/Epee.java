package objects;

import java.awt.Graphics2D;

import entity.Player;
import test.GamePanel;

public class Epee extends gameObject implements Usable {

    public Epee(GamePanel gp) {
        super(gp, "Ep�e", "e", "/objects/epe.png", true);
    }

    @Override
    public void use(Player player) {
        System.out.println("Tu attaques l'ennemi");
        player.setAttacking(true);  // Indique que le joueur est en train d'attaquer
    }

    @Override
    public boolean isConsumable() {
        return false; // L'�p�e n'est pas consomm�e apr�s utilisation
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
