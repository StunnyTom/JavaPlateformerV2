package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import objects.gameObject;
import test.GamePanel;

/* il faut avoir une pomme et elle donne un aure objet */

public class PNJ_Susie extends PNJ {
	private Player player; // Ajout d'un attribut Player (PERMET DE VOIR JUSTE L INVENTAIRE)
	private long lastCollisionTime = 0; // Temps de la dernière collision
    private static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes

    public PNJ_Susie(GamePanel gp) {
    	super(gp,"/img_npj/susie_pnj.png", 15);
        initializePosition('S'); // 'p' pour le point de spawn de Susie
        this.player = gp.getPlayer();
    }
    
    
    //Méthode commune aux PNJ, permet de voir dans l'inventaire du joueur 
    public void triggerDialog() {
        showInventory(); // Affiche l'inventaire lors de l'interaction
        if (System.currentTimeMillis() - getLastCollisionTime() > getCollisionCooldown()) {
            ArrayList<gameObject> inventory = this.player.getInv(); // Accès à l'inventaire du joueur
            boolean hasApple = false; // Assume que l'ID de la pomme est "v"
            
            for (gameObject item : inventory) { // Boucle pour vérifier l'inventaire
                if (item.getID().equals("e")) {
                    hasApple = true; // La pomme est trouvée
                    break;
                }
            }
            if (!hasApple) {
                JOptionPane.showMessageDialog(null, "Il te manque l'épée, reviens plus tard :(", "accès a l'inventaire de Susie", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Tu as enfin l'épée, tu peux passer a travers les collisions :)", "accès a l'inventaire de Susie", JOptionPane.INFORMATION_MESSAGE);
                initializeItemToGive(); // Initialise l'épée à donner
                addItemToPlayer(); // Ajoute l'item à l'inventaire du joueur
            }
            setLastCollisionTime(System.currentTimeMillis()); // Réinitialiser le temps de la dernière collision
        }
    }

    // Méthode pour ajouter un objet à l'inventaire du joueur
    private void addItemToPlayer() {
        player.getInv().add(this.itemToGive); // Ajoute l'épée à l'inventaire du joueur
    }

    private void initializeItemToGive() {
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/fantome.png"));
            this.itemToGive = new gameObject(gp, isCollisionWithPlayer); // Créez une nouvelle instance 
            this.itemToGive.setImage(itemImage);
            this.itemToGive.setID("f"); 
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
