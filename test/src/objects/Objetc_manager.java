package objects;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import test.GamePanel;

public class Objetc_manager {
    GamePanel gp;
    public Map<String, gameObject> Objet_Map;
    public int mapObjetnum[][]; // Variable pour la map

    // Constructeur
    public Objetc_manager(GamePanel gp) {
        this.gp = gp;
        Objet_Map = new HashMap<>();
        mapObjetnum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getObjetImage();
        loadMap("/maps_spawn/maps1.txt");
    }

    public void instObjet(String c, String filePath, boolean col) {
        try {
            Objet_Map.put(c, new gameObject(true));
            Objet_Map.get(c).setImage(ImageIO.read(getClass().getResourceAsStream(filePath)));
            Objet_Map.get(c).collision = col;
            Objet_Map.get(c).id = c; // Ajouter un champ "id"
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getObjetImage() {
        try {
            this.instObjet("k", "/objects/cle.png", true);
            this.instObjet("y", "/objects/cle.png", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // pour appeler la map + lire le fichier txt et le translater 
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

                while (col < gp.maxScreenCol && col < characters.length) {
                    char Objet_testChar = characters[col].charAt(0); // Obtient le premier caractère de la chaîne
                    int Objet_testNum = Objet_testChar - 'a';
                    mapObjetnum[col][row] = Objet_testNum;
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
            int Objet_testNum = mapObjetnum[worldCol][worldRow];

            if (Objet_testNum != 0) { // Si l'objet n'est pas vide
                char key = (char) ('a' + Objet_testNum);
                String Objet_testKey = "" + key;

                // Vérifiez que l'objet existe dans la map
                if (Objet_Map.containsKey(Objet_testKey)) {
                    gameObject Objet_test = Objet_Map.get(Objet_testKey);

                    if (Objet_test != null) { // Vérifiez que ce n'est pas null
                        g2.drawImage(Objet_test.getImage(),
                                worldCol * gp.ObjetSize,
                                worldRow * gp.ObjetSize,
                                gp.ObjetSize,
                                gp.ObjetSize, null);
                    } else {
                        // System.out.println("No  Objet_test for: " + Objet_testKey);
                    }
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
