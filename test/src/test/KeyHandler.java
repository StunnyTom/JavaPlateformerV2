package test;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.InventoryDisplay;

public class KeyHandler implements KeyListener{
	
	//PERMET LES COMMANDES DES TOUCHES 
	public boolean upPressed, leftPressed, rightPressed, downPressed; //creation de boolean pour verifier
	public int selectedObjectIndex = -1; // Index de l'objet sélectionné dans l'inventaire (-1 signifie aucun objet sélectionné)
	private InventoryDisplay inventoryDisplay; // Référence à l'InventoryDisplay pour appeler la méthode setSelectedObjectIndex
	public GamePanel gp;

	public KeyHandler(InventoryDisplay inventoryDisplay, GamePanel gp) {
        this.inventoryDisplay = inventoryDisplay;
        this.gp = gp;  // Initialiser gp avec la référence passée
    }

	//les 3 methodes sont obligatoires
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	public void keyPressed(KeyEvent e) {
		  int code = e.getKeyCode();
		
		
		 // Gestion du jeu terminé
	    if (gp.gameState.isGameOver()) {
	        if (code == KeyEvent.VK_ENTER) {
	            gp.gameState.retryGame();  // Redémarrage du jeu
	        }
	        return; // Ignorer les autres entrées si le jeu est terminé
	    }
	    

	    if (code == KeyEvent.VK_ENTER && gp.gameState.isGameOver()) {
	        gp.gameState.retryGame();
	    } else {
	        // Gestion des autres touches
	        switch (code) {
	            case KeyEvent.VK_RIGHT:
	                rightPressed = true;
	                break;
	            case KeyEvent.VK_LEFT:
	                leftPressed = true;
	                break;
	            case KeyEvent.VK_UP:
	                upPressed = true;
	                break;
	        }
	    }

		// Sélection des objets de l'inventaire avec les touches numériques (0 à 9)
        if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
            selectedObjectIndex = code - KeyEvent.VK_0 - 1; // Saoustraire le code de la touche numérique pour obtenir l'index (0-9)
            System.out.println(selectedObjectIndex);
            inventoryDisplay.setSelectedObjectIndex(selectedObjectIndex);
        }
        
        // Suppression de l'objet sélectionné avec la touche 'Espace'
        if (code == KeyEvent.VK_SPACE) {
            inventoryDisplay.removeSelectedObject();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) { //quand la touche est relaché 
		int code = e.getKeyCode();
		
		if (code ==  KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code ==  KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code ==  KeyEvent.VK_UP) {
			upPressed = false;
		}
	}
	
	   public boolean isLeftPressed() {
	        return leftPressed;
	    }

	    public void setLeftPressed(boolean leftPressed) {
	        this.leftPressed = leftPressed;
	    }
	    public boolean isRightPressed() {
	        return rightPressed;
	    }

	    public void setRightPressed(boolean rightPressed) {
	        this.rightPressed = rightPressed;
	    }

	    public boolean isUpPressed() {
	        return upPressed;
	    }

	    public void setUpPressed(boolean upPressed) {
	        this.upPressed = upPressed;
	    }
}