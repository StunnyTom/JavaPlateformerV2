package test;

import tiles.Tile;
import java.awt.Rectangle;
import generation.Generateur;

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
    public Generateur checkCollisionGen(int newX, int newY, int l, int L, Rectangle solidArea) {
        int entityLeftObj = (newX + solidArea.x) / gp.ObjetSize;
        int entityRightObj = (newX + solidArea.x + solidArea.width - L) / gp.ObjetSize;
        int entityTopObj = (newY + solidArea.y + l) / gp.ObjetSize;
        int entityBottomObj = (newY + solidArea.y + solidArea.height) / gp.ObjetSize;

        Generateur C1 = GenCollision(entityLeftObj, entityTopObj);
        Generateur C2 = GenCollision(entityRightObj, entityTopObj);
        Generateur C3 = GenCollision(entityLeftObj, entityBottomObj);
        Generateur C4 = GenCollision(entityRightObj, entityBottomObj);

        if (C1!=null) return C1;
        if (C2!=null) return C2;
        if (C3!=null) return C3;
        if (C4!=null) return C4;

        return null; // Retourner un gameObject "null"
    }

    private Generateur GenCollision(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxScreenCol || row >= gp.maxScreenRow) {
            return null; // Retourner un gameObject "null" pour les indices hors limites
        }

        String objId = gp.mapGenNum[col][row];
        Generateur object = gp.genMap.get(objId);

        if (object == null) {
            return null; // Retourner un gameObject "null" si aucun objet n'est trouvé
        }
        return object;
    }
}