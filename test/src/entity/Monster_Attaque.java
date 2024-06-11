package entity;

import test.GamePanel;

//Monstre statique
public class Monster_Attaque extends Monster {
    private int lives = 3; // Vie du monstre
   
    private boolean collidable = true;

    //Constructeur qui initialise le monstre et sa position
    public Monster_Attaque(GamePanel gp) {
        super(gp, "/img_monster/Monstre_attaque.png", 10, 0, 3);
        initializePosition('A');
    }

    //M�thode commune qui g�re l'interaction avec le joueur
    @Override
    public void checkPlayerInteraction() {
        // Ajouter une v�rification pour s'assurer que le monstre est visible et collidable avant de proc�der
        if ( !this.isCollidable()) {
            return; // Si le monstre n'est pas visible ou collidable, aucune interaction ne doit avoir lieu
        }

        if (gp.getPlayer().isAttacking()) {
            this.loseLife();
        } else {
            gp.getPlayer().loseLife();
        }
        gp.getPlayer().moveBackwards(-50); // Fait reculer le joueur de 5 "pas"
    }

    // M�thode modifi�e pour perdre une vie
    public void loseLife() {
        lives--;
        System.out.println("Vie du monstre restante : " + lives);
        if (lives <= 0) {
            System.out.println("Le monstre est vaincu");
            disappear(); // Appelle la m�thode pour faire dispara�tre le monstre
        }
    }

    // M�thode pour faire dispara�tre le monstre et le rendre non-collidable
    public void disappear() {
        setVisible(false);    // Rendre le monstre invisible
        setCollidable(false); // Rendre le monstre non-collidable
        // Vous pouvez �galement appeler ici une m�thode pour l�cher une r�compense si n�cessaire
    }



    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }
}
