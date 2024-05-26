package objects;

import entity.Player;
import test.GamePanel;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JOptionPane;

/*permet de voler un objet au pnj */

public class Aimant extends gameObject implements Usable {
    public Aimant(GamePanel gp) {
        super(gp, "Aimant", "c", "/objects/aimant.png", true);
    }

    //M�thode commune aux objets
    @Override
    public void use(Player player) {
        System.out.println("Utilisation de l'Aimant pour voler un objet au pnj.");
       
        gameObject[] possibleItems = {new Apple(gp), new Fantome_Collision(gp), new Dead(gp)};// Liste des objets potentiellement vol�s
        Random rand = new Random(); // S�lectionne un objet au hasard parmi les objets possibles
        gameObject stolenItem = possibleItems[rand.nextInt(possibleItems.length)];

        // Ajouter l'objet vol� � l'inventaire du joueur
        player.getInv().add(stolenItem);
        JOptionPane.showMessageDialog(null,"Item vol� au PNJ: " + stolenItem.getNom());
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
