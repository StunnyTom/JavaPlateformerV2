package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import objects.Key;
import objects.gameObject;
import test.GamePanel;

public class PNJ_Susie extends PNJ {
	private Player player; // Ajout d'un attribut Player (PERMET DE VOIR JUSTE L INVENTAIRE)
	private long lastCollisionTime = 0; // Temps de la dernière collision
    private static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes

    public PNJ_Susie(GamePanel gp,Player player) {
    	super(gp,"/img_npj/susie_pnj.png", 15);
        initializePosition('s'); // 'p' pour le point de spawn de Susie
        this.player = player;
    }
    
    //PERMET DE VOIR UNIQUEMENT L'INVENTAIRE DU JOUEUR
    /*public void showInventoryDialog() {
        // Vérifie si le délai de collision est passé
        if (System.currentTimeMillis() - lastCollisionTime > COLLISION_COOLDOWN) {
            ArrayList<gameObject> inventory = this.player.inv;
            StringBuilder items = new StringBuilder("Inventaire:\n");
            for(gameObject item : inventory) {
                items.append(item.id).append("\n");
            }

            JOptionPane.showMessageDialog(null, items.toString(), "Inventaire de Susie", JOptionPane.INFORMATION_MESSAGE);
            lastCollisionTime = System.currentTimeMillis(); // Réinitialiser le temps de la dernière collision
        }
    }*/
    
    //fonction qui permet de voir dans l'inventaire du joueur 
    public void showInventoryDialog() {
        if (System.currentTimeMillis() - getLastCollisionTime() > getCollisionCooldown()) { // me permet de plus avoir de collision avec mon pnj pendant 10 secondes 
            ArrayList<gameObject> inventory = this.player.getInv(); //permet d'acceder a l'inventaire de mon joueur
            boolean hasItem1 = false; // l'id 1 est d'office a faux 
            
            for(gameObject item : inventory) { // boucle dans mon inventaire
                if (item.getID().equals("e")) {
                    hasItem1 = true;
                    break; // Dès que l'ID 1 est trouvé, arrêtez la recherche
                }
            }

            // Message simple indiquant la présence ou l'absence de l'ID 1
            if (!hasItem1) {
                JOptionPane.showMessageDialog(null, "Il te manque un objet, reviens plus tard :(", "accès a l'inventaire de kirby", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Tu as enfin l'épé, tiens pour toi :)", "accès a l'inventaire du joueur", JOptionPane.INFORMATION_MESSAGE);
                initializeItemToGive();
                AddItemToPlayer(gp.getPlayer()); // Méthode pour ajouter un objet à l'inventaire du joueur
            }
            
            setLastCollisionTime(System.currentTimeMillis()); // Réinitialiser le temps de la dernière collision
        }
    }
    
    private void initializeItemToGive() {
    	 //this.itemToGive = new Key();
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
            itemToGive.setImage(itemImage);
            itemToGive.setID("3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public long getLastCollisionTime() {
		return lastCollisionTime;
	}

	public void setLastCollisionTime(long lastCollisionTime) {
		this.lastCollisionTime = lastCollisionTime;
	}

	public static long getCollisionCooldown() {
		return COLLISION_COOLDOWN;
	}
         
}
