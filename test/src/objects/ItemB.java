package objects;

import entity.Player;
import entity.PNJ;
import test.GamePanel;
import java.awt.Graphics2D;


import javax.swing.JOptionPane;

public class ItemB extends gameObject implements Usable {
	
	private boolean wasUsedSuccessfully = false;  // Ajoutez cet attribut pour suivre l'utilisation
	
    public ItemB(GamePanel gp) {
        super(gp, "ItemB", "B", "/objects/eclair.png", true);
    }

    @Override
    public void use(Player player) {
        if (!player.getLastPNJs().isEmpty()) {
            PNJ lastPNJ = player.getLastPNJs().get(player.getLastPNJs().size() - 1);
            if (!lastPNJ.getInv().isEmpty()) {
                   // this.wasUsedSuccessfully = true;  // Supposons que cet attribut indique si l'item a été utilisé
                }
             else {
                JOptionPane.showMessageDialog(null, "Le PNJ n'a plus d'objets à voler.");
                this.wasUsedSuccessfully = false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucun PNJ rencontré pour voler des objets.");
            this.wasUsedSuccessfully = false;
        }
    }

    @Override
    public boolean isConsumable() {
        return wasUsedSuccessfully;  // L'objet est consommable uniquement s'il a été utilisé avec succès
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
}
