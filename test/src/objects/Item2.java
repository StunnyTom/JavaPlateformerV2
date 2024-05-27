package objects;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import entity.PNJ;
import entity.PNJ1;
import entity.Player;
import test.GamePanel;

public class Item2 extends gameObject implements Usable{
	
	public Item2(GamePanel gp) {
        super(gp, "Canne à pêche", "c", "/objects/canne.png", true);
    }

	@Override
	public void use(Player player) {
	    if (player.getLastPNJs().isEmpty()) {
	        //JOptionPane.showMessageDialog(null, "Aucun PNJ dans la liste.");
	        return;
	    }

	    ArrayList<PNJ> lastPNJs = player.getLastPNJs();
	    StringBuilder pnjOptions = new StringBuilder();
	    for (int i = 0; i < lastPNJs.size(); i++) {
	        pnjOptions.append(i + 1).append(". ").append(lastPNJs.get(i).getID()).append("\n");
	    }

	    String selectedPNJIndexStr = JOptionPane.showInputDialog(
	            null,
	            "Choisissez un PNJ pour voler un objet:\n" + pnjOptions.toString(),
	            "Voler un objet",
	            JOptionPane.INFORMATION_MESSAGE
	    );

	    if (selectedPNJIndexStr == null || selectedPNJIndexStr.isEmpty()) {
	        return; // Aucun PNJ sélectionné
	    }

	    int pnjChoice;
	    try {
	        pnjChoice = Integer.parseInt(selectedPNJIndexStr) - 1;
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Sélection invalide.");
	        return;
	    }

	    if (pnjChoice < 0 || pnjChoice >= lastPNJs.size()) {
	        JOptionPane.showMessageDialog(null, "Sélection invalide.");
	        return;
	    }

	    PNJ selectedPNJ = lastPNJs.get(pnjChoice);

	    // Vérifier si le PNJ sélectionné a des objets dans son inventaire
	    ArrayList<gameObject> pnjInventory = ((PNJ1)selectedPNJ).getPnjInv();

	    StringBuilder objectOptions = new StringBuilder();
	    for (int i = 0; i < pnjInventory.size(); i++) {
	        objectOptions.append(i + 1).append(". ").append(pnjInventory.get(i).getNom()).append("\n");
	    }

	    String selectedObjectIndexStr = JOptionPane.showInputDialog(
	            null,
	            "Choisissez un objet à voler:\n" + objectOptions.toString(),
	            "Inventaire de " + selectedPNJ.getID(),
	            JOptionPane.INFORMATION_MESSAGE
	    );

	    if (selectedObjectIndexStr == null || selectedObjectIndexStr.isEmpty()) {
	        return; // Aucun objet sélectionné
	    }

	    int objectChoice;
	    try {
	        objectChoice = Integer.parseInt(selectedObjectIndexStr) - 1;
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Sélection invalide.");
	        return;
	    }

	    if (objectChoice < 0 || objectChoice >= pnjInventory.size()) {
	        JOptionPane.showMessageDialog(null, "Sélection invalide.");
	        return;
	    }

	    gameObject stolenObject = pnjInventory.get(objectChoice);

	    // Ajouter l'objet volé à l'inventaire du joueur
	    player.removeInv(this);
	    player.addInv(stolenObject);
	    ((PNJ1)selectedPNJ).getPnjInv().remove(stolenObject);
	    JOptionPane.showMessageDialog(null, "Vous avez volé : " + stolenObject.getNom());
	}




	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return false;
	}
}
