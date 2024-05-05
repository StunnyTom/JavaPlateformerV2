package test;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame fenetre = new JFrame();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // on ferme le programme 
		fenetre.setResizable(false); //fenetre non redimensionnable
		fenetre.setTitle("Test 2D"); //Nom de la fenetre
		
		//lancer la map
		GamePanel gamePanel = new GamePanel();
		fenetre.add(gamePanel);
		fenetre.pack();
	
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
		gamePanel.startGameThread();
	}

}
