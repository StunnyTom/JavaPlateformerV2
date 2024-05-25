package entity;

import test.GamePanel;

public class Monster_Bomb extends Monster {
    private long lastCollisionTime = 0; // Temps de la derni�re collision
    private static final long COLLISION_PAUSE = 4000; // Pause de 4 secondes entre les collisions

    public Monster_Bomb(GamePanel gp) {
        super(gp, "/img_monster/bomb.png", 5, 50);
        initializePosition('L');
    }

    @Override
    public void update() {
        // V�rification de l'interaction avec le joueur
        super.updateMovement(1);  // Assure la logique de mise � jour g�n�rale
        checkPlayerInteraction();
    }

    @Override
    public void checkPlayerInteraction() {
    	  if (!isVisible) {
              return; // Arr�ter l'ex�cution si le monstre n'est pas visible
          }

        long currentTime = System.currentTimeMillis();
        if (isPlayerNear()) {
            if (gp.getPlayer().isInvincible()) {
                this.setVisible(false); // Rendre le monstre invisible/dispara�tre
                System.out.println("Le monstre a disparu car le joueur est invincible.");
                return; // Arr�ter l'ex�cution de la m�thode si le monstre dispara�t
            } else if (currentTime - lastCollisionTime > COLLISION_PAUSE) {
                // Le joueur perd une vie si pas invincible et temps �coul� depuis derni�re collision
                gp.getPlayer().loseLife();
                lastCollisionTime = currentTime; // Mise � jour du temps de la derni�re collision
                System.out.println("Le monstre a touch� le joueur");
            }
        }
    }


    // D�termine si le joueur est suffisamment proche du monstre
    private boolean isPlayerNear() {
        Player player = gp.getPlayer(); // Obtenez une r�f�rence au joueur
        double distance = Math.sqrt(Math.pow(player.getScreenX() - this.L, 2) + Math.pow(player.getScreenY() - this.l, 2));
        //System.out.println(distance);
        return distance >= 800 && distance <= 900; 
    }
}
