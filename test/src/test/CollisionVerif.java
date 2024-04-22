package test;

import tiles.Tile;
import java.awt.Rectangle;

import entity.Entity;

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
    
    
    //public int checkObject(Entity entity, boolean player) {
    public int checkObject(Entity entity, boolean player) {
    
        int index = 10; // Utilisé comme valeur non trouvée / par défaut
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Calculer les positions absolues de la zone de collision de l'entité
                int entityLeft = entity.screenX + entity.solidAir.x;
                int entityTop = entity.screenY + entity.solidAir.y;
                int entityRight = entityLeft + entity.solidAir.width;
                int entityBottom = entityTop + entity.solidAir.height;
                
                System.out.println("entité gauche : " + entityLeft);

                // Calculer les positions absolues de la zone de collision de l'objet
                int objectLeft = gp.obj[i].objectX + gp.obj[i].solidAir.x;
                int objectTop = gp.obj[i].objectY + gp.obj[i].solidAir.y;
                int objectRight = objectLeft + gp.obj[i].solidAir.width;
                int objectBottom = objectTop + gp.obj[i].solidAir.height;
                
                System.out.println("l'objet gauche : " + objectLeft);

                // Vérification de la collision
                boolean collision = entityRight > objectLeft && entityLeft < objectRight && entityBottom > objectTop && entityTop < objectBottom;                      
                if (collision) {
                    System.out.println("Collision détectée avec l'objet à l'indice: " + i);
                    return i; // Retourner l'indice de l'objet en collision
                } else {
                    // Log si pas de collision avec cet objet spécifique
                    System.out.println("Pas de collision avec l'objet ");
                }
            }
        }
        return index; // Retourner index si aucune collision n'est détectée
    }


   
}