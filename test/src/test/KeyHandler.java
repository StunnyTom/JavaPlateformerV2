package test;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.InventoryDisplay;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, leftPressed, rightPressed, downPressed; //creation de boolean pour verifier
	public int selectedObjectIndex = -1; // Index de l'objet sélectionné dans l'inventaire (-1 signifie aucun objet sélectionné)
	private InventoryDisplay inventoryDisplay; // Référence à l'InventoryDisplay pour appeler la méthode setSelectedObjectIndex
	public boolean onePressed, twoPressed;

    public KeyHandler(InventoryDisplay inventoryDisplay) {
        this.inventoryDisplay = inventoryDisplay;
    }
	//les 3 methodes sont obligatoires
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if (code ==  KeyEvent.VK_RIGHT) {
			rightPressed = true;	
		}
		if (code ==  KeyEvent.VK_LEFT) {
			leftPressed = true;			
				}
		if (code ==  KeyEvent.VK_UP) {
			upPressed = true;	
		}
		if (code ==  KeyEvent.VK_DOWN) {
			downPressed = true;	
		}
		
		// Sélection des objets de l'inventaire avec les touches numériques (0 à 9)
        if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
            selectedObjectIndex = code - KeyEvent.VK_0 - 1; // Soustraire le code de la touche numérique pour obtenir l'index (0-9)
            System.out.println(selectedObjectIndex);
            inventoryDisplay.setSelectedObjectIndex(selectedObjectIndex);
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
		if (code ==  KeyEvent.VK_DOWN) {
			downPressed = false;
		}
	}
}