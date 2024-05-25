package entity;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import generation.Generateur;
import objects.Apple;
import objects.Key;
import objects.Potion;
import objects.Usable;
import objects.gameObject;
import test.GamePanel;
import test.KeyHandler;
import java.io.File;

public class Player extends Entity {    
    KeyHandler keyH;
    private int keyCount = 0;  // Nombre de clés
	private boolean hasPotionEffect = false;
	private long potionStartTime;
	private int lives = 3;
	private Image heartImage;  // Image pour les cœurs
	private  final int maxLives = 3;
	private boolean justPickedUpKey = false;  // Nouvelle variable pour gérer l'état de ramassage de clé
	private boolean collisionEnabled = true; //pour gerer la collison avec l'item 
	private Set<String> collectedKeys = new HashSet<>();  // Ensemble pour stocker les IDs des clés collectées
	
    //on dessine le joueur
    public Player(GamePanel gp) {
    	super(gp);
        this.keyH = null;
        setDefaultValues();
        getPlayerImage();
        loadHeartImage();  // Charger l'image du cœur
        
        setSolidAir(new Rectangle(16, -10, gp.tileSize, gp.tileSize));        
    }
    
    public void setkeyH(KeyHandler keyH) {
    	this.keyH = keyH;
    }

    private void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 2;
        setInv(new ArrayList<gameObject>());
    }
    
    // Charger l'image du cœur
    private void loadHeartImage() {
        try {
            heartImage = ImageIO.read(getClass().getResourceAsStream("/img_player/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter pour le nombre de vies
    public int getLives() {
        return lives;
    }

    public int getMaxLives() {
        return maxLives;
    }
    
    // Méthode pour gagner une vie
    public void gainLife() {
        if (lives < maxLives) {
            lives++;
            System.out.println("Vies restantes : " + lives);
        } else {
            System.out.println("Tu ne peux pas utiliser cet objet");
        }
    } 

    // Méthode pour réduire le nombre de vies
    public void loseLife() {
        if (lives > 0) {
            lives--;
            System.out.println("Vies restantes : " + lives);
            if (lives == 0) {
                gp.gameState.afficheGameOver();  // Terminer le jeu si le joueur n'a plus de vies
            }
        }
    }
   
    
 // Méthode pour gagner ou perdre de la vie
    public void setLives(int newLives) {
        this.lives = newLives;
        System.out.println("Vies ajustées à : " + lives);
        if (lives == 0) {
            this.getGamePanel().gameState.afficheGameOver();  // Gérer la fin du jeu ici
        }
    }
    
    public GamePanel getGamePanel() {
        return gp;
    }



  //pour ajouter les clés   
    public int getKeyCount() {
        return keyCount;
    }

    public void addKey(String keyId) {
        if (!collectedKeys.contains(keyId)) {  // Vérifier si la clé n'a pas déjà été collectée
            collectedKeys.add(keyId);
            keyCount++;  // Incrémenter le compteur seulement si la clé est nouvelle
            System.out.println("Nombre de clés: " + keyCount);
            if (keyCount >= 8) {
                System.out.println("Condition pour gagner vérifiée, toutes les clés collectées!");
                // Implémentez la logique pour gagner ou passer au niveau suivant
            }
        }
    }
    
    //l'impact de la potion sur le joueur
    public void setPotionEffect(boolean hasPotionEffect, long potionStartTime) {
        this.hasPotionEffect = hasPotionEffect;
        this.potionStartTime = potionStartTime;
    }

    
    // méthode pour gérer l'état des collisions
    public void setCollisionEnabled(boolean enabled) {
        this.collisionEnabled = enabled;
    }
  
    public void useItem(String itemId) {
        for (int i = 0; i < inv.size(); i++) {
            gameObject item = inv.get(i);
            if (item.getID().equals(itemId) && item instanceof Usable) {
                System.out.println("Utilisation de l'objet avec ID: " + itemId);
                Usable usableItem = (Usable) item;
                usableItem.use(this);

                if (usableItem.isConsumable() && (item instanceof Apple) && (getLives() < getMaxLives())) {
                    inv.remove(i);
                }
                break;
            }
        }
    }

   
    //les images pour le sprite
    private void getPlayerImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/img_player/position_neutre.png"));
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
        
        if (!collisionEnabled || !gp.verif.checkCollision(newX, newY, l, L, getSolidAir())) {
            setScreenY(newY);  // Se déplacer librement en Y
        } else {
            ySpeed = 0;  // Arrêter le mouvement si collision et collisions activées
        }

        // Mouvement sur l'axe X
        if (keyH.leftPressed) {
            newX -= speed;
        }
        if (keyH.rightPressed) {
            newX += speed;
        }

        if (!collisionEnabled || !gp.verif.checkCollision(newX, getScreenY(), l, L, getSolidAir())) {
            setScreenX(newX);  // Se déplacer librement en X
        }
        
        //utilisation de la potion sur le joueur
         if (hasPotionEffect && (System.currentTimeMillis() - potionStartTime > Potion.getPotionEffectDuration())) {
            hasPotionEffect = false; // Reset potion effect after duration
        }

        if (keyH.isUpPressed() && onGround) {
            if (hasPotionEffect) {
                ySpeed = Potion.getBoostedJumpSpeed(); // Boosted jump speed
            } else {
                ySpeed = -3; // 
            }
        }
        
        // Vérification des collisions avec des objets
        Generateur collOb = gp.verif.checkCollisionGen(newX, newY, l, L, getSolidAir());
        if (collOb!=null && !(collOb instanceof PNJ)) {
        	if (collOb instanceof Key) {
        		 ((Usable) collOb).use(this);  // Utiliser la clé qui appelle addKey
        	}else if (collOb.getID().equals("d")) {
            	loseLife();
               // gp.gameState.afficheGameOver(); // game over si il touche l'objet 
            }else {
            	addInv((gameObject) collOb);
            }
        	String key = collOb.getID();
            gp.genMap.remove(key);
            System.out.println(gp.Genlist);
            gp.Genlist.set(Character.getNumericValue(key.charAt(key.length()-1)), new Generateur(gp));
            //gp.Genlist.remove(Character.getNumericValue(key.charAt(key.length()-1))-1);
        } else if (collOb instanceof PNJ) {
        	PNJ pnj = (PNJ) collOb;
        	pnj.setCollisionWithPlayer(true);
        	pnj.triggerDialog();
        }
    
 // Réinitialiser justPickedUpKey si aucune clé n'est touchée dans cette mise à jour
    if (!justPickedUpKey || Math.abs(newX - getScreenX()) > gp.tileSize || Math.abs(newY - getScreenY()) > gp.tileSize) {
        justPickedUpKey = false;
    }
         
    }
/*
      
*/
        // Vérification des collisions avec les pnj
        /*
        if (gp.verif.checkCollisionPNJ(newX, newY, l, L, getSolidAir())) {
            //System.out.println("collision pnj");
        }

        // Gestion de l'animation sprite
        spriteCounter++;
        if (spriteCounter > 25) {
            spriteNum = spriteNum == 1 ? 2 : 1;
            spriteCounter = 0;
        }
        */
    
    public void draw(Graphics2D g2) {
    	super.draw(g2);

        // Dessiner le nombre de clés en haut à droite de l'écran
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Clés : " + collectedKeys.size(), gp.maxScreenCol * gp.tileSize - 250,30);
        // Dessiner les cœurs en haut à gauche de l'écran
        int heartX = 850; // Position X initiale pour les cœurs
        int heartY = 0; // Position Y initiale pour les cœurs
        int heartSpacing = 50; // Espacement entre les cœurs

        for (int i = 0; i < lives; i++) {
            g2.drawImage(heartImage, heartX + (i * heartSpacing), heartY, gp.tileSize, gp.tileSize, null);
        }
    }

}