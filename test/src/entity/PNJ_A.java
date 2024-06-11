package entity;

import java.util.ArrayList;
import objects.gameObject;
import test.GamePanel;

public class PNJ_A extends PNJ {
    private long lastInteractionTime = 0;  // Temps de la derni�re interaction

    public PNJ_A(GamePanel gp) {
        super(gp,"/img_npj/susie_pnj.png", 15);
        initializePosition('A');
    }

    @Override
    public void triggerDialog() {
        Player player = gp.getPlayer();
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastInteractionTime < 10000) {  // 10000 millisecondes = 10 secondes
            System.out.println("Attendez encore avant une nouvelle interaction.");
            return;
        }

        lastInteractionTime = currentTime; // Mettre � jour le temps de la derni�re interaction

        System.out.println("V�rification de la possession de l'itemA");
        if (player.hasItem("c")) {
            System.out.println("ItemA est pr�sent, �change en cours...");
            exchangeItem(player);
        } else {
            System.out.println("Pas l'item");
        }
    }

    private void exchangeItem(Player player) {
        // Afficher l'inventaire actuel
        System.out.println("Inventaire avant �change:");
        for (gameObject item : player.getInv()) {
            System.out.println(" - " + item.getID());
        }

        // Code existant pour l'�change...
    }

    public boolean hasItem(String itemID) {
        System.out.println("Recherche de l'item: " + itemID);
        for (gameObject item : inv) {
            System.out.println("Comparaison avec l'item dans l'inventaire: " + item.getID());
            if (item.getID().equalsIgnoreCase(itemID)) {
                System.out.println("Item trouv�!");
                return true;
            }
        }
        System.out.println("Item non trouv�.");
        return false;
    }
}
