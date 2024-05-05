package objects;
import java.awt.image.BufferedImage;

public class gameObject {
		
	public BufferedImage image;
	public boolean collision = false; //collision
	public String id;
		
	   
    public gameObject(boolean collision) {
        this.collision = collision;
    }
    
    public BufferedImage getImage() {
    	return this.image;
    }
    
    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return this.id;
    }
    
    public boolean nullObj() {
        return this.id != null && this.id.equals("0");
    }

}
