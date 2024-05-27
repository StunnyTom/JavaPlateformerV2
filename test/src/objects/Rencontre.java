package objects;

import entity.Player;
import test.GamePanel;

import javax.swing.JOptionPane;

public class Rencontre extends gameObject implements Usable {
    public Rencontre(GamePanel gp) {
        super(gp, "rencontre", "r", "/objects/potion.png", true);
    }

    @Override
    public void use(Player player) {
        // Vérifier si le joueur a eu des interactions avec des PNJ
        if (!player.hasPnjInteractions()) {
            // Afficher une boîte de dialogue indiquant que l'utilisation est impossible
            JOptionPane.showMessageDialog(null, "Impossible, rencontre un joueur", "Action impossible", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si l'inventaire du joueur est vide
        if (player.getInv().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pas d'interaction", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Afficher le contenu de l'inventaire s'il n'est pas vide
            StringBuilder inventoryContents = new StringBuilder("Contenu de l'inventaire:\n");
            player.getInv().forEach(item -> inventoryContents.append(item.getNom()).append(" - rencontré\n"));
            JOptionPane.showMessageDialog(null, inventoryContents.toString(), "Inventaire", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}



/*// Contenu principal affichant les items
JPanel panel = new JPanel();
panel.add(new JLabel("<html>Inventaire :<br/>"));
for (gameObject item : inv) {
    panel.add(new JLabel(item.getClass().getSimpleName() + "<br/>"));
}

*/