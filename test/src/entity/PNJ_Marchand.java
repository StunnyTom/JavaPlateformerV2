package entity;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.Apple;
import objects.Epee;
import objects.Usable;
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
        pnjInventory.add(new Epee(gp));
    }

    public void showPNJInventoryConsole() {
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
        String[] options = pnjInventory.stream().map(gameObject::getNom).toArray(String[]::new);
        int selected = JOptionPane.showOptionDialog(null, "Choisissez un objet à acheter:",
            "Inventaire du Marchand", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (selected >= 0) {
            gameObject item = pnjInventory.get(selected);
            gp.getPlayer().addInv(item); // Ajoutez l'objet sans vérifier s'il est Usable
            System.out.println("Objet ajouté à l'inventaire : " + item.getNom());
        }else {
            System.out.println("L'objet sélectionné n'est pas utilisable car il n'implémente pas Usable: ");
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

	@Override
	public void triggerDialog() {
		// TODO Auto-generated method stub
		this.showPNJInventoryConsole();
	}
}