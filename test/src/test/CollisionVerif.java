package test;

import tiles.Tile;
import objects.Object;
import java.awt.Rectangle;

import entity.PNJ_bandana;

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
    private boolean tileCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxScreenCol || row >= gp.maxScreenRow) {
            //System.out.println("Position de tuile hors limites");
            return false;
        }

        // Obtenir le caract�re de la tuile et v�rifier pour la collision
        char tileChar = (char) (gp.tileM.mapTilenum[col][row] + 'a');
        Tile tile = gp.tileM.tileMap.get(tileChar);

        boolean collision = tile != null && tile.collision;
        return collision;
    }
    
    public boolean checkCollisionObject(int newX, int newY, int l, int L, Rectangle solidArea) {
        // Calculer les positions des tuiles en fonction de newX et newY
        int entityLeftObj = (newX + solidArea.x) / gp.ObjetSize;
        int entityRightObj = (newX + solidArea.x + solidArea.width - L) / gp.ObjetSize;
        int entityTopObj = (newY + solidArea.y + l) / gp.ObjetSize;
        int entityBottomObj = (newY + solidArea.y + solidArea.height) / gp.ObjetSize;

        boolean collisionDetected = false;

        // Vérifier les collisions uniquement avec les objets
        if (ObjCollision(entityLeftObj, entityTopObj) || ObjCollision(entityRightObj, entityTopObj) ||
            ObjCollision(entityLeftObj, entityBottomObj) || ObjCollision(entityRightObj, entityBottomObj)) {
 
            collisionDetected = true;

            // uniquement si la collision est détectée
            System.out.println("Collision avec un objet détectée ");
        }

        return collisionDetected; // Retourne true si une collision avec un objet a été détectée
    }
 
    private boolean ObjCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxScreenCol || row >= gp.maxScreenRow) {
            return false; // Hors des limites
        }

        // Obtenir le caractère de la tuile et vérifier pour la collision
        char objChar = (char) (gp.ObjectM.mapObjetnum[col][row] + 'a');
        Object object = gp.ObjectM.Objet_Map.get(objChar);

        if (object == null) {
            //System.out.println("Aucun objet trouvé");
            return false;
        }
        boolean collision = object.collision;
        return collision; // Retourne true si l'objet a une propriété de collision
    }

    public boolean checkCollisionPNJ(int newX, int newY, int l, int L, Rectangle solidArea) {
        Rectangle playerArea = new Rectangle(newX, newY, l, L);
        for (PNJ_bandana pnj : gp.listPNJ) {
            if (pnj.solidAir.intersects(playerArea)) {
            	pnj.isCollisionWithPlayer = true; // Activer le flag de collision
               // System.out.println("Collision");
                return true;
            }
            //System.out.println("pas de collision avec un pnj");
        }
        return false;
    }


    
}