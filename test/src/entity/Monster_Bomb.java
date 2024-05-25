package entity;

import test.GamePanel;

public class Monster_Bomb extends Monster {
    private long lastCollisionTime = 0; // Temps de la dernière collision
    private static final long COLLISION_PAUSE = 4000; // Pause de 4 secondes entre les collisions

    public Monster_Bomb(GamePanel gp) {
        super(gp, "/img_monster/bomb.png", 5, 50);
        initializePosition('L');
    }

    @Override
    public void update() {
        // Vérification de l'interaction avec le joueur
        super.updateMovement(1);  // Assure la logique de mise à jour générale
        checkPlayerInteraction();
    }

    @Override
    public void checkPlayerInteraction() {
    	  if (!isVisible) {
              return; // Arrêter l'exécution si le monstre n'est pas visible
          }

        long currentTime = System.currentTimeMillis();
        if (isPlayerNear()) {
            if (gp.getPlayer().isInvincible()) {
                this.setVisible(false); // Rendre le monstre invisible/disparaître
                System.out.println("Le monstre a disparu car le joueur est invincible.");
                return; // Arrêter l'exécution de la méthode si le monstre disparaît
            } else if (currentTime - lastCollisionTime > COLLISION_PAUSE) {
                // Le joueur perd une vie si pas invincible et temps écoulé depuis dernière collision
                gp.getPlayer().loseLife();
                lastCollisionTime = currentTime; // Mise à jour du temps de la dernière collision
                System.out.println("Le monstre a touché le joueur");
            }
        }
    }


    // Détermine si le joueur est suffisamment proche du monstre
    private boolean isPlayerNear() {
        Player player = gp.getPlayer(); // Obtenez une référence au joueur
        double distance = Math.sqrt(Math.pow(player.getScreenX() - this.L, 2) + Math.pow(player.getScreenY() - this.l, 2));
        //System.out.println(distance);
        return distance >= 800 && distance <= 900; 
    }
}
