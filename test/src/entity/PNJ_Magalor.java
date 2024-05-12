package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import objects.gameObject;
import test.GamePanel;

public class PNJ_Magalor extends PNJ {
    boolean hasDialogStarted = false;
    int attemptsLeft = 3;  // Nombre de tentatives autorisées
    int currentQuestion = 1;
    boolean hasMadeMistake = false; 

    public PNJ_Magalor(GamePanel gp) {
        super(gp, "/img_npj/PNJ_Magalor.png", 755, 335, 50);
    }

    //le pnj va lui poser plusieurs question
    public void triggerDialogue() {
        if (!hasDialogStarted && attemptsLeft > 0) {
            hasDialogStarted = true;
            String question = "";
            String correctAnswer = "";

            switch (currentQuestion) {
                case 1:
                    question = "Combien font 2+2 ?";
                    correctAnswer = "4";
                    break;
                case 2:
                    question = "Combien font 4+4 ?";
                    correctAnswer = "8";
                    break;
                case 3:
                    question = "Combien font 5+5 ?";
                    correctAnswer = "10";
                    break;
            }

            String playerResponse = JOptionPane.showInputDialog(null, question, "Question Mathématique", JOptionPane.QUESTION_MESSAGE);

            if (playerResponse != null && playerResponse.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(null, "Correct! Vous avez réussi. Passons à la prochaine question.");
                if (currentQuestion < 3) {
                    currentQuestion++;
                    attemptsLeft = 3;
                } else {
                    if (!hasMadeMistake) {
                        initializeItemToGive(); // initialisation de l'item que je veux donner
                        addItemToInventory(gp.player); // ajout dans l'inventaire
                        JOptionPane.showMessageDialog(null, "Voici un cadeau pour vous :)");
                    }
                    deactivateCollision(); // on enleve la collision avec le joueur pour eviter une boucle 
                    attemptsLeft = 0;
                }
            } else {
                hasMadeMistake = true;
                attemptsLeft--;
                JOptionPane.showMessageDialog(null, "Incorrect. Il vous reste " + attemptsLeft + " tentatives.");
            }

            if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(null, "Plus de tentatives restantes.");
                deactivateCollision(); 
                currentQuestion = 1; 
            }

            hasDialogStarted = false; 
        }
    }
    
    private void initializeItemToGive() {
        this.itemToGive = new gameObject(false);
        try {
            BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream("/objects/cle.png"));
            itemToGive.image = itemImage;
            itemToGive.setID("2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //A MODIFIER : je veux que la collision avec le pnj dure que 10 secondes
    private void deactivateCollision() {
        isCollisionWithPlayer = false;
       
    }
}
