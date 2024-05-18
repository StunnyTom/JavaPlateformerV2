package objects;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import test.GamePanel;

public class Objetc_manager {
    GamePanel gp;
    public Map<String, gameObject> Objet_Map;
    public String[][] mapObjetnum; // Variable pour la map

    // Constructeur
    public Objetc_manager(GamePanel gp) {
        this.gp = gp;
        Objet_Map = new HashMap<>();
        mapObjetnum = new String [gp.maxWorldCol][gp.maxWorldRow];

        getObjetImage();
        loadMap("/maps_spawn/maps1.txt");
    }

    public void instObjet(gameObject objects) {
    	Objet_Map.put(objects.getID(), objects);
    }

    public void getObjetImage() {
        try {
            //instancier d'autre objets 
        	instObjet(new Potion());
        	instObjet(new Epee());
        	instObjet(new Dead());
        	instObjet(new Apple());
        	instObjet(new Etoile_Collision());
        	
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Pour appeler la map + lire le fichier txt et le translater

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            int col = 0;
            int keyCounter = 1; // Compteur pour générer des identifiants uniques pour les clés

            String line;
            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                String[] characters = line.split(" ");
                col = 0;

                while (col < gp.maxScreenCol && col < characters.length) {
                    char objChar = characters[col].charAt(0);
                    if (objChar == 'k') {
                        String uniqueKeyId = "k" + keyCounter++;
                        mapObjetnum[col][row] = uniqueKeyId; // Utilisation de l'identifiant unique
                        Objet_Map.put(uniqueKeyId, new Key(uniqueKeyId)); // Créer une clé avec un identifiant unique
                    } else {
                        mapObjetnum[col][row] = String.valueOf(objChar);
                    }
                    col++;
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
            String objectKey = mapObjetnum[worldCol][worldRow];

            if (objectKey != null) { // Si l'objet n'est pas vide
                gameObject objectToDraw = Objet_Map.get(objectKey);

                if (objectToDraw != null) { // Vérifiez que ce n'est pas null
                    g2.drawImage(objectToDraw.getImage(),
                            worldCol * gp.ObjetSize,
                            worldRow * gp.ObjetSize,
                            gp.ObjetSize,
                            gp.ObjetSize, null);
                }
            }

            worldCol++;
            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

