package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

//import javax.swing.JPanel;

import entity.PNJ_Magalor;
import entity.PNJ_bandana;
import entity.Player;
import objects.InventoryDisplay;
import objects.Objetc_manager;
import tiles.Tiles_manger;

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
	int FPS = 30;
	
	Tiles_manger tileM = new Tiles_manger(this);//tuile img
	public Objetc_manager ObjectM = new Objetc_manager(this); // img object
	public InventoryDisplay displayInv;
	KeyHandler keyH;
	Thread gameThread; //le fil du jeu, il appelle automatiquement la methode run 
	
	public CollisionVerif verif = new CollisionVerif(this); // pour la collision 
 	public Player player;
 	public PNJ_bandana pnj_bandana = new PNJ_bandana(this);
 	public PNJ_Magalor pnj_magalor = new PNJ_Magalor(this);
 	

 // Liste pour stocker les PNJ
    public ArrayList<PNJ_bandana> listPNJ = new ArrayList<>();
    public ArrayList<PNJ_Magalor> listPNJ_Magalor = new ArrayList<>();
    
    GameState gameState; // Ajout de l'attribut gameState

    
	//constructeur de panel 
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		player = new Player(this);
		gameState = new GameState(this); // Initialisation de gameState
		
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        displayInv = new InventoryDisplay(player);
        add(displayInv, gbc);
        setOpaque(false);
        
        keyH = new KeyHandler(displayInv, this);  // Passer 'this' pour référencer GamePanel

		player.setkeyH(keyH);
		this.addKeyListener(keyH); //reconaitre l'entr�e des touches 
		this.setFocusable(true);
		
		PNJ_bandana pnj1 = new PNJ_bandana(this);
	    listPNJ.add(pnj1);
	    
	    PNJ_Magalor pnj2 = new PNJ_Magalor(this);
	    listPNJ_Magalor.add(pnj2); // Ajouter PNJ_Magalor à sa propre liste
		
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


		public void update() {
		    // Mettre à jour le joueur en premier pour prendre en compte les nouvelles positions
		    if (!gameState.isGameOver()) {
		        player.update();
		    }

		    // Convertir les coordonnées du joueur en indices de tuile pour vérifier la collision
		    int playerTileX = player.screenX / tileSize;  // Utilisation de screenX pour la position en X
		    int playerTileY = player.screenY / tileSize;  // Utilisation de screenY pour la position en Y

		    
		    System.out.println("x = " + playerTileX);
		    System.out.println("y = "+ playerTileY);
		    // Vérifier la collision avec les tuiles et afficher le game over si nécessaire
		   

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
		    } else {
			
			tileM.draw(g2); // d abord le decors 
			ObjectM.draw(g2); // puis les objects
			
	        if (currentMap != null && currentMap.equals("/maps/maps2.txt")) {// Afficher le PNJ si la carte actuelle est "map3.txt"
	        	for (PNJ_bandana pnj : listPNJ) {
	                pnj.draw(g2);  // Dessiner chaque PNJ, inclura la boîte de dialogue si collision
	            }
	        }
        	if (currentMap != null && currentMap.equals("/maps/map3.txt")) {// Afficher le PNJ si la carte actuelle est "map3.txt"
	        	for (PNJ_Magalor pnj : listPNJ_Magalor) {
	                pnj.draw(g2);  // Dessiner chaque PNJ, inclura la boîte de dialogue si collision
	            }
	        }

			player.draw(g2);// puis apres le perso 	
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

		
}