package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import objects.gameObject;
import test.GamePanel;

public class Monster extends Entity {
	
	GamePanel gp;
	public String sprite;
	
	
	public Monster(GamePanel gp) {
		//super();
		
        this.gp = gp;

        setDefaultValues();
        this.sprite = "/mobs/bomb.png";
        
        File nameMap = new File(gp.currentMap);
        Point x;
		try {
			x = Entity.findSpawnPoints('b', nameMap.getName())[0];
			screenX = (int) (gp.tileSize * x.getY());
	        screenY = (int) (gp.tileSize * x.getX());
	        super.neutre1 = ImageIO.read(getClass().getResourceAsStream("/mobs/bomb.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        solidAir = new Rectangle(16, -10, gp.tileSize, gp.tileSize);
        
    }
	
	public void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 1;
        direction = "neutre";
        inv = new ArrayList<gameObject>();
    }
	
	public void update() {
        int newX = screenX+1, newY = screenY;

        // Appliquer la gravit�
        ySpeed += GRAVITY;
        newY += ySpeed;
        //boolean onGround = gp.verif.checkCollision(screenX, screenY + 1, l, L, solidAir); // V�rifier les collisions avec le sol
        
        // Collision check pour l'axe Y
        if (!gp.verif.checkCollision(screenX, newY, l, L, solidAir)) {
            screenY = newY;
        } else {
            ySpeed = 0; // Arr�ter le mouvement si collision
        }


        if (!gp.verif.checkCollision(newX, screenY, l, L, solidAir)) { // Collision check pour l'axe X
            screenX = newX;
        }
        
        // V�rification des collisions avec des objets
        gameObject collOb = gp.verif.checkCollisionObject(newX, newY, l, L, solidAir);
        if (!collOb.nullObj()) {
            addInv(collOb); // Ramasser l'objet en cas de collision
            gp.ObjectM.Objet_Map.remove(collOb.getID());
            
            Iterator<gameObject> li = inv.iterator();
            
            while (li.hasNext())
                System.out.println(li.next());
            
        }
        
        

        // Gestion de l'animation sprite
        spriteCounter++;
        if (spriteCounter > 25) {
            spriteNum = spriteNum == 1 ? 2 : 1;
            spriteCounter = 0;
        }

    }
	
	public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "neutre":
                if (spriteNum == 1) {
                    image = neutre1;
                } else if (spriteNum == 2) {
                    image = neutre1;
                }
                break;
        }
        
        // Dessine l'image sur le rectangle bleu
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }  
	
}
