package entity;

import objects.ItemA; // Exemple d'item
import objects.ItemB; // Exemple d'item
import test.GamePanel;
import javax.swing.JOptionPane; // Pour afficher un dialogue de choix

public class Coffre extends PNJ {
    private boolean isOpened = false; // Pour v�rifier si le coffre a d�j� �t� ouvert

    public Coffre(GamePanel gp) {
        super(gp, "/img_npj/Coffre.png", 15);
        initializePosition('C');
    }

    @Override
    public void triggerDialog() {
        if (!isOpened) {
            String[] options = {"Item A", "Item B"};
            int choice = JOptionPane.showOptionDialog(null, "Choisissez un item:", "Coffre",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            
            switch (choice) {
                case 0:
                    gp.getPlayer().addInv(new ItemA(gp));
                    break;
                case 1:
                    gp.getPlayer().addInv(new ItemB(gp));
                    break;
                default:
                    System.out.println("Aucun choix effectu� ou dialogue ferm�.");
                    return;
            }
            
            isOpened = true; // Marquer le coffre comme ouvert pour �viter de reprendre un item
            setVisible(false); // Rendre le coffre invisible ou le supprimer de la sc�ne
            System.out.println("Item choisi : " + options[choice]);
        } else {
            System.out.println("Le coffre a d�j� �t� ouvert.");
        }
    }


    // M�thode pour d�finir la visibilit� (en supposant que votre classe de base Entity ou PNJ a cette m�thode)
    public void setVisible(boolean visible) {
        // Logique pour g�rer la visibilit� du coffre
        this.isVisible = true;
    }
}
