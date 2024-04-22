package objects;

import javax.imageio.ImageIO;

public class Obj_key extends ParentsObjects{
	
	public Obj_key() {
		name = "key";
	
		try { 
			//charger l'image
			image = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
