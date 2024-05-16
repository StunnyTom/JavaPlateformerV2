package objects;

import entity.Player;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Epe extends gameObject implements Usable {
	 public Epe() {
	        super(true); // La potion a une collision
	        this.nom = "Potion";
	        this.id = "e";
	        try {
	            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/epe.png"));
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
        return false; // La potion est consommée après utilisation
    }
    
    @Override
    public String getDescription() {
    	return "te permet d'attaquer";
    }

}