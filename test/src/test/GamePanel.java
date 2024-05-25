package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.swing.*;

import entity.Player;
import objects.InventoryDisplay;
import objects.Key;
import entity.Monster;
import entity.PNJ_Magalor;
import entity.PNJ_Susie;
import entity.PNJ_bandana;
import objects.Objetc_manager;
import tiles.Tiles_manger;
import generation.Generateur;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
    public String currentMap; // Utilisez une chaîne de caractères pour stocker le chemin de la carte
	 //parametre ecran 
	final int originalTileSize = 16; // tuiles 16*16
	final int scale = 3; //variable pour le mettre a la bonne echelle
	
	public final int tileSize = originalTileSize * scale; // 48*48 pour afficher les tuiles
	public final int ObjetSize = originalTileSize * scale; // 48*48 pour afficher les objets 
	public final int PNJSize = originalTileSize * scale; // 48*48 pour afficher les objets 

	public final int maxScreenCol = 21; // on choisi la taille de la hauteur 
	public final int maxScreenRow = 10; // la taille de la hauteur
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixel
	public final int screenHeight = tileSize * maxScreenRow; // 480 pixel

	public final int maxWorldCol = 22;
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxScreenRow;

	//nombre d image par seconde d'image
	int FPS = 20;
	
	public ArrayList<Generateur> Genlist = new ArrayList<>();
 	public Map<String, Generateur> genMap;
 	//public PNJ_bandana pnj_bandana;
 	//public PNJ_Magalor pnj_magalor;
 	//public PNJ_Susie pnj_susie;
 	public String[][] mapGenNum;
	
	Tiles_manger tileM = new Tiles_manger(this);//tuile img
	//Objetc_manager ObjectM = new Objetc_manager(this); // img object
	public InventoryDisplay displayInv;
	KeyHandler keyH;
	Thread gameThread; //le fil du jeu, il appelle automatiquement la methode run 
	
	public CollisionVerif verif = new CollisionVerif(this); // pour la collision 

 	
 	
 	public Monster bomb;
    public GameState gameState; // Ajout de l'attribut gameState
	
	//constructeur de panel 
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		gameState = new GameState(this); // Initialisation de gameState
		
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        displayInv = new InventoryDisplay(getPlayer());
        add(displayInv, gbc);
        setOpaque(false);
        
        mapGenNum = new String [maxWorldCol][maxWorldRow];
        genMap = new HashMap<>();
        //this.loadGenMap("/maps_spawn/" + tileM.mapAct);
        
        keyH = new KeyHandler(displayInv, this);  // Passer 'this' pour référencer GamePanel
		getPlayer().setkeyH(keyH);
		this.addKeyListener(keyH); //reconaitre l'entr�e des touches 
		this.setFocusable(true);
		
		
	}
	
	public void addGen(Generateur g) {
		this.Genlist.add(g);
	}
	
	public void elaguerGen() {
		if(Genlist.size() != 0) {
			Genlist.subList(1, Genlist.size()).clear();
		}
	}
	
	public void fillMap() {
		
	}
	
	public Player getPlayer() {
		return ((Player) this.Genlist.get(0));
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGameThread() {
	    if (gameThread != null) {
	        gameThread.interrupt();
	        gameThread = null;
	    }
	}
	
	/*
	public void loadGenMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            int col = 0;
            int keyCounter = 1; // Compteur pour générer des identifiants uniques pour les clés

            String line;
            while ((line = br.readLine()) != null && row < maxWorldRow) {
                String[] characters = line.split(" ");
                col = 0;

                while (col < maxScreenCol && col < characters.length) {
                    char objChar = characters[col].charAt(0);
                    //String uniqueKeyId = "" + objChar + keyCounter++;
                    mapGenNum[col][row] = ""+objChar; // Utilisation de l'identifiant unique
                    col++;
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } */
	/*
	public void spawnNPC(char npcChar, int row, int col) {
        // Handle spawning NPC based on npcChar at the given row and col
        // This might involve creating a new NPC object and setting its position
    }
*/
	
	/*
	public void updateGenerators() {
        for (Generateur gen : GenList) {
             gen.update();
        }
    } */
	
		@SuppressWarnings("unused")
		public void update() {
		    // Mettre à jour le joueur en premier pour prendre en compte les nouvelles positions
		    if (!gameState.isGameOver()) {
		    	 getPlayer().update();
		    }

		    // Convertir les coordonnées du joueur en indices de tuile pour vérifier la collision
			int playerTileX = getPlayer().getScreenX() / tileSize;  // Utilisation de screenX pour la position en X
		    int playerTileY = getPlayer().getScreenY() / tileSize;  // Utilisation de screenY pour la position en Y
	
		
		    // Si le jeu continue, vérifier le changement de carte
		    if (!gameState.isGameOver()) {
		    	tileM.checkAndChangeMapOnPosition(); // Vérifie si le joueur a atteint la position spécifique pour le changement de carte
		    }	
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			if (gameState.isGameOver()) {
		        gameState.drawGameOverScreen(g2);
		    } 
			else {
				tileM.draw(g2); // d abord le decors 
				
	        if (currentMap != null && currentMap.equals("/maps/maps2.txt")) {// Afficher le PNJ si la carte actuelle est "map3.txt"
	        	//pnj_bandana.draw(g2);
	        	
	        }
        	if (currentMap != null && currentMap.equals("/maps/map3.txt")) {// Afficher le PNJ si la carte actuelle est "map3.txt"
	        	
	        }
        	if (currentMap != null && currentMap.equals("/maps/maps1.txt")) {// Afficher le PNJ si la carte actuelle est "map3.txt"
	            	//pnj_magalor.draw(g2);
	            	//pnj_susie.draw(g2);
	            	
	            
	        }
        	for (Generateur generateur : Genlist) {
                generateur.draw(g2); // Appelle la méthode update de la sous-classe spécifique
            }
			displayInv.paint(g2);
			
			
			g2.dispose();
		}
	}

		//genere automatiquement cette classe, permet de faire bouger le joueur
		@Override
		public void run() {
		    double drawInterval = 100000000.0 / FPS;
		    double nextDrawInterval = System.nanoTime() + drawInterval; 

		    while (gameThread != null && !Thread.currentThread().isInterrupted()) {
		        update();
		        repaint();

		        try {
		            double remainingTime = nextDrawInterval - System.nanoTime();
		            remainingTime /= 1000000.0; // convertir en millisecondes

		            if (remainingTime < 0) {
		                remainingTime = 0;
		            }

		            Thread.sleep((long) remainingTime);

		            nextDrawInterval += drawInterval;

		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt(); // Ré-interrompt le thread pour préserver le statut de l'interruption
		            break; // Sort de la boucle
		        }
		    }
		}

		/*public Objetc_manager getObjectM() {
			return ObjectM;
		}

		public void setObjectM(Objetc_manager objectM) {
			ObjectM = objectM;
		}	*/
}
