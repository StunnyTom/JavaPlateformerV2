package test;

import javax.swing.JFrame;

public class Main {
	
    private static JFrame fenetre;


    public static void main(String[] args) {
    	fenetre = new JFrame("Test 2D");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // on ferme le programme 
        fenetre.setResizable(false); // fenetre non redimensionnable
   
        initializeGame();
        
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }

    public static void initializeGame() {
    	 if (fenetre.getContentPane() instanceof GamePanel) {
             ((GamePanel) fenetre.getContentPane()).stopGameThread(); // Assurez-vous que cette méthode arrête correctement le thread de jeu
         }
    	 
    	GamePanel gamePanel = new GamePanel();
        fenetre.setContentPane(gamePanel);
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }
}
