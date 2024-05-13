package test;

import tiles.Tile;
import objects.gameObject;
import java.awt.Rectangle;
import entity.PNJ_Susie;


public class CollisionVerif {
    GamePanel gp;

    public CollisionVerif(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkCollision(int newX, int newY, int l, int L, Rectangle solidArea) {
        // Calculer les positions des tuiles en fonction de newX et newY
        int entityLeftTile = (newX + solidArea.x) / gp.tileSize;
        int entityRightTile = (newX + solidArea.x + solidArea.width - L) / gp.tileSize;
        int entityTopTile = (newY + solidArea.y + l) / gp.tileSize;
        int entityBottomTile = (newY + solidArea.y + solidArea.height) / gp.tileSize;

        // V�rifier les collisions par rapport a mon perso chaque coin de tuile
        if (tileCollision(entityLeftTile, entityTopTile) || tileCollision(entityRightTile, entityTopTile) ||
            tileCollision(entityLeftTile, entityBottomTile) || tileCollision(entityRightTile, entityBottomTile)) {
            //System.out.println("Collision detectee ");
            return true; // Collision d�tect�e
        }
        //System.out.println("Aucune collision detecte ");
        return false; // Pas de collision
    }

    //verif si le perso est en dehors des limites
    boolean tileCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) {
        	System.out.println("Position de tuile hors limites: col=" + col + ", row=" + row);
            gp.gameState.afficheGameOver();
            return false;
        }
        // Obtenir le caract�re de la tuile et v�rifier pour la collision
        char tileChar = (char) (gp.tileM.mapTilenum[col][row] + 'a');
        Tile tile = gp.tileM.tileMap.get(tileChar);
        boolean collision = tile != null && tile.collision;
        return collision;
    }
    
    public gameObject checkCollisionObject(int newX, int newY, int l, int L, Rectangle solidArea) {
        // Calculer les positions des tuiles en fonction de newX et newY
        int entityLeftObj = (newX + solidArea.x) / gp.ObjetSize;
        int entityRightObj = (newX + solidArea.x + solidArea.width - L) / gp.ObjetSize;
        int entityTopObj = (newY + solidArea.y + l) / gp.ObjetSize;
        int entityBottomObj = (newY + solidArea.y + solidArea.height) / gp.ObjetSize;

        //boolean collisionDetected = false;

        // Vérifier les collisions uniquement avec les objets
        gameObject C1 = ObjCollision(entityLeftObj, entityTopObj);
        gameObject C2 = ObjCollision(entityRightObj, entityTopObj);
        gameObject C3 = ObjCollision(entityLeftObj, entityBottomObj);
        gameObject C4 = ObjCollision(entityRightObj, entityBottomObj);
        
        if (!C1.nullObj()) {
        	System.out.println("Collision avec un objet détectée ");
        	return C1;
        }
        
        if (!C2.nullObj()) {
        	System.out.println("Collision avec un objet détectée ");
        	return C2;
        }
        
        if (!C3.nullObj()) {
        	System.out.println("Collision avec un objet détectée ");
        	return C3;
        }
        
        if (!C4.nullObj()) {
        	System.out.println("Collision avec un objet détectée ");
        	return C4;
        }
        
        return C1;
    }
 
    private gameObject ObjCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxScreenCol || row >= gp.maxScreenRow) {
        	gameObject newOb = new gameObject(false);
        	newOb.setID("0");
            return newOb; // Hors des limites
        }

        // Obtenir le caractère de la tuile et vérifier pour la collision
        char key = (char) (gp.ObjectM.mapObjetnum[col][row] + 'a');
        String objChar = "" + key;
        gameObject object = gp.ObjectM.Objet_Map.get(objChar);

        if (object == null) {
            //System.out.println("Aucun objet trouvé");
        	gameObject newOb = new gameObject(false);
        	newOb.setID(Integer.toString(0));
            return newOb;
        }
        //boolean collision = object.collision;
        return object; // Retourne true si l'objet a une propriété de collision
    }

    public boolean checkCollisionPNJ(int newX, int newY, int l, int L, Rectangle solidArea) {
        Rectangle playerArea = new Rectangle(newX, newY, l, L);
        // Vérifier la collision pour PNJ_bandana sur "map2.txt"     
        if (gp.currentMap.equals("/maps/maps1.txt")) {
            if (gp.pnj_bandana != null && gp.pnj_bandana.solidAir.intersects(playerArea)) {
                gp.pnj_bandana.isCollisionWithPlayer = true;
                gp.pnj_bandana.triggerDialogue();
                gp.pnj_bandana.addItemToInventory(gp.player);
                
                System.out.println("Collision avec test_bandana");
                return true;
            }
           
        }
    
        // Vérifier la collision pour PNJ_Magalor sur "map3.txt"
        if (gp.currentMap.equals("/maps/maps1.txt")) {
            if (gp.pnj_magalor != null && gp.pnj_magalor.solidAir.intersects(playerArea)) {
                gp.pnj_magalor.isCollisionWithPlayer = true;
                gp.pnj_magalor.triggerDialogue();
             // System.out.println("Collision avec PNJ_Magalor");
                
                System.out.println("Collision avec test_bandana");
                return true;
            	}
        }
        
      
        if (gp.currentMap.equals("/maps/maps1.txt")) {
        	if( gp.pnj_susie != null && gp.pnj_susie.solidAir.intersects(playerArea)&& System.currentTimeMillis() - gp.pnj_susie.lastCollisionTime > PNJ_Susie.COLLISION_COOLDOWN) {  
        		gp.pnj_susie.isCollisionWithPlayer = true;
        		gp.pnj_susie.showInventoryDialog(); // Affiche la boîte de dialogue si la période de cooldown est passée
                    return true;
                }
        	}
            
        
        // Aucune collision avec les PNJ car ils ne sont pas sur la carte actuelle
        return false;
    }   
}