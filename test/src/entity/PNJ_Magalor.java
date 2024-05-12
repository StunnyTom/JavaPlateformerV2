package entity;

import javax.swing.JOptionPane;
import test.GamePanel;

public class PNJ_Magalor extends PNJ {
    boolean hasDialogStarted = false;
    int attemptsLeft = 3;  // Nombre de tentatives autoris�es
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

            String playerResponse = JOptionPane.showInputDialog(null, question, "Question Math�matique", JOptionPane.QUESTION_MESSAGE);

            if (playerResponse != null && playerResponse.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(null, "Correct! Vous avez r�ussi.");
                if (currentQuestion < 3) {
                    currentQuestion++;  // Passer � la question suivante
                    attemptsLeft = 3;  // R�initialiser les tentatives pour la nouvelle question
                    JOptionPane.showMessageDialog(null, "Passons � la prochaine question.");
                } else {
                    deactivateCollision(); // D�sactiver imm�diatement la collision
                    attemptsLeft = 0; // R�ussite : on arr�te les tentatives
                    JOptionPane.showMessageDialog(null, "Voici un cadeau pour vous :)");
                }
            } else {
                attemptsLeft--; // R�duire le nombre de tentatives restantes
                JOptionPane.showMessageDialog(null, "Incorrect. Il vous reste " + attemptsLeft + " tentatives.");
            }

            if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(null, "Plus de tentatives restantes.");
                deactivateCollision(); // M�thode pour d�sactiver la collision
                currentQuestion = 1;  // R�initialiser le suivi des questions
            }

            hasDialogStarted = false;  // R�initialiser pour permettre une nouvelle tentative si encore possible
        }
    }
    private void deactivateCollision() {
        isCollisionWithPlayer = false; // Assurez-vous que cette propri�t� est v�rifi�e dans votre syst�me de collision
    }
}
