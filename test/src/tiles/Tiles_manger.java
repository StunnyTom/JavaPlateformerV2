package tiles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import generation.Generateur;
import objects.Key;
import objects.Pistolet;
import objects.Aimant;
import objects.Apple;
import objects.Dead;
import objects.Epee;
import objects.Etoile;
import objects.Fantome_Collision;
import objects.ItemA;
import objects.ItemB;
import objects.ItemC;
import objects.ItemD;
import objects.ItemE;
import objects.Potion;
import objects.Revivre;
import objects.gameObject;
import entity.Coffre;
import entity.Monster_Attaque;
import entity.Monster_Bomb;
import entity.Monster_Max;
import entity.Monster_Nuage;
import entity.Monster_Volant;
import entity.PNJ_A;
import entity.PNJ_Coffre;
import entity.PNJ_Inv;
import entity.PNJ_Magalor;
import entity.PNJ_Marchand;
import entity.PNJ_bandana;
import entity.PNJ_Susie;
import entity.Player;
import test.GamePanel;


//Classe qui gère les apparitions du décor et le chargement du contenu de chaque map
public class Tiles_manger {
    GamePanel gp;
    public Map<Character, Tile> tileMap;
    public int mapTilenum[][];
    Tile backgroundTile;
    Tile Background2Tile;
    Tile Background3Tile;
    
    public String mapOrigin;
    public String mapAct;

    public Map<String, Class<? extends Generateur>> Gen_Map = new HashMap<>();

    // UDictionnaire pour mémoriser les transitions de carte
    private Map<Point, MapTransition> mapTransitions;

