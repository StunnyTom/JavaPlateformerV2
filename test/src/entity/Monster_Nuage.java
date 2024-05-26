package entity;

import test.GamePanel;

public class Monster_Nuage extends Monster {
    public Monster_Nuage(GamePanel gp) {
        super(gp, "/img_monster/monstre_nuage.png", 10, 0);
        initializePosition('N');
    }

    @Override
    public void update() {
        if (!isVisible) {
            return; // Ignore la suite si le monstre n'est pas visible
        }
        super.updateMovement(1);  
        checkPlayerInteraction();
    }

    @Override
    public void checkPlayerInteraction() {
        if (!isVisible) {
            return; // Arrêter l'exécution si le monstre n'est pas visible
        }

        if (isPlayerNear()) { // Si le joueur est proche du monstre
            gp.gameState.afficheGameOver(); // Terminer le jeu
            //System.out.println("Le joueur a touché le monstre nuage.");
        }
    }

    // Détermine si le joueur est suffisamment proche du monstre
    private boolean isPlayerNear() {
        Player player = gp.getPlayer();
        double distance = Math.sqrt(Math.pow(player.getScreenX() - this.screenX, 2) + Math.pow(player.getScreenY() - this.screenY, 2));
        //System.out.println(distance);
        return distance <= gp.tileSize; // Distance de détection
    }
}
