package entity;

import javax.swing.JOptionPane;
import test.GamePanel;

public class PNJ_Magalor extends PNJ {
    boolean hasDialogStarted = false;
    int attemptsLeft = 3;  // Nombre de tentatives autorisées
    int currentQuestion = 1;

    public PNJ_Magalor(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 755, 335, 50);
    }

    public void triggerDialogue() {
        if (!hasDialogStarted && attemptsLeft > 0) {
            hasDialogStarted = true;
            String question = "";
            String correctAnswer = "";

            if (currentQuestion == 1) {
                question = "Combien font 2+2 ?";
                correctAnswer = "4";
            } else if (currentQuestion == 2) {
                question = "Combien font 4+4 ?";
                correctAnswer = "8";
            } else if (currentQuestion == 3) {
                question = "Combien font 5+5 ?";
                correctAnswer = "10";
            }

            String playerResponse = JOptionPane.showInputDialog(null, question, "Question Mathématique", JOptionPane.QUESTION_MESSAGE);

            if (playerResponse != null && playerResponse.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(null, "Correct! Vous avez réussi.");
                if (currentQuestion < 3) {
                    currentQuestion++;  // Passer à la question suivante
                    attemptsLeft = 3;  // Réinitialiser les tentatives pour la nouvelle question
                    JOptionPane.showMessageDialog(null, "Passons à la prochaine question.");
                } else {
                    deactivateCollision(); // Désactiver immédiatement la collision
                    attemptsLeft = 0; // Réussite : on arrête les tentatives
                    JOptionPane.showMessageDialog(null, "Voici un cadeau pour vous :)");
                }
            } else {
                attemptsLeft--; // Réduire le nombre de tentatives restantes
                JOptionPane.showMessageDialog(null, "Incorrect. Il vous reste " + attemptsLeft + " tentatives.");
            }

            if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(null, "Plus de tentatives restantes.");
                deactivateCollision(); // Méthode pour désactiver la collision
                currentQuestion = 1;  // Réinitialiser le suivi des questions
            }

            hasDialogStarted = false;  // Réinitialiser pour permettre une nouvelle tentative si encore possible
        }
    }
    private void deactivateCollision() {
        isCollisionWithPlayer = false; // Assurez-vous que cette propriété est vérifiée dans votre système de collision
    }
}
