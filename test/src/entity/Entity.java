package entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.sun.tools.javac.Main;
import objects.gameObject;

public class Entity {
	
	//variable
    public int screenX;
    public int screenY; 
    public int l;
    public int L;
	public int speed;
	
	//Inventaire
	public ArrayList<gameObject> inv;
	
	//Gravit�
	public static final double GRAVITY = 0.06; // Constante de gravit�
    public double ySpeed = 0; // Vitesse verticale
	
	public BufferedImage neutre1, neutre2, up1, up2, right1, right2, left1, left2, down; //les images
	public String direction;
	
	//fait une animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//pour les collision 
	public Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	public boolean collisionOn = false;
	
	public void addInv(gameObject o) {
		if (inv.size() < 9) {
			this.inv.add(o);
		} else {
			System.out.println("Impossible : inventaire plein");
		}
	}
	
	public static Point[] findSpawnPoints(char character, String filePath) throws IOException {
        List<Point> spawnPointsList = new ArrayList<>();
        try (
            InputStream is = Main.class.getClassLoader().getResourceAsStream("maps_spawn/" + filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                int column = 0;
                line = line.replaceAll("\\s", ""); // Supprime tous les espaces de la ligne
                for (char c : line.toCharArray()) {
                    if (c != ' ' && c == character) { // Ignore les espaces
                        spawnPointsList.add(new Point(row, column));
                    }
                    column++;
                }
                row++;
            }
        }
        if (spawnPointsList.isEmpty()) {
            throw new IllegalArgumentException("Character not found in the file");
        }
        return spawnPointsList.toArray(new Point[0]);
    }

}