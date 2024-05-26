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
        super(gp, "/img_monster/monstre_4.png", 5, 50);
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
    //interraction joueur monstre
    public void checkPlayerInteraction() {
        if (!interactionDelay.isRunning()) {
            String[] questions = {"Le Java c'est trop génial ?", "Tu es sûr de toi ?", "Vraiment ?"}; //questions
            String[] correctAnswers = {"oui", "oui", "non"};  //  réponses correctes pour chaque question

            for (int i = 0; i < questions.length; i++) {
                String question = questions[i];//on fait les questions dans une boucle
                String correctAnswer = correctAnswers[i];
                String response = JOptionPane.showInputDialog(null, question);
                if (!response.equalsIgnoreCase(correctAnswer)) {
                    player.loseLife(); // Le joueur perd une vie après une mauvaise réponse
                    JOptionPane.showMessageDialog(null, "Mauvaise réponse! Vies restantes: " + player.getLives());
                } else {
                    JOptionPane.showMessageDialog(null, "Bonne réponse!");
                }
            }
            interactionDelay.start();
        }
    }

}
