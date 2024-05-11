package entity;

import javax.swing.JOptionPane;
import test.GamePanel;

public class PNJ_Magalor extends PNJ {
    boolean hasDialogStarted = false;
    int attemptsLeft = 3;  // Nombre de tentatives autoris�es

    public PNJ_Magalor(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 755, 335, 50);
    }

    public void triggerDialogue() {
        if (!hasDialogStarted && attemptsLeft > 0) {
            hasDialogStarted = true;
            String playerResponse = JOptionPane.showInputDialog(null, "Combien font 2+2 ?", "Question Math�matique", JOptionPane.QUESTION_MESSAGE);
            
            if (playerResponse != null && playerResponse.equals("4")) {
                JOptionPane.showMessageDialog(null, "Correct! Vous avez r�ussi.");
                attemptsLeft = -1; // R�ussite : on arr�te les tentatives
            } else {
                attemptsLeft--; // R�duire le nombre de tentatives restantes
                JOptionPane.showMessageDialog(null, "Incorrect. Il vous reste " + attemptsLeft + " tentatives.");
            }
            
            if (attemptsLeft <= 0) {
                JOptionPane.showMessageDialog(null, "Plus de tentatives restantes. La collision avec ce PNJ sera d�sactiv�e.");
                deactivateCollision(); // M�thode pour d�sactiver la collision
            }
            if (attemptsLeft == -1) {
                JOptionPane.showMessageDialog(null, "Voici un cadeau pour vous :)");
                deactivateCollision(); // M�thode pour d�sactiver la collision
            }

            hasDialogStarted = false;  // R�initialiser pour permettre une nouvelle tentative si encore possible
        }
    }

    private void deactivateCollision() {
        isCollisionWithPlayer = false; // Assurez-vous que cette propri�t� est v�rifi�e dans votre syst�me de collision
    }
}
