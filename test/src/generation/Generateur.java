package generation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.Main;

import entity.Entity;
import test.GamePanel;

public class Generateur {

	protected BufferedImage image; //les images
	protected static GamePanel gp;
	
	protected int screenX;
	protected int screenY;

	protected boolean actif;
	
	protected String id;
	
	public Generateur(GamePanel gp) {
		setGP(gp);
	}
	
	public static GamePanel getGP() {
		return gp;
	}
	
	public void setCoordonnees(int cpt) {
		File nameMap = new File(gp.currentMap);
		Point x;
		try {
			x = findSpawnPoints(getID().charAt(0), nameMap.getName())[cpt];
			setScreenX((int) (gp.tileSize * x.getY()));
	        setScreenY((int) (gp.tileSize * x.getX()));
	
	        System.out.println(getScreenX());
	        System.out.println(getScreenY());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGP(GamePanel gp) {
		Generateur.gp = gp;
	}
	
	public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
	
	public boolean getActif() {
		return this.actif;
	}
	
	public void setActif(boolean act) {
		actif = act;
	}
	
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
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
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
	
	public void draw(Graphics2D g2) {
        
		
        // Dessine l'image sur le rectangle bleu
        g2.drawImage(getImage(), getScreenX(), getScreenY(), gp.tileSize, gp.tileSize, null);
    }
	
}
