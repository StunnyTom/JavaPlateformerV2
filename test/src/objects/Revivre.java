package objects;

import entity.Player;
import test.GamePanel;

public class Revivre extends gameObject implements Usable {
    // Constructeur pour l'item "Revivre"
    public Revivre(GamePanel gp) {
        super(gp, "Revivre", "r", "/objects/fee.png", true);
    }

    @Override
    public void use(Player player) {
        // Lorsque l'item "Revivre" est utilisé, aucune action n'est effectuée.
        // Ce comportement est intentionnel pour cet item.
    }

    @Override
    public boolean isConsumable() {
        // L'item "Revivre" n'est pas consommable.
        return false;
    }
}
