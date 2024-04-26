package objects;
import java.awt.image.BufferedImage;

public class Object {
		
	public Object() {
		// TODO Auto-generated constructor stub
	}
	public BufferedImage image;
	public boolean collision = false; //colision
	public char id;
		
	   
    public Object(boolean collision) {
        this.collision = collision;
    }
}

