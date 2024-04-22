package test;

import objects.Obj_Chest;
import objects.Obj_key;

public class ObjSetter {
	
	GamePanel gp;
	
	//on recoit GamePanel 
	public ObjSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	//methode on utilise un tableau d'objet 
	public void setObjet() { //instencifier des objets par default et les placer sur la carte
		
		//position du premier objet
		gp.obj[0] = new Obj_key();
		gp.obj[0].objectX = 19 * gp.tileSize;
		gp.obj[0].objectY = 11 * gp.tileSize;
		
		//position du coffre 
		gp.obj[1] = new Obj_Chest();
		gp.obj[1].objectX = 13 * gp.tileSize;
		gp.obj[1].objectY = 13 * gp.tileSize;

		
	}
	
}