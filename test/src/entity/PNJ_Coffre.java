package entity;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.gameObject;
import test.GamePanel;

public class PNJ_Coffre extends PNJ {
    private ArrayList<gameObject> pnjInventory = new ArrayList<>();
    private long lastCollisionTime = 0; // Temps de la dernière collision
    public static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes


    public PNJ_Coffre(GamePanel gp) {
        super(gp, "/img_npj/Coffre.png", 15);
        initializePosition('C');
    }

    @SuppressWarnings("unused")
	private void addItemsToInventory(gameObject o) {
        // Exemple d'ajout d'items avec des valeurs fictives appropriées pour les paramètres
    	gp.getPlayer().getInv().remove(o);
        pnjInventory.add(o);
    }

    public void showPNJInventoryConsole() {
    	showInventory(); // Affiche l'inventaire lors de l'interaction
        if (System.currentTimeMillis() - lastCollisionTime > COLLISION_COOLDOWN) {
            System.out.println("Inventaire du PNJ:");
            for (gameObject item : pnjInventory) {
                if (item != null) {
                    System.out.println(item);  
                } else {
                    System.out.println("Objet non défini");
                }
            }
            lastCollisionTime = System.currentTimeMillis();
            this.selectItemFromInventory();
        }
    }

 // Méthode pour afficher l'inventaire et permettre la sélection
    public void selectItemFromInventory() {
        String[] initialOptions = {"Déposer un objet", "Reprendre un objet"};
        int initialChoice = JOptionPane.showOptionDialog(
            null,
            "Voulez-vous déposer un objet ou en reprendre un ?",
            "Options de Marchand",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            initialOptions,
            initialOptions[0]
        );

        if (initialChoice == 0) { // Déposer un objet
            // Obtenez l'inventaire du joueur
            ArrayList<gameObject> playerInventory = gp.getPlayer().getInv();
            
            if (playerInventory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Votre inventaire est vide. Rien à déposer.");
                return;
            }

            // Affichez les objets du joueur pour qu'il en sélectionne un à déposer
            String[] playerOptions = playerInventory.stream().map(gameObject::getNom).toArray(String[]::new);
            int selectedToDeposit = JOptionPane.showOptionDialog(
                null,
                "Choisissez un objet à déposer:",
                "Inventaire du Joueur",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                playerOptions,
                playerOptions[0]
            );

            if (selectedToDeposit >= 0) {
                gameObject itemToDeposit = playerInventory.get(selectedToDeposit);

                // Choisissez une position dans l'inventaire du PNJ pour déposer l'objet
                String[] positions = new String[pnjInventory.size() + 1];
                for (int i = 0; i < positions.length; i++) {
                    positions[i] = "Position " + (i + 1);
                }
                int position = JOptionPane.showOptionDialog(
                    null,
                    "Choisissez une position pour déposer l'objet:",
                    "Inventaire du Marchand",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    positions,
                    positions[0]
                );

                if (position >= 0) {
                    pnjInventory.add(position, itemToDeposit);
                    gp.getPlayer().removeInv(itemToDeposit);
                    System.out.println("Objet déposé : " + itemToDeposit.getNom());
                }
            }
        } else if (initialChoice == 1) { // Reprendre un objet
            if (pnjInventory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "L'inventaire du marchand est vide. Rien à reprendre.");
                return;
            }

            // Affichez les objets du PNJ pour qu'il en sélectionne un à reprendre
            String[] pnjOptions = pnjInventory.stream().map(gameObject::getNom).toArray(String[]::new);
            int selectedToReclaim = JOptionPane.showOptionDialog(
                null,
                "Choisissez un objet à reprendre:",
                "Inventaire du Marchand",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                pnjOptions,
                pnjOptions[0]
            );

            if (selectedToReclaim >= 0) {
                gameObject itemToReclaim = pnjInventory.get(selectedToReclaim);
                gp.getPlayer().addInv(itemToReclaim);
                pnjInventory.remove(itemToReclaim);
                System.out.println("Objet repris : " + itemToReclaim.getNom());
            }
        }
    }

   
    // Getters et setters
    public long getLastCollisionTime() {
        return lastCollisionTime;
    }

    public void setLastCollisionTime(long lastCollisionTime) {
        this.lastCollisionTime = lastCollisionTime;
    }

    public static long getCollisionCooldown() {
        return COLLISION_COOLDOWN;
    }
    
    public ArrayList<gameObject> getPnjInv(){
    	return this.pnjInventory;
    }
    
    public void addPnjInv(gameObject o) {
    	this.pnjInventory.add(o);
    }

	@Override
	public void triggerDialog() {
		// TODO Auto-generated method stub
		this.showPNJInventoryConsole();
	}
}

