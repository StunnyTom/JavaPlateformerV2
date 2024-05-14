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
	protected int screenX;
	protected int screenY; 
    protected int l;
    protected int L;
	protected int speed;
	
	//Inventaire
	private ArrayList<gameObject> inv;
	
	//Gravit�
	protected static final double GRAVITY = 0.06; // Constante de gravit�
	protected double ySpeed = 0; // Vitesse verticale
	
	protected BufferedImage neutre1, neutre2, up1, up2, right1, right2, left1, left2, down; //les images
	protected String direction;
	
	//fait une animation
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	
	protected int padding;
	//private GamePanel gp;
	
	//pour les collision 
	private Rectangle solidAir; //stocker un rectangle invisible ou abstrait avec x,y, largeur, hauteur
	protected boolean collisionOn = false;
	
	private final int taille_inv = 9;
	
	public void addInv(gameObject o) {
		if (getInv().size() < taille_inv) {
			this.getInv().add(o);
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
      
        return spawnPointsList.toArray(new Point[0]);
    }
	
	//creation par default
	public int getScreenX() {
		return screenX;
	}
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}
	public int getScreenY() {
		return screenY;
	}
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public ArrayList<gameObject> getInv() {
		return inv;
	}

	public void setInv(ArrayList<gameObject> inv) {
		this.inv = inv;
	}

	public Rectangle getSolidAir() {
		return solidAir;
	}

	public void setSolidAir(Rectangle solidAir) {
		this.solidAir = solidAir;
	}
}