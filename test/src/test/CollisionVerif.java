package test;

import tiles.Tile;
import objects.gameObject;
import java.awt.Rectangle;



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
    
    // Vérification des collisions avec des objets basée sur les positions x, y et des dimensions données :
    public gameObject checkCollisionObject(int newX, int newY, int l, int L, Rectangle solidArea) {
        int entityLeftObj = (newX + solidArea.x) / gp.ObjetSize;
        int entityRightObj = (newX + solidArea.x + solidArea.width - L) / gp.ObjetSize;
        int entityTopObj = (newY + solidArea.y + l) / gp.ObjetSize;
        int entityBottomObj = (newY + solidArea.y + solidArea.height) / gp.ObjetSize;

        gameObject C1 = ObjCollision(entityLeftObj, entityTopObj);
        gameObject C2 = ObjCollision(entityRightObj, entityTopObj);
        gameObject C3 = ObjCollision(entityLeftObj, entityBottomObj);
        gameObject C4 = ObjCollision(entityRightObj, entityBottomObj);

        if (!C1.isNullObject()) return C1;
        if (!C2.isNullObject()) return C2;
        if (!C3.isNullObject()) return C3;
        if (!C4.isNullObject()) return C4;

        return new gameObject(false); // Retourner un gameObject "null"
    }

    private gameObject ObjCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxScreenCol || row >= gp.maxScreenRow) {
            return new gameObject(false); // Retourner un gameObject "null" pour les indices hors limites
        }

        String objId = gp.ObjectM.mapObjetnum[col][row];
        gameObject object = gp.ObjectM.Objet_Map.get(objId);

        if (object == null) {
            return new gameObject(false); // Retourner un gameObject "null" si aucun objet n'est trouvé
        }
        return object;
    }


    /*
    public boolean checkCollisionPNJ(int newX, int newY, int l, int L, Rectangle solidArea) {
        Rectangle playerArea = new Rectangle(newX, newY, l, L);
        if (gp.currentMap.equals("/maps/maps1.txt")) {
            if (gp.pnj_susie != null && gp.pnj_susie.getSolidAir() != null && gp.pnj_susie.getSolidAir().intersects(playerArea) && System.currentTimeMillis() - gp.pnj_susie.getLastCollisionTime() > PNJ_Susie.getCollisionCooldown()) {
                gp.pnj_susie.setCollisionWithPlayer(true);
                gp.pnj_susie.showInventoryDialog();
                return true;
            }
        }
        
        if (gp.currentMap.equals("/maps/maps2.txt")) {
            if (gp.pnj_bandana != null && gp.pnj_bandana.getSolidAir() != null && gp.pnj_bandana.getSolidAir().intersects(playerArea)) {
                gp.pnj_bandana.setCollisionWithPlayer(true);
                gp.pnj_bandana.triggerDialogue();
                gp.pnj_bandana.addItemToInventory(gp.player);
                System.out.println("Collision avec test_bandana");
                return true;
            }
        }
    
        return false;
	}
     */
    

    
        /*// Vérifier la collision pour PNJ_Magalor sur "map3.txt"
        if (gp.currentMap.equals("/maps/maps1.txt")) {
            if (gp.pnj_magalor != null && gp.pnj_magalor.solidAir.intersects(playerArea)) {
                gp.pnj_magalor.isCollisionWithPlayer = true;
                gp.pnj_magalor.triggerDialogue();
             // System.out.println("Collision avec PNJ_Magalor");
                
                System.out.println("Collision avec test_bandana");
                return true;
            	}
        } */   
   
  
}