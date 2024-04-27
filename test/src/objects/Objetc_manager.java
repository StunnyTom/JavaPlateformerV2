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
    public Map<Character, Object> Objet_Map;
    public int mapObjetnum[][]; // Variable pour la map
  

 
    // Constructeur
    public Objetc_manager(GamePanel gp) {
        this.gp = gp;
        Objet_Map = new HashMap<>();
        mapObjetnum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getObjetImage();
        loadMap("/maps_object/maps1_obj.txt");
            }
    
    public void getObjetImage() {
        try {
        	
        	Objet_Map.put('z', new Object());
        	Objet_Map.get('z').image = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
        	Objet_Map.get('z').collision = true;
        	Objet_Map.get('z').id = '1'; // Ajouter un champ "id"
        	
       

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
   	                char Objet_testChar = characters[col].charAt(0); // Obtient le premier caract�re de la cha�ne
   	                int Objet_testNum = Objet_testChar - 'a'; 
   	                mapObjetnum[col][row] = Objet_testNum;
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

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
            int Objet_testNum = mapObjetnum[worldCol][worldRow];

            if (Objet_testNum != 0) { // Si l'objet n'est pas vide
                char Objet_testKey = (char) ('a' + Objet_testNum);

                // Vérifiez que l'objet existe dans la map
                if (Objet_Map.containsKey(Objet_testKey)) {
                    Object Objet_test = Objet_Map.get(Objet_testKey);
                    
                    if (Objet_test != null) { // Vérifiez que ce n'est pas null
                        g2.drawImage(Objet_test.image, 
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


