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
	        loadSpawnMap("/maps_spawn/maps1.txt");
	        //System.out.println("Initial map loaded: /maps/maps1.txt");
	    }
	    
	 // Initialisation des transitions de carte
	    private void initMapTransitions() {
	        mapTransitions = new HashMap<>();
	        mapTransitions.put(new Point(20, 6), new MapTransition("/maps/maps2.txt", "/maps_spawn/maps2.txt", 1, 0)); // map 1 à 2
	        mapTransitions.put(new Point(0, 1), new MapTransition("/maps/maps1.txt", "/maps_spawn/maps1.txt", 20, 6)); // map 2 à 1
	        mapTransitions.put(new Point(20, 7), new MapTransition("/maps/map3.txt", "/maps_spawn/map3.txt", 0, 6)); // map 2 à 3
	        mapTransitions.put(new Point(0, 7), new MapTransition("/maps/maps2.txt", "/maps_spawn/maps2.txt", 19, 7)); // map 3 à 2
	        mapTransitions.put(new Point(19, 1), new MapTransition("/maps/maps4.txt", "/maps_spawn/maps4.txt", 1, 3)); // map 3 à 4
	        mapTransitions.put(new Point(20, 3), new MapTransition("/maps/maps1.txt", "/maps_spawn/maps1.txt", 1, 2)); // map 4 à 1
	    }

	    
	    public void checkAndChangeMapOnPosition() {
	        int playerTileX = gp.getPlayer().getScreenX() / gp.tileSize;
	        int playerTileY = gp.getPlayer().getScreenY() / gp.tileSize;
	        Point playerTile = new Point(playerTileX, playerTileY);

	        // Vérifie si une transition est disponible pour cette position
	        if (mapTransitions.containsKey(playerTile)) {
	            MapTransition transition = mapTransitions.get(playerTile);
	            changeMap(transition.getMapPath(), transition.getSpawnMapPath(),  transition.startX, transition.startY);
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
             
             tileMap.put('q', new Tile());
             tileMap.get('q').image = ImageIO.read(getClass().getResourceAsStream("/tiles/castel.png")); 
             
      
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     private void changeMap(String mapPath, String spawnMapPath, int startX, int startY) {
         System.out.println("Changement de carte à " + mapPath);
         loadMap(mapPath);
         loadSpawnMap(spawnMapPath);
         gp.getPlayer().setScreenX(startX * gp.tileSize);
         gp.getPlayer().setScreenY(startY * gp.tileSize);
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
     
     public void loadSpawnMap(String filePath) {
         try {
             //System.out.println("Loading spawn map: " + filePath);
             InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is));
             @SuppressWarnings("unused")
			String line;
             while ((line = br.readLine()) != null) {
                 // Implémentez ici le chargement des objets, PNJ et monstres
                 // Exemple: parsez la ligne et créez des instances d'objets correspondantes
             }
            // System.out.println("Spawn map loaded successfully.");
         } catch (Exception e) {
             e.printStackTrace();
            // System.out.println("Error loading spawn map: " + filePath);
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
 
    
         
         
        
         
    

   