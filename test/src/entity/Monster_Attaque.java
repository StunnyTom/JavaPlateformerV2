package entity;

import test.GamePanel;

public class Monster_Attaque extends Monster {
    private int lives = 3; // Vie du monstre

    public Monster_Attaque(GamePanel gp) {
        super(gp, "/img_monster/Monstre_attaque.png", 10, 0);
        initializePosition('A');
    }

    @Override
    public void checkPlayerInteraction() {
        if (!this.isVisible() || !this.isCollidable()) {
            return; // Si le monstre n'est pas visible ou collidable, aucune interaction ne doit avoir lieu
        }
        if (gp.getPlayer().isAttacking()) {
            // Appelle la méthode pour perdre une vie lorsque le joueur attaque
            this.loseLife();
        } else {
            gp.getPlayer().loseLife();
        }
        gp.getPlayer().moveBackwards(-50); // Fait reculer le joueur 
    }

    // Méthode modifiée pour perdre une vie
    public void loseLife() {
        lives--;
        System.out.println("Vie du monstre restante : " + lives);
        if (lives <= 0) {
            System.out.println("Le monstre est vaincu");
            disappear(); // Appelle la méthode pour faire disparaître le monstre
        }
    }
    
    // Méthode pour faire disparaître le monstre et le rendre non-collidable
    public void disappear() {
        this.setVisible(false);    // Rendre le monstre invisible
        this.setCollidable(false); // Rendre le monstre non-collidable
    }
}
