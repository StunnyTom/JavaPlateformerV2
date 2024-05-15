package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.gameObject;
import test.GamePanel;
import test.KeyHandler;
import java.io.File;


public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private int keyCount = 0;  // Nombre de clés
    
    // ou nous dessinons le joueur 
    public Player(GamePanel gp) {
        this.gp = gp;
        this.keyH = null;
        setDefaultValues();
        getPlayerImage();
        
        
        File nameMap = new File(gp.currentMap);
        Point x;
		try {
			x = Entity.findSpawnPoints('z', nameMap.getName())[0];
			setScreenX((int) (gp.tileSize * x.getY()));
	        setScreenY((int) (gp.tileSize * x.getX()));
	
	        System.out.println(getScreenX());
	        System.out.println(getScreenY());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setSolidAir(new Rectangle(16, -10, gp.tileSize, gp.tileSize));        
    }
    
  //pour ajouter les clés   
    public int getKeyCount() {
        return keyCount;
    }

    public void addKey() {
        keyCount++;
        if (keyCount >= 3) {
            gp.gameState.afficheVictory();  // Trigger the victory condition
        }
    }
    public void setkeyH(KeyHandler keyH) {
    	this.keyH = keyH;
    }

    private void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 2;
        direction = "neutre";
        setInv(new ArrayList<gameObject>());
    }
    

    //les images pour le sprite
    private void getPlayerImage() {
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
        int newX = getScreenX(), newY = getScreenY();

        // Appliquer la gravité
        ySpeed += GRAVITY;
        newY += ySpeed;
        boolean onGround = gp.verif.checkCollision(getScreenX(), getScreenY() + 1, l, L, getSolidAir()); // Vérifier les collisions avec le sol
        if (keyH.upPressed && onGround) {   // Sauter seulement si le personnage est au sol
            ySpeed = -3; // Valeur de saut
        }

        // Collision check pour l'axe Y
        if (!gp.verif.checkCollision(getScreenX(), newY, l, L, getSolidAir())) {
            setScreenY(newY);
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

        if (!gp.verif.checkCollision(newX, getScreenY(), l, L, getSolidAir())) { // Collision check pour l'axe X
            setScreenX(newX);
        }

        // Vérification des collisions avec des objets
        gameObject collOb = gp.verif.checkCollisionObject(newX, newY, l, L, getSolidAir());
        if (!collOb.nullObj()) {
            if (collOb.getID().equals("k") || collOb.getID().equals("y")) {
                addKey(); // Incrémente le nombre de clés
            }
            gp.getObjectM().Objet_Map.remove(collOb.getID());
        }

        // Vérification des collisions avec les pnj
        if (gp.verif.checkCollisionPNJ(newX, newY, l, L, getSolidAir())) {
            //System.out.println("collision pnj");
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
                }
                if (spriteNum == 2) {
                    image = neutre2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
        }

        // On dessine les images
        g2.drawImage(image, getScreenX(), getScreenY(), gp.tileSize, gp.tileSize, null);

        // Dessiner le nombre de clés en haut à droite de l'écran
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Clés : " + getKeyCount(), gp.maxScreenCol * gp.tileSize - 100, 30);
    }


}