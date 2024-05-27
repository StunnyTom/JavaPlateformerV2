package entity;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import generation.Generateur;
import objects.Aimant;
import objects.Apple;
import objects.Dead;
import objects.Etoile;
import objects.Fantome_Collision;
import objects.Key;
import objects.Pistolet;
import objects.Potion;
import objects.Rencontre;
import objects.Usable;
import objects.gameObject;
import test.GamePanel;
import test.KeyHandler;

//Classe du joueur 
public class Player extends Entity { 
	
	//Attribut pour la gestion de touches
    KeyHandler keyH;
    
    //Attributs liés aux objets
    private int keyCount = 0;  // Nombre de clés
	private boolean hasPotionEffect = false;
	private long potionStartTime;
	private Image heartImage;  // Image pour les cœurs
	private boolean justPickedUpKey = false;  // Variable pour gérer l'état de ramassage de clé
	private boolean collisionEnabled = true; //pour gerer la collison avec l'item 
	private Set<String> collectedKeys = new HashSet<>();  // Ensemble pour stocker les IDs des clés collectées
	
	 private boolean isCollisionWithPNJ = false;
	 private PNJ lastInteractedPnj; // Attribut pour suivre le dernier PNJ avec lequel le joueur a interagi
	  
	
	// Dessiner les cœurs en haut à gauche de l'écran
    private int lives = 3; // Nombre de vies du joueur
    private  final int maxLives = 3;
    int heartX = 850; // Position X initiale pour les cœurs
    int heartY = 0; // Position Y initiale pour les cœurs
    int heartSpacing = 50; // Espacement entre les cœurs
    
  //item invincible 
    private boolean isInvincible = false;
    private long invincibilityStartTime;
    private static final long INVINCIBILITY_DURATION = 3000; // 3 secondes

	private static final int NUM_SQUARES = 5;

    private boolean isAttacking = false; // Nouvel état pour gérer l'attaque
    private Set<String> pnjInteractions = new HashSet<>();
    
    
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

    
    public PNJ getLastInteractedPnj() {
        return lastInteractedPnj;
    }

    public void setLastInteractedPnj(PNJ pnj) {
        this.lastInteractedPnj = pnj;
    }

    public void interactWithPNJ(PNJ pnj) {
        setCollisionWithPNJ(true);
        setLastInteractedPnj(pnj);
        addPnjInteraction(pnj.getID());
    }
 
    //Méthode qui met des valeurs par défaut au joueur
    private void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 2;
        setInv(new ArrayList<gameObject>());
        
