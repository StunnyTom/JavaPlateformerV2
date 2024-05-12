 package tiles;

 import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import test.GamePanel;

 public class Tiles_manger {
	    GamePanel gp;
	    public Map<Character, Tile> tileMap;
	    public int mapTilenum[][];
	    Tile backgroundTile;
	    Tile Background2Tile;
	    Tile Background3Tile;

	 // Utilisez un dictionnaire pour mémoriser les transitions de carte
	    private Map<Point, MapTransition> mapTransitions;

	    public Tiles_manger(GamePanel gp) {
	        this.gp = gp;
	        tileMap = new HashMap<>();
	        mapTilenum = new int[gp.maxWorldCol][gp.maxWorldRow];
	        getTileImage();
	        initMapTransitions();  // Initialiser les transitions ici
	        loadMap("/maps/maps1.txt");  // Charge la première carte au démarrage
	        System.out.println("Initial map loaded: /maps/maps1.txt");
	    }
	    
	 // Initialisation des transitions de carte
	    private void initMapTransitions() {
	        mapTransitions = new HashMap<>();
	        mapTransitions.put(new Point(20, 6), new MapTransition("/maps/maps2.txt", 1, 0)); //map 1 à 2
	        mapTransitions.put(new Point(0, 1), new MapTransition("/maps/maps1.txt", 20, 6)); // map 2 à 1
	        mapTransitions.put(new Point(20, 7), new MapTransition("/maps/map3.txt", 0, 6)); //map 2 à 3
	        mapTransitions.put(new Point(0, 7), new MapTransition("/maps/maps2.txt", 19, 7)); // map 3 à 2 
	        mapTransitions.put(new Point(19, 1), new MapTransition("/maps/maps4.txt", 1, 3));  // map 3 à 4 
	        mapTransitions.put(new Point(20, 3), new MapTransition("/maps/maps1.txt", 1, 2)); //map 4 à 1
	    }
	    
	    public void checkAndChangeMapOnPosition() {
	        int playerTileX = gp.player.screenX / gp.tileSize;
	        int playerTileY = gp.player.screenY / gp.tileSize;
	        Point playerTile = new Point(playerTileX, playerTileY);

	        // Vérifie si une transition est disponible pour cette position
	        if (mapTransitions.containsKey(playerTile)) {
	            MapTransition transition = mapTransitions.get(playerTile);
	            changeMap(transition.mapPath, transition.startX, transition.startY);
	        }
	    }

     public void getTileImage() {
         try {
        	 
             // Chargement the 1er background
             backgroundTile = new Tile();
             try {
                 backgroundTile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG1.png"));
             } catch (Exception e) {
                 e.printStackTrace();
             }
             // Chargement the 2eme background
             Background2Tile = new Tile();
             try {
                 Background2Tile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG2.png")); 
             } catch (Exception e) {
                 e.printStackTrace();
             }
             //Chargement the 3em background
             Background3Tile = new Tile();
             try {
            	 Background3Tile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG3.png"));
             }catch (Exception e) {
                 e.printStackTrace();
             }    
             
             //appeler les tuiles
             tileMap.put('b', new Tile());
             tileMap.get('b').image = ImageIO.read(getClass().getResourceAsStream("/tiles/010.png"));
             tileMap.get('b').collision = true;
             
             tileMap.put('c', new Tile());
             tileMap.get('c').image = ImageIO.read(getClass().getResourceAsStream("/tiles/003.png"));
             tileMap.get('c').collision = true;
             
             tileMap.put('d', new Tile());
             tileMap.get('d').image = ImageIO.read(getClass().getResourceAsStream("/tiles/002.png"));
             
             tileMap.put('e', new Tile());
             tileMap.get('e').image = ImageIO.read(getClass().getResourceAsStream("/tiles/011.png"));
             tileMap.get('e').collision = true;
             
             tileMap.put('f', new Tile());
             tileMap.get('f').image = ImageIO.read(getClass().getResourceAsStream("/tiles/015.png"));
             
             tileMap.put('g', new Tile());
             tileMap.get('g').image = ImageIO.read(getClass().getResourceAsStream("/tiles/001.png"));
             
             tileMap.put('h', new Tile());
             tileMap.get('h').image = ImageIO.read(getClass().getResourceAsStream("/tiles/012.png"));
             tileMap.get('h').collision = true;
             
             tileMap.put('i', new Tile());
             tileMap.get('i').image = ImageIO.read(getClass().getResourceAsStream("/tiles/013.png"));
             
             tileMap.put('j', new Tile());
             tileMap.get('j').image = ImageIO.read(getClass().getResourceAsStream("/tiles/005.png"));  
            
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     private void changeMap(String mapPath, int startX, int startY) {
         System.out.println("Changement de carte à " + mapPath);
         loadMap(mapPath);
         gp.player.screenX = startX * gp.tileSize;
         gp.player.screenY = startY * gp.tileSize;
         System.out.println("La carte a été changée avec succès à: " + mapPath);
     }

     
	//pour appeler la map + lire le fichier txt et le translater 
     public void loadMap(String filePath) {
         try {
        	 System.out.println("Loading map: " + filePath);
             gp.currentMap = filePath;
             InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is));
             int row = 0;
             int col = 0;
             String line;
             while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                 String[] characters = line.split(" ");
                 col = 0;
                 while (col < gp.maxWorldCol && col < characters.length) {
                     char tileChar = characters[col].charAt(0);
                     int tileNum = tileChar - 'a';
                     mapTilenum[col][row] = tileNum;
                     col++;
                 }
                 row++;
             }
             System.out.println("Map loaded successfully.");
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("Error loading map: " + filePath);
         }
     }
     
     public void draw(Graphics2D g2) {
    	    int worldCol = 0;
    	    int worldRow = 0;
    
    	    if (backgroundTile != null) { // Draw the 1er background
    	        g2.drawImage(backgroundTile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    	    }
    	    if (Background2Tile != null) { // Draw the 2eme overlay background
    	        g2.drawImage(Background2Tile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    	    }
    	    if (Background3Tile != null) { //Draw the 3eme overlay background
    	        g2.drawImage(Background3Tile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    	    }
    	  
    	    // Draw the tiles
    	    while(worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
    	        int tileNum = mapTilenum[worldCol][worldRow];
    	        
    	        int worldX = worldCol * gp.tileSize;
    	        int worldY = worldRow * gp.tileSize;
    	        int screenX = worldX;
    	        int screenY = worldY;
    	        
    	        if (tileNum != 0) { // If the tile is not 'a', then draw it
    	            char tileKey = (char) ('a' + tileNum);

    	            if (tileMap.containsKey(tileKey)) {
    	                Tile tile = tileMap.get(tileKey);
    	                g2.drawImage(tile.image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    	            } else {
    	            }
    	        }
    	        worldCol++;
   
    	        if(worldCol == gp.maxScreenCol) {
    	            worldCol = 0;
    	            worldRow++;
    	        }
    	    }
    	}

	
 }
 
     
     /*
     //A CHANGER
     public void checkAndChangeMapOnPosition() {
         // Convertir les coordonnées du joueur en indices de tuile
         int playerTileX = gp.player.screenX / gp.tileSize;
         int playerTileY = gp.player.screenY / gp.tileSize;

         // Spécifier la position de changement de carte map 1 => 2
         if (playerTileX == 20 && playerTileY == 6) {
             String nextMap = "/maps/maps2.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 1 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 0 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
         //maps 2 => 1 
         if (playerTileX == 0 && playerTileY == 1) {
             String nextMap = "/maps/maps1.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 20 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 6 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
             }

         //maps 2 => 3
         if (playerTileX == 20 && playerTileY == 7) {
             String nextMap = "/maps/map3.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 0 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 6 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
         
         //map 3 => 2
         if (playerTileX == 0 && playerTileY == 7) {
             String nextMap = "/maps/maps2.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 19 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 7 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
       //map 3 => 4 
         if (playerTileX == 19 && playerTileY == 1) {
             String nextMap = "/maps/maps4.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 1 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 3 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
         
         if (playerTileX == 20 && playerTileY == 3) {
             String nextMap = "/maps/maps1.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 1 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 2 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
         
         if (playerTileX == 0 && playerTileY == 2) {
             String nextMap = "/maps/maps4.txt"; // Chemin de la nouvelle carte
             System.out.println("Changement de carte à " + nextMap);
             loadMap(nextMap);  // Charger la nouvelle carte
             // Réinitialiser la position du joueur si nécessaire ou ajuster selon la logique de votre jeu
             gp.player.screenX = 1 * gp.tileSize; // Réinitialiser x à l'entrée de la nouvelle carte
             gp.player.screenY = 2 * gp.tileSize; // Réinitialiser y à l'entrée de la nouvelle carte
             System.out.println("La carte a été changée avec succès à: " + nextMap);
         }
         
      */
         
         
         
        
         
    

   