package objects;
import java.awt.image.BufferedImage;

public class gameObject {
		
	public BufferedImage image;
	public boolean collision = false; //colision
	public String id;
		
	   
    public gameObject(boolean collision) {
        this.collision = collision;
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

