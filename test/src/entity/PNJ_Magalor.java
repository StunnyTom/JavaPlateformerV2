package entity;

import javax.swing.JOptionPane;
import test.GamePanel;

public class PNJ_Magalor extends PNJ {
    boolean hasDialogStarted = false;
    int attemptsLeft = 3;  // Nombre de tentatives autorisées

    public PNJ_Magalor(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 755, 335, 50);
    }

    public void triggerDialogue() {
        if (!hasDialogStarted && attemptsLeft > 0) {
            hasDialogStarted = true;
            String playerResponse = JOptionPane.showInputDialog(null, "Combien font 2+2 ?", "Question Mathématique", JOptionPane.QUESTION_MESSAGE);
            
            if (playerResponse != null && playerResponse.equals("4")) {
                JOptionPane.showMessageDialog(null, "Correct! Vous avez réussi.");
                attemptsLeft = -1; // Réussite : on arrête les tentatives
            } else {
                attemptsLeft--; // Réduire le nombre de tentatives restantes
                JOptionPane.showMessageDialog(null, "Incorrect. Il vous reste " + attemptsLeft + " tentatives.");
            }
            
            if (attemptsLeft <= 0) {
                JOptionPane.showMessageDialog(null, "Plus de tentatives restantes. La collision avec ce PNJ sera désactivée.");
                deactivateCollision(); // Méthode pour désactiver la collision
            }
            if (attemptsLeft == -1) {
                JOptionPane.showMessageDialog(null, "Voici un cadeau pour vous :)");
                deactivateCollision(); // Méthode pour désactiver la collision
            }

            hasDialogStarted = false;  // Réinitialiser pour permettre une nouvelle tentative si encore possible
        }
    }

    private void deactivateCollision() {
        isCollisionWithPlayer = false; // Assurez-vous que cette propriété est vérifiée dans votre système de collision
    }
}
