package objects;

import entity.Player;

public class Key extends gameObject implements Usable {
    public Key() {
        super("Cl�", "k", "/objects/cle.png", false);
    }
    
    @Override
    public void use(Player player) {
        System.out.println("Cl� utilis�e: " + id);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    public String getDescription() {
        return "Obtiens 8 cl�s pour finir le jeu";
    }

 
}
