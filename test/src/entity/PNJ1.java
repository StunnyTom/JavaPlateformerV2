package entity;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.Aimant;
import objects.Apple;
import objects.gameObject;
import test.GamePanel;

//Gère le coffre, appelé comme un pnj pour des raisons pratiques
public class PNJ1 extends PNJ {
    private ArrayList<gameObject> pnjInventory = new ArrayList<>();
    private long lastCollisionTime = 0; // Temps de la dernière collision
    public static final long COLLISION_COOLDOWN = 10000; // Délai de 10 secondes


    public PNJ1(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 15);
        initializePosition('C');
        this.pnjInventory.add(new Apple(gp));
        this.pnjInventory.add(new Aimant(gp));
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
    	
    	ArrayList<gameObject> pnjInventory = getPnjInv();

        // Vérifiez si l'inventaire du PNJ est vide
        if (pnjInventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "L'inventaire du PNJ est vide.");
            return;
        }

        // Affichez les objets du PNJ
        String[] pnjOptions = pnjInventory.stream().map(gameObject::getNom).toArray(String[]::new);
        JOptionPane.showMessageDialog(
            null,
            "Inventaire du PNJ :\n" + String.join("\n", pnjOptions),
            "Inventaire du PNJ",
            JOptionPane.INFORMATION_MESSAGE
        );
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

    //Méthode commune des PNJ
	@Override
	public void triggerDialog() {
		// TODO Auto-generated method stub
		this.showPNJInventoryConsole();
	}
}