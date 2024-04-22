package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import objects.ParentsObjects;
import tiles.Tiles_manger;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
	//parametre ecran 
	final int originalTileSize = 16; // tuiles 16*16
	final int scale = 3; //variable pour le mettre a la bonne echelle
	
	public final int tileSize = originalTileSize * scale; // 48*48
	public final int maxScreenCol = 21; // on choisi la taille de la hauteur 
	public final int maxScreenRow = 10; // la taille de la hauteur
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixel
	public final int screenHeight = tileSize * maxScreenRow; // 480 pixel
	
	
	public final int maxWorldCol = 42;
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxScreenRow;
	
	//nombre d image par seconde d'image
	int FPS = 30;
	
	Tiles_manger tileM = new Tiles_manger(this);//tuile img
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //le fil du jeu, il appelle automatiquement la methode run 
	public CollisionVerif verif = new CollisionVerif(this); // pour la collision 
	public ObjSetter objSetter = new ObjSetter(this);//on instencifie ObjSetter
 	public Player player = new Player(this, keyH);
 	public ParentsObjects obj[] = new ParentsObjects[10];//on l'utilise comme un tableau de 10 emplacements (afficher 10 objetcs en meme temps)
	
 	
	//constructeur de panel 
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); //reconaitre l'entr�e des touches 
		this.setFocusable(true);
		
		// Initialisation du tableau obj avant d'utiliser des instances qui en dépendent
	    for (int i = 0; i < obj.length; i++) {
	        obj[i] = null; // Ou initialisez avec une instance appropriée si nécessaire
	    }
	    setupGame();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	//on appelle la methode setObject
	public void setupGame() {
		objSetter.setObjet();
	}
	//genere automatiquement cette classe, permet de faire bouger le joueur
	@Override
	public void run() {
		//methode du sommeil 
		double drawInterval = 100000000/FPS;
		double nextDrawInterval = System.nanoTime() + drawInterval; // quand le temps de mon programme est atteint il faut dessiner
		
		//boucle du jeu ==> le <3 de notre jeu
		while(gameThread != null) { 
			//1. update la position du joueur 	2. redessiner le perso
			update();
			repaint();
			
			//le slepp mets en pause le jeu et ne fera rien 	
			try {
				double remainingTime = nextDrawInterval - System.nanoTime();
				//convertir en miliseconde
				remainingTime = remainingTime/1000000;
				
				if(remainingTime <0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime); 
				
				nextDrawInterval += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
		//Mis a jour de la position 
		public void update() {
			player.update();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			tileM.draw(g2); // d abord le decors 
			//connaitre le type d obj
			for (int i = 0; i<obj.length; i++) { // on verifie les objets dans le table
				if(obj[i] != null) {
					obj[i].draw(g2,  this);
				}
			}
			player.draw(g2);// puis apres le perso 
		
			g2.dispose();
			
		}
}

/*
	
		
	// ajouter 
	
	public void updateCamera() {
	    camX = player.screenX - (screenWidth / 2);
	    camY = player.screenY - (screenHeight / 2);

	    // Limiter la caméra à la taille de la carte
	    if(camX < 0) camX = 0;
	    if(camY < 0) camY = 0;
	    if(camX > worldWidth - screenWidth) camX = worldWidth - screenWidth;
	    if(camY > worldHeight - screenHeight) camY = worldHeight - screenHeight;
	}

		//Mis a jour de la position 
		public void update() {
			player.update();
		}
		
		
}
*/