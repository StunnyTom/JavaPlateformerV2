package objects;

import entity.Player;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Apple extends gameObject implements Usable {
	 public Apple() {
	        super(true); // La potion a une collision
	        this.nom = "Vie";
	        this.id = "v";
	        try {
	            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/apple.png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

	@Override
	public void use(Player player) {
		if (player.getLives() < player.getMaxLives()) { // si le joueur a moins de vie qu'au debut 
            player.gainLife(); // Ajoute une vie au joueur
        } else {
            System.out.println("Tu ne peux pas utiliser cet objet");
        }
    }
	
    @Override
    public boolean isConsumable() {
        return true; // La pomme est consommée après utilisation
    }
    
    @Override
    public String getDescription() {
    	return "Cette pomme te redonne de la vie";
    }

}