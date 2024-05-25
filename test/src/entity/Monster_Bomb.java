package entity;

import test.GamePanel;

public class Monster_Bomb extends Monster {
    private boolean isVisible = true;
   
    public Monster_Bomb(GamePanel gp) {
        super(gp, "/img_monster/bomb.png", 5, 200);
        initializePosition('L');
    }

    @Override
    public void update() {
        updateMovement(1); // Use the shared movement update logic
        if (!isVisible) {
            return; // Skip update logic if the monster is not visible
        }
    }
    private void handleMonsterBomb(Monster_Bomb monster) {
        // Logique spécifique pour gérer Monster_Bomb
        // Par exemple, peut-être que le monstre attaque le joueur ou déclenche un événement spécial
        System.out.println("collision");
    }
    
}