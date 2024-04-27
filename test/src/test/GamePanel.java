package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import objects.Objetc_manager;
import tiles.Tiles_manger;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
	//parametre ecran 
	final int originalTileSize = 16; // tuiles 16*16
	final int scale = 3; //variable pour le mettre a la bonne echelle
	
	public final int tileSize = originalTileSize * scale; // 48*48 pour afficher les tuiles
	
	public final int maxScreenCol = 21; // on choisi la taille de la hauteur 
	public final int maxScreenRow = 10; // la taille de la hauteur
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixel
	public final int screenHeight = tileSize * maxScreenRow; // 480 pixel

	public final int maxWorldCol = 42;
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxScreenRow;
	
	
	public final int ObjetSize = originalTileSize * scale; // 48*48 pour afficher les objets 

	
	//nombre d image par seconde d'image
	int FPS = 30;
	
	Tiles_manger tileM = new Tiles_manger(this);//tuile img
	public Objetc_manager ObjectM = new Objetc_manager(this); // img object
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //le fil du jeu, il appelle automatiquement la methode run 
	
	public CollisionVerif verif = new CollisionVerif(this); // pour la collision 
 	public Player player = new Player(this, keyH);

 	
	//constructeur de panel 
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); //reconaitre l'entr�e des touches 
		this.setFocusable(true);
		
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
		//Mis a jour de la position 
		public void update() {
			player.update();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			tileM.draw(g2); // d abord le decors 
			ObjectM.draw(g2); // puis les objects
			player.draw(g2);// puis apres le perso 
			g2.dispose();
			
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
		



}