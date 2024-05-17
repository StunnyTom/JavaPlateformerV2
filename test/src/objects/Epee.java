package objects;

import entity.Player;

public class Epee extends gameObject implements Usable {

    public Epee() {
        super("Ep�e", "e", "/objects/epe.png", true);
    }

    @Override
    public void use(Player player) {
        // Impl�mentation sp�cifique de l'utilisation de l'�p�e
    }
    
    @Override
    public boolean isConsumable() {
        return false; // L'�p�e n'est pas consomm�e apr�s utilisation
    }
    
 
}
