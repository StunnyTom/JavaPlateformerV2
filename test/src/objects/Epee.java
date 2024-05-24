package objects;

import entity.Player;

public class Epee extends gameObject implements Usable {

    public Epee() {
        super("Epée", "e", "/objects/epe.png", true);
    }

    @Override
    public void use(Player player) {
        // Implémentation spécifique de l'utilisation de l'épée
    }
    
    @Override
    public boolean isConsumable() {
        return false; // L'épée n'est pas consommée après utilisation
    }
    
 
}