    public Tiles_manger(GamePanel gp) {
        this.gp = gp;
        tileMap = new HashMap<>(); 
        mapTilenum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        initMapTransitions();  // Initialiser les transitions ici
        try {
            mapOrigin = findFileWithCharacterZ("/maps_spawn/");
            mapAct = mapOrigin;
            //System.out.println(mapOrigin);
            loadMap("/maps/" + mapOrigin);  // Charge la première carte au démarrage
            loadSpawnMap("/maps_spawn/" + mapOrigin);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new IllegalArgumentException("No initial map found.");
        }
        initGenMap();
        addGenToGamePanel("z");
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

    private void initGenMap() {
        Gen_Map.put("z", Player.class); //perso 
        
    	//object 
    	Gen_Map.put(" ", Aimant.class);
    	Gen_Map.put("d", Dead.class);
    	Gen_Map.put("e", Epee.class);
    	Gen_Map.put("f", Fantome_Collision.class);
    	Gen_Map.put("i", Etoile.class);
    	Gen_Map.put("k", Key.class);
        Gen_Map.put("p", Potion.class);
        Gen_Map.put("t", Pistolet.class);
        Gen_Map.put("v", Apple.class);
        
        //nouveau item créer
        Gen_Map.put("c", ItemA.class);
        Gen_Map.put("B", ItemB.class);
        Gen_Map.put("T", ItemC.class);
        Gen_Map.put("D", ItemD.class);
        Gen_Map.put("E", ItemE.class);
        Gen_Map.put("f", Revivre.class);
        
        
     
        //PNJ
        Gen_Map.put("S", PNJ_A.class);
        Gen_Map.put("b", PNJ_bandana.class);
        Gen_Map.put(" ", PNJ_Coffre.class);
        Gen_Map.put("C", Coffre.class);
        Gen_Map.put("I", PNJ_Inv.class);
        Gen_Map.put("M", PNJ_Magalor.class);
        //Gen_Map.put("S", PNJ_Susie.class);
        Gen_Map.put("W", PNJ_Marchand.class);
        
        //Monstre
        Gen_Map.put("A",  Monster_Attaque.class);
        Gen_Map.put(" ", Monster_Max.class);
        Gen_Map.put("L", Monster_Bomb.class);
        Gen_Map.put("N", Monster_Nuage.class);
        Gen_Map.put("V", Monster_Volant.class);
    }
    
    //rajouter le générateur à la liste des éléments chargés de la map
    private void addGenToGamePanel(String key) {
        Class<? extends Generateur> clazz = Gen_Map.get(key);
        if (clazz != null) {
            try {
                Generateur generateur = clazz.getDeclaredConstructor(GamePanel.class).newInstance(gp);
                generateur.setID(key);
                int cpt = countOccurrences(gp.Genlist, generateur.getClass());
                generateur.setCoordonnees(cpt);
                gp.addGen(generateur);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static <T> int countOccurrences(List<?> list, Class<T> clazz) {
        int count = 0;
        for (Object obj : list) {
            if (clazz.isInstance(obj)) {
                count++;
            }
        }
        return count;
    }

    public void checkAndChangeMapOnPosition() {
        int playerTileX = gp.getPlayer().getScreenX() / gp.tileSize;
        int playerTileY = gp.getPlayer().getScreenY() / gp.tileSize;
        Point playerTile = new Point(playerTileX, playerTileY);

        // Vérifie si une transition est disponible pour cette position
        if (mapTransitions.containsKey(playerTile)) {
            MapTransition transition = mapTransitions.get(playerTile);
            changeMap(transition.getMapPath(), transition.getSpawnMapPath(), transition.startX, transition.startY);
        }
    }

    public void getTileImage() {
        try {
            // Chargement des backgrounds
            backgroundTile = new Tile();
            backgroundTile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG1.png"));

            Background2Tile = new Tile();
            Background2Tile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG2.png"));

            Background3Tile = new Tile();
            Background3Tile.image = ImageIO.read(getClass().getResourceAsStream("/background_img/BG3.png"));

            // Appeler les tuiles
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
            tileMap.get('f').collision = true;

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

    //Gestion du changement de map
    private void changeMap(String mapPath, String spawnMapPath, int startX, int startY) {
       // System.out.println("Changement de carte à " + mapPath);
        loadMap(mapPath);
        loadSpawnMap(spawnMapPath);
        gp.getPlayer().setScreenX(startX * gp.tileSize);
        gp.getPlayer().setScreenY(startY * gp.tileSize);
        //System.out.println("La carte a été changée avec succès à: " + mapPath);
    }

    // Pour appeler la map + lire le fichier txt et le translater 
    public void loadMap(String filePath) {
        try {
           // System.out.println("Loading map: " + filePath);
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
           // System.out.println("Map loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
           // System.out.println("Error loading map: " + filePath);
        }
    }

    //Idem que loadMap, mais pour gérer les éléments non-décoratifs de la map correspondante
    public void loadSpawnMap(String filePath) {
        try {
            // Trouver le premier coffre dans Genlist
            PNJ_Coffre firstCoffre = findFirstCoffre();
            
            // Transférer les objets du premier coffre dans l'inventaire bonus
            if (firstCoffre != null) {
                transferItemsToBonusInventory(firstCoffre);
            }

            gp.mapGenNum = new String[gp.maxWorldCol][gp.maxWorldRow];
            gp.elaguerGen();
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            int row = 0;
            int col = 0;
            int keyCounter = 1;
            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                String[] characters = line.split(" ");
                col = 0;
                while (col < gp.maxWorldCol && col < characters.length) {
                    String tileChar = characters[col];
                    if (!tileChar.equals("a") && !tileChar.equals("z")) {
                        addGenToGamePanel(tileChar);
                        String uniqueKeyId = "" + tileChar + keyCounter++;
                        gp.mapGenNum[col][row] = uniqueKeyId; // Utilisation de l'identifiant unique
                        if (!gp.Genlist.isEmpty() && gp.Genlist.size() > 1) {
                            gp.genMap.put(uniqueKeyId, gp.Genlist.get(gp.Genlist.size() - 1));
                            gp.Genlist.get(gp.Genlist.size() - 1).setID(uniqueKeyId);
                           // System.out.println(uniqueKeyId);
                        }
                    }
                    col++;
                }
                row++;
            }

            // Remettre les objets de l'inventaire bonus dans le premier coffre de la nouvelle map
            firstCoffre = findFirstCoffre(); // Rechercher le premier coffre dans la nouvelle liste
            if (firstCoffre != null) {
                transferItemsFromBonusInventory(firstCoffre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pour éviter la duplication d'objet, seul le premier coffre généré gère réellement le stockage d'objet à long terme
    private PNJ_Coffre findFirstCoffre() {
        for (Object obj : gp.Genlist) {
            if (obj instanceof PNJ_Coffre) {
                return (PNJ_Coffre) obj;
            }
        }
        return null;
    }

    //Deux méthodes pour la gestion du stockage
    
    private void transferItemsToBonusInventory(PNJ_Coffre coffre) {
    	if (coffre.getPnjInv().size()!=0)
        gp.getStockInv().addAll(coffre.getPnjInv());
    }

    private void transferItemsFromBonusInventory(PNJ_Coffre coffre) {
    	if (gp.getStockInv().size()!=0)
    	for (gameObject o : gp.getStockInv()) {
    		coffre.addPnjInv(o);
    	}
        gp.getStockInv().clear();
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
        if (Background3Tile != null) { // Draw the 3eme overlay background
            g2.drawImage(Background3Tile.image, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }

        // Draw the tiles
        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
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
                }
            }
            worldCol++;

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public static String findFileWithCharacterZ(String directoryPath) throws IOException, URISyntaxException {
        URL url = Tiles_manger.class.getResource(directoryPath);
        if (url == null) {
            throw new IllegalArgumentException("The provided path does not exist: " + directoryPath);
        }
        File directory = new File(url.toURI());
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory.");
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("The directory is empty or an error occurred.");
        }

        String foundFile = null;
        for (File file : files) {
            if (file.isFile()) {
                if (containsCharacterZ(file)) {
                    if (foundFile != null) {
                        throw new IllegalArgumentException("The character 'z' was found in multiple files.");
                    }
                    foundFile = file.getName();
                }
            }
        }

        if (foundFile == null) {
            throw new IllegalArgumentException("The character 'z' was not found in any file.");
        }

        return foundFile;
    }

    //Méthode pour trouver quelle map générer en premier (dépend de la position choisie du joueur)
    private static boolean containsCharacterZ(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("z")) {
                    return true;
                }
            }
        }
        return false;
    }
}