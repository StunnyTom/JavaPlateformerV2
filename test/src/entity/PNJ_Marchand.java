package entity;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import objects.Apple;
import objects.Dead;
import objects.gameObject;
import test.GamePanel;

public class PNJ_Marchand extends PNJ {
    private ArrayList<gameObject> pnjInventory = new ArrayList<>();
    private long lastCollisionTime = 0; // Temps de la dernière collision
    public static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes


    public PNJ_Marchand(GamePanel gp) {
        super(gp, "/img_npj/marchadn.png", 15);
        initializePosition('W');
        addItemsToInventory();
    }

    private void addItemsToInventory() {
        // Exemple d'ajout d'items avec des valeurs fictives appropriées pour les paramètres
        pnjInventory.add(new Apple(gp));
        pnjInventory.add(new Dead(gp));
    }

    public void showPNJInventoryConsole() {
    	showInventory(); // Affiche l'inventaire lors de l'interaction
        if (System.currentTimeMillis() - lastCollisionTime > COLLISION_COOLDOWN && !collisionOn) {
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
            collisionOn = true; // Marquer la première interaction
        } else if (!collisionOn) {
            System.out.println("Vous pouvez interagir avec ce PNJ.");
        } else {
            System.out.println("Le PNJ ne réagit plus aux interactions.");
        }
    }

    //Gère le choix dans l'étalage du marchand
    public void selectItemFromInventory() {
        String[] options = pnjInventory.stream().map(gameObject::getNom).toArray(String[]::new);
        int selected = JOptionPane.showOptionDialog(null, "Choisissez un objet à acheter:",
            "Inventaire du Marchand", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (selected >= 0) {
            gameObject item = pnjInventory.get(selected);

            if (gp.getPlayer().getInv().contains(item)) {
                JOptionPane.showMessageDialog(null, "Tu as déjà pris cet objet.");
            } else {
                gp.getPlayer().addInv(item); // Ajoute l'objet à l'inventaire du joueur
                System.out.println("Objet ajouté à l'inventaire : " + item.getNom());
            }
        } else {
            System.out.println("Aucun objet sélectionné.");
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

    //Méthode commune aux PNJ
	@Override
	public void triggerDialog() {
		// TODO Auto-generated method stub
		this.showPNJInventoryConsole();
	}
}