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
	private long lastCollisionTime = 0; // Temps de la derni�re collision
    private static final long COLLISION_COOLDOWN = 10000; // D�lai de 10 secondes

    public PNJ_Susie(GamePanel gp) {
    	super(gp,"/img_npj/susie_pnj.png", 15);
        initializePosition('S'); // 'p' pour le point de spawn de Susie
        this.player = gp.getPlayer();
    }
    
    
    //M�thode commune aux PNJ, permet de voir dans l'inventaire du joueur 
    public void triggerDialog() {
        showInventory(); // Affiche l'inventaire lors de l'interaction
        if (System.currentTimeMillis() - getLastCollisionTime() > getCollisionCooldown()) {
            ArrayList<gameObject> inventory = this.player.getInv(); // Acc�s � l'inventaire du joueur
            boolean hasApple = false; // Assume que l'ID de la pomme est "v"
            
            for (gameObject item : inventory) { // Boucle pour v�rifier l'inventaire
                if (item.getID().equals("e")) {
                    hasApple = true; // La pomme est trouv�e
                    break;
                }
            }
            if (!hasApple) {
                JOptionPane.showMessageDialog(null, "Il te manque l'�p�e, reviens plus tard :(", "acc�s a l'inventaire de Susie", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Tu as enfin l'�p�e, tu peux passer a travers les collisions :)", "acc�s a l'inventaire de Susie", JOptionPane.INFORMATION_MESSAGE);
                initializeItemToGive(); // Initialise l'�p�e � donner
                addItemToPlayer(); // Ajoute l'item � l'inventaire du joueur
            }
            setLastCollisionTime(System.currentTimeMillis()); // R�initialiser le temps de la derni�re collision
        }
    }

    // M�thode pour ajouter un objet � l'inventaire du joueur
    private void addItemToPlayer() {
        player.getInv().add(this.itemToGive); // Ajoute l'�p�e � l'inventaire du joueur
    }

    private void initializeItemToGive() {
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/fantome.png"));
            this.itemToGive = new gameObject(gp, isCollisionWithPlayer); // Cr�ez une nouvelle instance 
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
