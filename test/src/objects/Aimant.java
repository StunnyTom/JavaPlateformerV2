package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JOptionPane;

public class Aimant extends gameObject implements Usable {
    public Aimant(GamePanel gp) {
        super(gp, "Aimant", "c", "/objects/aimant.png", true);
    }

    @Override
    public void use(Player player) {
        if (player.isCollisionWithPNJ()) {  // V�rifie si la collision avec un PNJ a eu lieu
            System.out.println("Utilisation de l'Aimant pour voler un objet au PNJ.");
            gameObject[] possibleItems = {new Apple(gp), new Fantome_Collision(gp), new Dead(gp)};
            Random rand = new Random();
            gameObject stolenItem = possibleItems[rand.nextInt(possibleItems.length)];
            player.getInv().add(stolenItem);  // Ajoute l'objet vol� � l'inventaire
            JOptionPane.showMessageDialog(null, "Item vol� au PNJ: " + stolenItem.getNom());
            System.out.println("Voici un objet : " + stolenItem.getNom());  // Affiche le nom de l'objet vol�
            player.setCollisionWithPNJ(false); // R�initialise l'�tat apr�s utilisation
        } else {
            System.out.println("Je ne peux pas voler un objet sans avoir touch� un PNJ.");  // Message si pas de collision
            JOptionPane.showMessageDialog(null, "Je ne peux pas voler un objet sans avoir touch� un PNJ.");
        }
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
