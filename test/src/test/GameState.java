package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

//L ETAT DU JEU? PERDU? VAINQUEUR
public class GameState {
    private GamePanel gp;
    private boolean isGameOver = false;
    private boolean isVictory = false;
    public int commandNum = 0; 

    public GameState(GamePanel gp) {
        this.gp = gp;
    }

    public void afficheGameOver() {
        isGameOver = true;
    }

    public void afficheVictory() {
        isVictory = true;
        JOptionPane.showMessageDialog(null, "Félicitations ! ");
    }

    public void retryGame() {
        isGameOver = false;
        isVictory = false;
        Main.initializeGame();  // Appelle la nouvelle méthode pour réinitialiser le jeu
    }

    public boolean isGameOver() {
        return isGameOver || isVictory;
    }
    
    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
        if (gameOver) {
        	//System.out.println("Le jeu est terminé.");
            JOptionPane.showMessageDialog(null, "Game Over! Vous avez perdu toutes vos vies.");
        }
    }

    public int getXForCenteredText(String text, Graphics2D g2) {
        int stringWidth = g2.getFontMetrics().stringWidth(text);
        return (gp.screenWidth - stringWidth) / 2;
    }
    
    //affichage
    public void drawGameOverScreen(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String text = isVictory ? "Bravo ! " : "Game Over";
        int x = getXForCenteredText(text, g2);
        int y = gp.tileSize * 4;
        g2.setColor(Color.red);
        g2.drawString(text, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String retryText = "";
        x = getXForCenteredText(retryText, g2);
        y += gp.tileSize * 2;
        g2.setColor(Color.white);
        g2.drawString(retryText, x, y);
    }
}
