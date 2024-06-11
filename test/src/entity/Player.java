package entity;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import generation.Generateur;
import objects.ItemA;
import objects.ItemB;
import objects.ItemE;
import objects.Key;
import objects.Potion;
import objects.Revivre;
import objects.Usable;
import objects.gameObject;
import test.GamePanel;
import test.KeyHandler;

//Classe du joueur 
public class Player extends Entity { 
	
	//Attribut pour la gestion de touches
    KeyHandler keyH;
    
    private int initialX, initialY; // Coordonnées initiales pour la téléportation

    
    //Attributs liés aux objets
    private int keyCount = 0;  // Nombre de clés
	private boolean hasPotionEffect = false;
	private long potionStartTime;
	private Image heartImage;  // Image pour les cœurs
	private boolean justPickedUpKey = false;  // Variable pour gérer l'état de ramassage de clé
	private boolean collisionEnabled = true; //pour gerer la collison avec l'item 
	private Set<String> collectedKeys = new HashSet<>();  // Ensemble pour stocker les IDs des clés collectées
	
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
    private boolean isAttacking = false; // Nouvel état pour gérer l'attaque
    
    
    public ArrayList<PNJ> lastPNJs=new ArrayList<>();
    // Nouveau champ pour suivre si "Revivre" a déjà été utilisé
    private boolean revivreUsed = false;
    
    //on dessine le joueur
    public Player(GamePanel gp) {
    	super(gp);
        this.keyH = null;
        setDefaultValues();
        getPlayerImage();
        loadHeartImage();  // Charger l'image du cœur
        
    
        initialX = 1;  // Position initiale X - Adaptez selon votre grille de jeu
        initialY = 0;  // Position initiale Y - Adaptez selon votre grille de jeu
        setScreenX(initialX * gp.tileSize);  // Convertit la position de grille en pixels
        setScreenY(initialY * gp.tileSize);  // Convertit la position de grille en pixels
        
        setSolidAir(new Rectangle(16, -10, gp.tileSize, gp.tileSize));        
    }
    
    public void setkeyH(KeyHandler keyH) {
    	this.keyH = keyH;
    }

    //Méthode qui met des valeurs par défaut au joueur
    private void setDefaultValues() {
        l = 20;
        L = 25;
        speed = 2;
        setInv(new ArrayList<gameObject>());
        
        //remplir l'inventaire
        this.addInv(new ItemA(gp));
        this.addInv(new ItemE(gp));
        this.addInv(new ItemB(gp));
        this.addInv(new Revivre(gp));
        
        /* POUR DIRECT AVOIR LES 7 CLé
        for (int i = 0; i < 7; i++) {
            collectedKeys.add("Key" + i);  // Ajouter des identifiants de clés fictifs
        }
        */
    }
    //inventaire 
    public boolean hasItem(String itemID) {
        for (gameObject item : inv) {
            if (item.getID().equals(itemID)) {
                //System.out.println("Item trouvé: " + itemID);
                return true;
            }
        }
        return false;
    }
    
    public void checkAndUseRevivre() {
        // Ensure that we're checking and removing the correct item ID
        if (hasItem("r") && !revivreUsed) {  // Check if the item is present and not already used
            System.out.println("Utilisation de Revivre pour éviter le game over.");
            setLives(maxLives); // Restore lives to maximum
            removeInv("r"); // Remove the item with the correct ID from inventory
            JOptionPane.showMessageDialog(null, "Revivre utilisé! Vous continuez avec " + maxLives + " vies.");
            revivreUsed = true; // Mark Revivre as used to prevent further use
        }
    }