        this.inv.add(new Rencontre(gp));
        this.inv.add(new Etoile(gp));
        this.inv.add(new Dead(gp));
        this.inv.add(new Pistolet(gp));
        this.inv.add(new Aimant(gp));
        
    }
    
 // Méthode pour ajouter une interaction
    public void addPnjInteraction(String pnjId) {
        pnjInteractions.add(pnjId);
    }

    // Méthode pour vérifier si des interactions ont eu lieu
    public boolean hasPnjInteractions() {
        return !pnjInteractions.isEmpty();
    }


       /*
            this.inv.add(new Apple(gp));
            this.inv.add(new Fantome_Collision(gp));
            this.inv.add(new Dead(gp));
        /* POUR DIRECT AVOIR LES 7 CLé
        for (int i = 0; i < 7; i++) {
            collectedKeys.add("Key" + i);  // Ajouter des identifiants de clés fictifs
        }
        */
   
    
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
  

    public boolean isCollisionWithPNJ() {
        return isCollisionWithPNJ;
    }

    public void setCollisionWithPNJ(boolean collisionWithPNJ) {
        isCollisionWithPNJ = collisionWithPNJ;
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

  //pour récupérer le nombre de clés   
    public int getKeyCount() {
        return keyCount;
    }

    //ajout de la key
    public void addKey(String keyId) {
        if (!collectedKeys.contains(keyId)) {  // Vérifier si la clé n'a pas déjà été collectée
            collectedKeys.add(keyId);
            keyCount++;  // Incrémenter le compteur seulement si la clé est nouvelle
            System.out.println("Nombre de clés: " + keyCount);
            if (keyCount == 8) {
                System.out.println(" toutes les clés collectées!");
                gp.gameState.afficheVictory();  // Déclencher l'état de victoire
            
            }
        }
    }
    
    // Méthode pour reculer le joueur
    public void moveBackwards(int steps) {
        int newX = getScreenX() - steps * (-1) * speed;  // Calculer la nouvelle position en X
        // Vérifier si la nouvelle position est libre de collision
        if (!gp.verif.checkCollision(newX, getScreenY(), l, L, getSolidAir())) {
            setScreenX(newX);  // Appliquer la nouvelle position si elle ne cause pas de collision
        } else {
            System.out.println("Mouvement en arrière bloqué par une collision");
        }
    }
   
    //Pour utiliser l'item
    public void useItem(String itemId) {
    	
    	
        for (int i = 0; i < inv.size(); i++) {
            gameObject item = inv.get(i);
            System.out.println(item.getID() + " = " + itemId + "\nUsable ? "+ (item instanceof Usable));
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
    
    
    
    //l'impact de la potion sur le joueur
    public void setPotionEffect(boolean hasPotionEffect, long potionStartTime) {
        this.hasPotionEffect = hasPotionEffect;
        this.potionStartTime = potionStartTime;
    }

    
    // méthode pour gérer l'état des collisions
    public void setCollisionEnabled(boolean enabled) {
        this.collisionEnabled = enabled;
    }
    
    //item invincible 
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public void setInvincibilityStartTime(long startTime) {
        this.invincibilityStartTime = startTime;
    }

    public boolean isInvincible() {
        return isInvincible && (System.currentTimeMillis() - invincibilityStartTime < INVINCIBILITY_DURATION);
    }

   
    //les images pour le sprite
    private void getPlayerImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/img_player/position_neutre.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isAttacking() {
        return this.isAttacking;
    }

    /*
     * 
     * 
     * 
     * 
    

    // Vérification des collisions avec les différentes entités
    Generateur collOb = gp.verif.checkCollisionGen(newX, newY, l, L, getSolidAir());
    if (collOb != null && collOb instanceof gameObject) {
        gameObject obj = (gameObject) collOb;
        
        if (inv.size() < NUM_SQUARES) { // Assurez-vous que NUM_SQUARES est défini comme la taille maximale de l'inventaire
            addInv(obj);
            String key = collOb.getID();
            gp.genMap.remove(key);
            gp.Genlist.set(Character.getNumericValue(key.charAt(key.length()-1)), new Generateur(gp));
        } else {
            System.out.println("Inventaire plein, impossible de ramasser : " + obj.getID());
            // Ne retirez pas l'objet du jeu
        }
    } else if (collOb instanceof PNJ) {
        // Gérer la collision avec PNJ ici ...
    } 
    // Code pour gérer d'autres types de collisions ...
}

     */
    public void update() {
        if (gp.gameState.isGameOver()) {
            return;
        }
        int newX = getScreenX(), newY = getScreenY();
        ySpeed += GRAVITY;
        newY += ySpeed;
        boolean onGround = gp.verif.checkCollision(getScreenX(), getScreenY() + 1, l, L, getSolidAir());

        if (keyH.upPressed && onGround) {
            ySpeed = -3;
        }

        if (!gp.verif.checkCollision(getScreenX(), newY, l, L, getSolidAir())) {
            setScreenY(newY);
        } else {
            ySpeed = 0;
        }

        if (!collisionEnabled || !gp.verif.checkCollision(newX, newY, l, L, getSolidAir())) {
            setScreenY(newY);
        } else {
            ySpeed = 0;
        }

        if (keyH.leftPressed) {
            newX -= speed;
        }
        if (keyH.rightPressed) {
            newX += speed;
        }

        if (!collisionEnabled || !gp.verif.checkCollision(newX, getScreenY(), l, L, getSolidAir())) {
            setScreenX(newX);
        }

        if (keyH.isUpPressed() && onGround) {
            if (hasPotionEffect) {
                ySpeed = Potion.getBoostedJumpSpeed();
            } else {
                ySpeed = -3;
            }
        }
        
        if (hasPotionEffect && (System.currentTimeMillis() - potionStartTime > Potion.getPotionEffectDuration())) {
            hasPotionEffect = false;
        }
        
        if (isInvincible && System.currentTimeMillis() - invincibilityStartTime > INVINCIBILITY_DURATION) {
            isInvincible = false;
            System.out.println("Tu n'es plus invincible.");
        }
        
        Generateur collOb = gp.verif.checkCollisionGen(newX, newY, l, L, getSolidAir());
        if (collOb != null && collOb instanceof gameObject) {
            gameObject obj = (gameObject) collOb;
            
            if (inv.size() < NUM_SQUARES) {
                addInv(obj);
                String key = collOb.getID();
                gp.genMap.remove(key);
                gp.Genlist.set(Character.getNumericValue(key.charAt(key.length()-1)), new Generateur(gp));
            } else {
                System.out.println("Inventaire plein, impossible de ramasser : " + obj.getID());
            }
            
            if (collOb instanceof Key) {
                ((Usable) collOb).use(this);
            } else if (collOb.getID().equals("d")) {
                loseLife();
            } else {
                addInv((gameObject) collOb);
            }
            String key = collOb.getID();
            gp.genMap.remove(key);
            gp.Genlist.set(Character.getNumericValue(key.charAt(key.length() - 1)), new Generateur(gp));
        } else if (collOb instanceof PNJ) {
            PNJ pnj = (PNJ) collOb;
            pnj.setCollisionWithPlayer(true);
            pnj.triggerDialog();
            setCollisionWithPNJ(true); // Ajoutez cette ligne pour indiquer qu'il y a eu une interaction avec un PNJ
            moveBackwards(5);
        } else if (collOb instanceof Monster) {
            if (!gp.getPlayer().isInvincible()) {
                Monster mon = (Monster) collOb;
                mon.checkPlayerInteraction();
                moveBackwards(5);
            }

            if (collOb != null && (collOb instanceof gameObject)) {
                gameObject obj = (gameObject) collOb;
                if (inv.size() < NUM_SQUARES) {
                    addInv(obj);
                    String key = collOb.getID();
                    gp.genMap.remove(key);
                    gp.Genlist.set(Character.getNumericValue(key.charAt(key.length() - 1)), new Generateur(gp));
                } else {
                    System.out.println("Inventaire plein, impossible de ramasser : " + obj.getID());
                }
            }
        }
 // Réinitialiser justPickedUpKey si aucune clé n'est touchée dans cette mise à jour
    if (!justPickedUpKey || Math.abs(newX - getScreenX()) > gp.tileSize || Math.abs(newY - getScreenY()) > gp.tileSize) {
        justPickedUpKey = false;}      
    }
  
    //Gère l'affichage du joueur
	public void draw(Graphics2D g2) {
		super.draw(g2);
		
		 // On dessine les images
	     g2.setFont(new Font("Arial", Font.PLAIN, 20));
	        g2.setColor(Color.WHITE);
	        g2.drawString("Clés : " + collectedKeys.size(), gp.maxScreenCol * gp.tileSize - 250,30);

	        
	    for (int i = 0; i < lives; i++) {
	        g2.drawImage(heartImage, heartX + (i * heartSpacing), heartY, gp.tileSize, gp.tileSize, null);
	    }
	}
}