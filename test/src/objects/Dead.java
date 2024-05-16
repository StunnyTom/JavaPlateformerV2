package objects;

import entity.Player;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dead extends gameObject implements Usable {
	 public Dead() {
	        super(true); // La potion a une collision
	        this.nom = "Mort";
	        this.id = "d";
	        try {
	            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/eclair.png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public boolean isConsumable() {
        return true; // La potion est consommée après utilisation
    }
    
    @Override
    public String getDescription() {
    	return "Fin du jeu";
    }

}