    public void removeInv(String itemID) {
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i).getID().equals(itemID)) {
                inv.remove(i);
                break;
            }
        }
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
    
    public ArrayList<PNJ> getLastPNJs() {
    	return this.lastPNJs;
    }
    
    
    //fonction pour teleporter le joueur 
    public void teleportToInitialPosition() {
        setScreenX(initialX * gp.tileSize);
        setScreenY(initialY * gp.tileSize);
        System.out.println("Téléportation");
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
        lives--;
        if (lives <= 0) {
            gp.gameState.setGameOver(true);  // Déclencher la vérification de game over dans GameState
        } else {
            System.out.println("Vies restantes : " + lives);
        }
    }

   
 // Méthode pour gagner ou perdre de la vie
    public void setLives(int newLives) {
        this.lives = newLives;
        System.out.println("Vies ajustées à : " + lives);
        // Dessine à nouveau les cœurs sur l'interface utilisateur
        redrawHearts();
        if (lives == 0) {
            this.getGamePanel().gameState.afficheGameOver();  // Gérer la fin du jeu ici
        }
    }
    
 // Méthode pour redessiner les cœurs sur l'interface utilisateur
    public void redrawHearts() {
        Graphics2D g2 = (Graphics2D) this.getGamePanel().getGraphics(); // Obtenez les graphiques du panel de jeu
        g2.setColor(Color.BLACK); // Choisissez une couleur pour effacer les vieux cœurs
        g2.fillRect(heartX, heartY, heartSpacing * maxLives, gp.tileSize); // Efface l'ancienne ligne de cœurs
        for (int i = 0; i < lives; i++) {
            g2.drawImage(heartImage, heartX + (i * heartSpacing), heartY, gp.tileSize, gp.tileSize, null); // Redessiner les cœurs
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
            //System.out.println("Mouvement en arrière bloqué par une collision");
        }
    }
   
    //Pour utiliser l'item
    public void useItem(String itemId) {
        for (int i = 0; i < inv.size(); i++) {
            gameObject item = inv.get(i);
            if (item.getID().equals(itemId) && item instanceof Usable) {
                Usable usableItem = (Usable) item;
                usableItem.use(this);
                if (usableItem.isConsumable()) {
                    inv.remove(i); // Retirer l'item de l'inventaire après usage
                    break;
                }
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

    
    public void update() {
    	if (gp.getGenlist().size()==1) {
    		//System.out.println("oui");
    		//gp.getTileM().loadSpawnMap(gp.currentMap);
    	}
    	
    	 if (gp.gameState.isGameOver()) {
    	        return; // Ne rien faire si le jeu est terminé
    	    }
        int newX = getScreenX(), newY = getScreenY();
        ySpeed += GRAVITY; // Appliquer la gravité
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

        if (keyH.isUpPressed() && onGround) {
            if (hasPotionEffect) {
                ySpeed = Potion.getBoostedJumpSpeed(); // Boosted jump speed
            } else {
                ySpeed = -3; // 
            }
        }
        
        //utilisation de la potion sur le joueur
        if (hasPotionEffect && (System.currentTimeMillis() - potionStartTime > Potion.getPotionEffectDuration())) {
           hasPotionEffect = false; // Reset potion effect after duration
       }
        
        if (isInvincible && System.currentTimeMillis() - invincibilityStartTime > INVINCIBILITY_DURATION) {
            isInvincible = false;
            System.out.println("Tu n'es plus invincible.");
        }
        
        // Vérification des collisions avec les différentes entités (en comprenant les objets)
        Generateur collOb = gp.verif.checkCollisionGen(newX, newY, l, L, getSolidAir());
        if (collOb!=null && (collOb instanceof gameObject)) {
        	if (collOb instanceof Key) {
        		 ((Usable) collOb).use(this);  // Utiliser la clé qui appelle addKey
        	}else if (collOb.getID().equals("d")) {
            	loseLife();
            }else {
            	addInv((gameObject) collOb);
            }
        	String key = collOb.getID();
            gp.genMap.remove(key);
            //System.out.println(gp.Genlist);
            gp.Genlist.set(Character.getNumericValue(key.charAt(key.length()-1)), new Generateur(gp));
            //gp.Genlist.remove(Character.getNumericValue(key.charAt(key.length()-1))-1);
        } 
        
        else if (collOb instanceof PNJ) {
        	PNJ pnj = (PNJ) collOb;
        	pnj.setCollisionWithPlayer(true);
        	pnj.triggerDialog();
        	if(!this.getLastPNJs().contains(pnj))
        		this.getLastPNJs().add(pnj);
        	 //moveBackwards(5);  // Fait reculer le joueur de 5 "pas"
        } 
        
        else if (collOb instanceof Monster) {
        	if (!gp.getPlayer().isInvincible()) {
        	Monster mon = (Monster) collOb;
        	mon.checkPlayerInteraction();
        	 moveBackwards(5);  // Fait reculer le joueur de 5 "pas"
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