package entity;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import test.GamePanel;

/* Ce monstre pose 3 questions, si il se trompe il perd de la vie */
public class Monster_Max extends Monster {
    private Timer interactionDelay; //timer comme dab
    private Player player;  

    public Monster_Max(GamePanel gp) {
        super(gp, "/img_monster/monstre_4.png", 5, 50,2);
        initializePosition('D'); 
        this.player = gp.getPlayer(); // Initialisation de player
        
        interactionDelay = new Timer(4000, new ActionListener() { // Initialisation du Timer
            @Override
            public void actionPerformed(ActionEvent e) {
                interactionDelay.stop();
            }
        });
    }

    @Override
    //Collision entre mon joueur et mon monstre
    public void checkPlayerInteraction() {
        if (!interactionDelay.isRunning() && this.getIsCollidable()) {
            String[] questions = {"Le Java c'est trop g�nial ?", "Tu es s�r de toi ?", "Vraiment ?"};
            String[] correctAnswers = {"oui", "oui", "non"};  // R�ponses correctes pour chaque question

            boolean allCorrect = true;  // Flag pour suivre si toutes les r�ponses sont correctes

            for (int i = 0; i < questions.length; i++) {
                String question = questions[i];
                String correctAnswer = correctAnswers[i];
                String response = JOptionPane.showInputDialog(null, question);
                if (response == null) {
                	JOptionPane.showMessageDialog(null, "Il faut r�pondre !");
                    return;  // Sortir de la m�thode si l'utilisateur annule l'interaction
                } else if (!response.equalsIgnoreCase(correctAnswer)) {
                    player.loseLife(); // Le joueur perd une vie imm�diatement apr�s une mauvaise r�ponse
                    JOptionPane.showMessageDialog(null, "Mauvaise r�ponse! Vies restantes: " + player.getLives());
                    allCorrect = false;  // Marquer comme fausse r�ponse
                } else {
                    JOptionPane.showMessageDialog(null, "Bonne r�ponse!");
                }
            }

            if (allCorrect) {
                player.addKey("KeyX");  // Ajouter une cl� au joueur si toutes les r�ponses sont correctes
                System.out.println("F�licitations ! Vous avez obtenu une nouvelle cl�.");
            }

            interactionDelay.start();
        }
    }

}
