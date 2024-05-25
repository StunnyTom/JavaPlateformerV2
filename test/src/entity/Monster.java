package entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.gameObject;
import test.GamePanel;

public class Monster extends Entity {
	public Monster(GamePanel gp) {
		
        super(gp);
        setDefaultValues();
        
        File nameMap = new File(gp.currentMap);
        Point x;
		try {
			x = Entity.findSpawnPoints('b', nameMap.getName())[0];
			setScreenX((int) (gp.tileSize * x.getY()));
	        setScreenY((int) (gp.tileSize * x.getX()));
	        this.setImage(ImageIO.read(getClass().getResourceAsStream("/mobs/bomb.png"))); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setSolidAir(new Rectangle(16, -10, gp.tileSize, gp.tileSize));
    }
	
	public void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 1;
        setInv(new ArrayList<gameObject>());
    }
	
	public void update() {
	    int newX = getScreenX() + 1, newY = getScreenY();

	    // Appliquer la gravit�
	    ySpeed += GRAVITY;
	    newY += ySpeed;

	    // Collision check pour l'axe Y
	    if (!gp.verif.checkCollision(getScreenX(), newY, l, L, getSolidAir())) {
	        setScreenY(newY);
	    } else {
	        ySpeed = 0; // Arr�ter le mouvement si collision
	    }

	    // Collision check pour l'axe X
	    if (!gp.verif.checkCollision(newX, getScreenX(), l, L, getSolidAir())) {
	        setScreenX(newX);
	    }

	    /*
	    // V�rification des collisions avec des objets
	    gameObject collOb = gp.verif.checkCollisionObject(newX, newY, l, L, getSolidAir());
	    if (!collOb.nullObj()) {
	        addInv(collOb); // Ramasser l'objet en cas de collision

	        // Suppression de l'objet de la carte
	        Map<String, gameObject> objectMap = gp.getObjectM().Objet_Map;

	        String objectId = collOb.getId();
	        
	        if (objectMap.containsKey(objectId)) {
	            objectMap.remove(objectId);
	        }
	        
	        Iterator<gameObject> li = getInv().iterator();
	        
	        while (li.hasNext()) {
	            System.out.println(li.next());
	        }
	    }
*/
	    // Gestion de l'animation sprite
	    spriteCounter++;
	    if (spriteCounter > 25) {
	        spriteNum = spriteNum == 1 ? 2 : 1;
	        spriteCounter = 0;
	    }
	}

	
	
}