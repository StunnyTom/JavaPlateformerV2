package entity;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.gameObject;
import test.GamePanel;

public class PNJ_Marchand extends PNJ {
	private Player player;
    private ArrayList<gameObject> pnjInventory = new ArrayList<>();
    private long lastCollisionTime = 0; // Temps de la derni�re collision
    public static final long COLLISION_COOLDOWN = 10000; // D�lai de 10 secondes


    public PNJ_Marchand(GamePanel gp) {
        super(gp, "/img_npj/marchadn.png", 15);
        initializePosition('W');
        this.player = gp.getPlayer();
        addItemsToInventory();
    }

    private void addItemsToInventory() {
        // Exemple d'ajout d'items avec des valeurs fictives appropri�es pour les param�tres
        pnjInventory.add(new gameObject(gp, "Potion", "p", "/objects/potion.png", false));
        pnjInventory.add(new gameObject(gp, "�p�e", "e", "/objects/epe.png", true));
       
    }


    public void showPNJInventoryConsole() {
        if (System.currentTimeMillis() - lastCollisionTime > COLLISION_COOLDOWN) {
            System.out.println("Inventaire du PNJ:");
            for (gameObject item : pnjInventory) {
                if (item != null) {
                    System.out.println(item);  
                } else {
                    System.out.println("Objet non d�fini");
                }
            }
            lastCollisionTime = System.currentTimeMillis();
        }
    }


 // M�thode pour afficher l'inventaire et permettre la s�lection
    public gameObject selectItemFromInventory() {
        String[] options = pnjInventory.stream().map(gameObject::getNom).toArray(String[]::new);
        int selected = JOptionPane.showOptionDialog(null, "Choisissez un objet � acheter:",
            "Inventaire du Marchand", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (selected >= 0) {
            return pnjInventory.get(selected);
        }
        return null;
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
		
	}
}