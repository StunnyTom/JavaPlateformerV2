package objects;

import entity.Player;

public class Key extends gameObject implements Usable {
    public Key() {
        super("Clé", "k", "/objects/cle.png", false);
    }
    
    @Override
    public void use(Player player) {
        System.out.println("Clé utilisée: " + id);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    public String getDescription() {
        return "Obtiens 8 clés pour finir le jeu";
    }

 
}
