package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import test.GamePanel;
import test.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    // ou nous dessinons le joueur 
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        
        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;
        
        solidAir = new Rectangle(16, -10, gp.tileSize, gp.tileSize);
        
        //collision des objets 
        solidAirDefaultX = solidAir.x;
        solidAirDefaultY = solidAir.y;
       
    }

    public void setDefaultValues() {
        screenX = gp.tileSize *0; //position du joueur sur la carte 10
        screenY = gp.tileSize * 2; //100
        l = 20;
        L = 25;
        speed = 1;
        direction = "neutre";
    }

    public void getPlayerImage() {
        try {
            neutre1 = ImageIO.read(getClass().getResourceAsStream("/img_player/position_neutre.png"));
            neutre2 = ImageIO.read(getClass().getResourceAsStream("/img_player/position_neutre.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/img_player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/img_player/right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/img_player/position_neutre.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void update() {
        int newX = screenX, newY = screenY;
    
        // Appliquer la gravité
        ySpeed += GRAVITY;
        newY += ySpeed;
        
        boolean onG = !gp.verif.checkCollision(screenX, screenY+1, l, L, solidAir);

		if (keyH.upPressed && !onG) {
            //if (!gp.verif.checkCollision(screenX, newY, l, L, solidAir)) {
                ySpeed = -2; 
                
             // Vérifier la collision avec les objets ici
                int objIndex = gp.verif.checkObject(this, true);
                if (objIndex != 999) {
                    // Logique de traitement de la collision avec l'objet
                    System.out.println("Collision avec l'objet à l'index : " + objIndex);
                 
                }

            //}
        }

        // Collision check pour Y
        if (!gp.verif.checkCollision(screenX, newY, l, L, solidAir)) {
            screenY = newY;
        } else {
            // Si collision avec le sol, arrêter le saut
            ySpeed = 0;
        }

        if (keyH.leftPressed) newX -= speed;
        if (keyH.rightPressed) newX += speed;

        // Collision check for X and Y independently
        if (!gp.verif.checkCollision(newX, screenY, l,L, solidAir)) {
            screenX = newX;
        }
        //if (!gp.verif.checkCollision(screenX, newY, l,L, solidAir)) {
        //    screenY = newY;
        //}
	    
	 
	        // Gestion de l'animation sprite
	       spriteCounter++;
	        if(spriteCounter > 25) {
	            spriteNum = spriteNum == 1 ? 2 : 1;
	        	spriteCounter = 0;
	        } 
    }
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
			case"neutre":
				if(spriteNum == 1) {
					image = neutre1;
				}
				if (spriteNum == 2) {
					image = neutre2;
				}
				break;
				
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			
				break;
			case"left":
				if(spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
		}
				
		//on dessine les images 
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}

}