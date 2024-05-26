package entity;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.Apple;
import objects.Dead;
import objects.Fantome_Collision;
import objects.gameObject;
import test.GamePanel;

//Classe abstraite pour g�rer les interactions et attributs communs des PNJ
public abstract class PNJ extends Entity {
    String direction;
    int screenX, screenY;
    int padding; // Ajout du padding comme attribut de la classe
    protected gameObject itemToGive; // Objet � donner utilis� dans les sous-classes
    protected ArrayList<gameObject> inv; // Inventaire des objets
    protected ArrayList<gameObject> pnjInventory;
    protected boolean isCollisionWithPlayer = false; // Flag de collision avec le joueur
    @SuppressWarnings("unused")
	private boolean isVisible = true; // Contr�le la visibilit� du PNJ
    @SuppressWarnings("unused")
	private boolean isCollidable = true; // Default collidability

    public PNJ(GamePanel gp, String imagePath, int padding) {
        super(gp);
        this.direction = "neutre"; // Valeur par d�faut de la direction
        this.padding = padding; // Initialisation du padding
        this.inv = new ArrayList<>();  // Initialiser l'inventaire
        this.pnjInventory = new ArrayList<>(); // Initialisation de l'inventaire du PNJ

        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                throw new RuntimeException("Pas acc�s au lieu du pnj: " + imagePath);
            }
            this.setImage(ImageIO.read(is));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        initializeInventory(); 
    }
    
    //M�thode commune d'interaction avec les PNJ
    public abstract void triggerDialog();
    
    //inventaire par default
    private void initializeInventory() {
        this.inv.add(new Apple(gp));
        this.inv.add(new Fantome_Collision(gp));
        this.inv.add(new Dead(gp));
    }
    
    public gameObject getStolenItem() {
        if (itemToGive != null) {
            gameObject temp = itemToGive;
            itemToGive = null;  // S'assurer que l'item ne peut �tre vol� qu'une fois.
            return temp;
        }
        return null;
    }
    
    //ajouter un objet dans l'inventaire
    public void AddItemToPlayer(Player player) {
        if (itemToGive != null && itemToGive.getImage() != null) {
            if (!player.getInv().contains(itemToGive)) {
                player.getInv().add(itemToGive);
                System.out.println("Objet ajout� � l'inventaire : " + itemToGive.getID());
                itemToGive = null;  // Assurez-vous que l'objet ne peut �tre donn� qu'une fois
            } else {
                System.out.println("L'objet existe d�j� dans l'inventaire.");
            }
        } else {
            System.out.println("Aucun objet � donner ou image manquante.");
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
    
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    
    public void showInventory() {
        if (inv.isEmpty()) {
            System.out.println("L'inventaire de " + this.getClass().getSimpleName() + " est vide.");
        } else {
            System.out.println("Inventaire de " + this.getClass().getSimpleName() + ":");
            for (gameObject item : inv) {
                System.out.println(item.getClass().getSimpleName() + " - ID: " + item.getID());
            }
        }
    }
    public void interact() {
    	
        setVisible(false); // Rendre le PNJ invisible lors de l'interaction
        triggerDialog(); // D�clenche le dialogue si n�cessaire
       
    }

	public boolean isCollisionWithPlayer() {
		return isCollisionWithPlayer;
	}

	public void setCollisionWithPlayer(boolean isCollisionWithPlayer) {
		this.isCollisionWithPlayer = isCollisionWithPlayer;
	}
}
