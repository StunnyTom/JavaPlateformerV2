 package tiles;

 import java.awt.Graphics2D;
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
     public int mapTilenum[][]; // Variable pour la map
     Tile backgroundTile; // Variable membre pour l'image de fond
     Tile Background2Tile; // Variable pour le second background
     Tile Background3Tile; // Variable pour le troisieme background


     // Constructeur
     public Tiles_manger(GamePanel gp) {
         this.gp = gp;
         tileMap = new HashMap<>();
         mapTilenum = new int[gp.maxWorldCol][gp.maxWorldRow];

         getTileImage();
         loadMap("/maps/maps2.txt");
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

	//pour appeler la map + lire le fichier txt et le translater 
     public void loadMap(String filePath) {
    	    try {
    	        InputStream is = getClass().getResourceAsStream(filePath);
    	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

    	        int row = 0;
    	        int col = 0;

    	        String line;
    	        while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
    	            String[] characters = line.split(" ");
    	            col = 0;
    	            
    	            while (col < gp.maxWorldCol && col < characters.length) {
    	                char tileChar = characters[col].charAt(0); // Obtient le premier caract�re de la cha�ne
    	                int tileNum = tileChar - 'a'; 
    	                mapTilenum[col][row] = tileNum;
    	                col++;
    	            }
    	            row++;
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
	
     public void draw(Graphics2D g2) {
    	    int worldCol = 0;
    	    int worldRow = 0;
    	   
    	    
    	 // Draw the 1er background
    	    if (backgroundTile != null) {
    	        g2.drawImage(backgroundTile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    	    }

    	    // Draw the 2eme overlay background
    	    if (Background2Tile != null) {
    	        g2.drawImage(Background2Tile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    	    }
    	    
    	    //Draw the 3eme overlay background
    	    if (Background3Tile != null) {
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
 