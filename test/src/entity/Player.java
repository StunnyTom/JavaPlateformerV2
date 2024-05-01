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
        
    }

    public void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 1;
        direction = "neutre";
    }

    //les images pour le sprite
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

        boolean onGround = gp.verif.checkCollision(screenX, screenY + 1, l, L, solidAir); // Vérifier les collisions avec le sol
      
        if (keyH.upPressed && onGround) {   // Sauter seulement si le personnage est au sol
            ySpeed = -2; // Valeur de saut
        }
/*
        // Mouvement vers le bas 
        if (keyH.downPressed) {
            newY += speed; // Déplacement vers le bas
        }
*/
        // Collision check pour l'axe Y
        if (!gp.verif.checkCollision(screenX, newY, l, L, solidAir)) {
            screenY = newY;
        } else {
            ySpeed = 0; // Arrêter le mouvement si collision
        }

        // Mouvement sur l'axe X
        if (keyH.leftPressed) {
            newX -= speed;
        }
        if (keyH.rightPressed) {
            newX += speed;
        }

        if (!gp.verif.checkCollision(newX, screenY, l, L, solidAir)) { // Collision check pour l'axe X
            screenX = newX;
        }
        
        // Vérification des collisions avec des objets
        if (gp.verif.checkCollisionObject(newX, newY, l, L, solidAir)) {
            pickupObjet(); // Ramasser l'objet en cas de collision
        }
        
        // Vérification des collisions avec les pnj
        if (gp.verif.checkCollisionPNJ(newX, newY, l, L, solidAir)) {
            //System.out.println("collision pnj");
        }
        

        // Gestion de l'animation sprite
        spriteCounter++;
        if (spriteCounter > 25) {
            spriteNum = spriteNum == 1 ? 2 : 1;
            spriteCounter = 0;
        }

    }

    public void pickupObjet() {
    	System.out.println("objet bientot ramassé");
    	//on pourra le mettre a l inventaire
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