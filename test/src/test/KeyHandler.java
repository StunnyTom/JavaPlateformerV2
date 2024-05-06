package test;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.InventoryDisplay;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, leftPressed, rightPressed, downPressed; //creation de boolean pour verifier
	public int selectedObjectIndex = -1; // Index de l'objet s�lectionn� dans l'inventaire (-1 signifie aucun objet s�lectionn�)
	private InventoryDisplay inventoryDisplay; // R�f�rence � l'InventoryDisplay pour appeler la m�thode setSelectedObjectIndex
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
		
		// S�lection des objets de l'inventaire avec les touches num�riques (0 � 9)
        if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
            selectedObjectIndex = code - KeyEvent.VK_0 - 1; // Soustraire le code de la touche num�rique pour obtenir l'index (0-9)
            System.out.println(selectedObjectIndex);
            inventoryDisplay.setSelectedObjectIndex(selectedObjectIndex);
        }
	}

	@Override
	public void keyReleased(KeyEvent e) { //quand la touche est relach� 
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