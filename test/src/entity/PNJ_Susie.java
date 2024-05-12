package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import objects.gameObject;
import test.GamePanel;


public class PNJ_Susie extends PNJ {
	private Player player; // Ajout d'un attribut Player (PERMET DE VOIR JUSTE L INVENTAIRE)
   
	public long lastCollisionTime = 0; // Temps de la dernière collision
    public static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes

    public PNJ_Susie(GamePanel gp, Player player) {
        super(gp, "/img_npj/susie_pnj.png", 150, 288, 5);
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
        if (System.currentTimeMillis() - lastCollisionTime > COLLISION_COOLDOWN) { // me permet de plus avoir de collision avec mon pnj pendant 10 secondes 
            ArrayList<gameObject> inventory = this.player.inv; //permet d'acceder a l'inventaire de mon joueur
            boolean hasItem1 = false; // l'id 1 est d'office a faux 
            
            for(gameObject item : inventory) { // boucle dans mon inventaire
                if (item.id.equals("1")) {
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
                addItemToInventory(gp.player); // Méthode pour ajouter un objet à l'inventaire du joueur
            }
            
            lastCollisionTime = System.currentTimeMillis(); // Réinitialiser le temps de la dernière collision
        }
    }
    
    private void initializeItemToGive() {
        this.itemToGive = new gameObject(false);
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
            itemToGive.image = itemImage;
            itemToGive.setID("3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
         
}
