package objects;

import javax.imageio.ImageIO;

public class Obj_Chest extends ParentsObjects{
	
		public Obj_Chest() {
			name = "coffre";
		
			try { //charger l'image
				image = ImageIO.read(getClass().getResourceAsStream("/objects/coffre_test.png"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			collision = true;
		}
	}
