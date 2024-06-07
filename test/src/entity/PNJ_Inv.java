package entity;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import objects.Potion;
//import java.util.ArrayList;
//import java.util.Random;
import objects.gameObject;
import test.GamePanel;

public class PNJ_Inv extends PNJ {
    private Timer collisionTimer;
    private boolean popupShown = false; // Ajouter un indicateur pour contr�ler l'affichage de la popup

    public PNJ_Inv(GamePanel gp) {
        super(gp, "/img_npj/pnj_inv.png", 30);
        initializePosition('I');
        
       
        // Configuration du timer pour d�sactiver la collision pendant 15 secondes (15000 ms)
        collisionTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // R�activer les collisions apr�s 15 secondes
                setCollisionWithPlayer(true);
                popupShown = false; // R�initialiser l'indicateur d'affichage de la popup
            }
        });
        collisionTimer.setRepeats(false); // Le timer ne se r�p�te pas automatiquement
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();  // Appelle la m�thode de la classe de base pour ajouter les objets par d�faut.

        // Ajouter des objets suppl�mentaires sp�cifiques � PNJ_Inv
        this.inv.add(new Potion(gp));  // Ajoutez une potion avec le constructeur appropri�.
    }
    @Override
    public void triggerDialog() {
        // System.out.println("collision");
    }
    @Override
    public void setCollisionWithPlayer(boolean isCollisionWithPlayer) {
    	// System.out.println("setCollisionWithPlayer called with: " + isCollisionWithPlayer);
        // On verifie si la collision est activ�e ou d�sactiv�e
        if (isCollisionWithPlayer) {
            if (!this.isCollisionWithPlayer() && !popupShown) {
                super.setCollisionWithPlayer(true);
                showInventoryPopup(); // Affiche l'inventaire du PNJ
                popupShown = true; // Marquer que la popup a �t� montr�e
                // D�sactive la collision et d�marre le timer
                super.setCollisionWithPlayer(false);
                collisionTimer.start();
            }
        } else {
            super.setCollisionWithPlayer(false);
        }
    }

    //PERMET DE VOIR UNIQUEMENT L INVENTAIRE DU PNJ 
    private void showInventoryPopup() {
    	 if (inv.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "Je n'ai rien � te montrer.", "Inventaire du PNJ vide", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Inventaire vide");
        } else {
            JDialog dialog = new JDialog();
            dialog.setTitle("Inventaire du PNJ");
            dialog.setModal(true);

            // Contenu principal
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("<html><h1>Inventaire du PNJ :</h1><br/>"), BorderLayout.NORTH);

            // Ajout des items de l'inventaire dans le dialogue
            StringBuilder itemList = new StringBuilder("<html>");
            for (gameObject item : inv) {
                itemList.append(item.getClass().getSimpleName()).append("<br/>");
            }
            itemList.append("</html>");
            panel.add(new JLabel(itemList.toString()), BorderLayout.CENTER);

            // Bouton de fermeture
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dialog.dispose());
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(closeButton);

            dialog.getContentPane().add(panel, BorderLayout.CENTER);
            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    @SuppressWarnings("unused")
	private Player getPlayer() {
        return gp.getPlayer();
    }
    
    
    /*FONCTION QUI PERMET D ECHANGER UN ITEM AVEC LE JOUEUR EN SELECTIONNANT L ITEM DU PNJ
     * MAIS CHOIX ALEATOIRE POUR MOI 
     
    private void exchangeItems() {
        // Obtenir l'inventaire du joueur
        ArrayList<gameObject> playerInventory = getPlayer().getInv();
        if (playerInventory.isEmpty() || inv.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun �change possible, un des inventaires est vide.", "�change Impossible", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // S�lection al�atoire d'un objet dans chaque inventaire
        Random random = new Random();
        int playerItemIndex = random.nextInt(playerInventory.size());
        int pnjItemIndex = random.nextInt(inv.size());

        // �change des objets
        gameObject temp = playerInventory.get(playerItemIndex);
        playerInventory.set(playerItemIndex, inv.get(pnjItemIndex));
        inv.set(pnjItemIndex, temp);

        // Affichage d'un message confirmant l'�change
        JOptionPane.showMessageDialog(null, "�change effectu� avec succ�s!\n " +
                                      "\nVous avez donn�: " + temp.getClass().getSimpleName(), "�change R�ussi", JOptionPane.INFORMATION_MESSAGE);
       // System.out.println("�change effectu� : " + temp.getClass().getSimpleName() + " contre " + inv.get(pnjItemIndex).getClass().getSimpleName());
    }

    private void showInventoryPopup() {
        if (getPlayer().getInv().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Reviens plus tard, tu n'as rien dans ton inventaire", "Inventaire Vide", JOptionPane.WARNING_MESSAGE);
            System.out.println("Inventaire vide ");
        } else {
            JDialog dialog = new JDialog();  // Cr�e un nouveau JDialog
            dialog.setTitle("Inventaire du PNJ");
            dialog.setModal(true);  // Assure que le dialogue bloque les autres fen�tres jusqu'� ce qu'il soit ferm�

            // Contenu principal
            JPanel panel = new JPanel();
            panel.add(new JLabel("<html>Inventaire :<br/>"));
            for (gameObject item : inv) {
                panel.add(new JLabel(item.getClass().getSimpleName() + "<br/>"));
            }
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            // Boutons
            JPanel buttonPanel = new JPanel();
           
           
            JButton exchangeButton = new JButton("�changer");
            exchangeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exchangeItems();
                    dialog.dispose();  // Ferme la bo�te de dialogue apr�s l'�change
                }
            });
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();  // Ferme la bo�te de dialogue
                }
            });
            
            buttonPanel.add(exchangeButton);
            buttonPanel.add(cancelButton);
            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();  
            dialog.setLocationRelativeTo(null);  // Centre la bo�te de dialogue sur l'�cran
            dialog.setVisible(true);  // Rend la bo�te de dialogue visible
        }
    }
*/
    
}
