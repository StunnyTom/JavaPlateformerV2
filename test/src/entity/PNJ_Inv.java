package entity;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import objects.gameObject;
import test.GamePanel;

public class PNJ_Inv extends PNJ {
    private Timer collisionTimer;
    private boolean popupShown = false; // Ajouter un indicateur pour contrôler l'affichage de la popup

    public PNJ_Inv(GamePanel gp) {
        super(gp, "/img_npj/pnj_inv.png", 30);
        initializePosition('I');
        // Configuration du timer pour désactiver la collision pendant 15 secondes (15000 ms)
        collisionTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Réactiver les collisions après 15 secondes
                setCollisionWithPlayer(true);
                popupShown = false; // Réinitialiser l'indicateur d'affichage de la popup
            }
        });
        collisionTimer.setRepeats(false); // Le timer ne se répète pas automatiquement
    }

    @Override
    public void triggerDialog() {
        // System.out.println("collision");
    }
    @Override
    public void setCollisionWithPlayer(boolean isCollisionWithPlayer) {
    	 System.out.println("setCollisionWithPlayer called with: " + isCollisionWithPlayer);
        // On verifie si la collision est activée ou désactivée
        if (isCollisionWithPlayer) {
            if (!this.isCollisionWithPlayer() && !popupShown) {
                super.setCollisionWithPlayer(true);
                showInventoryPopup(); // Affiche l'inventaire du PNJ
                popupShown = true; // Marquer que la popup a été montrée
                // Désactive la collision et démarre le timer
                super.setCollisionWithPlayer(false);
                collisionTimer.start();
            }
        } else {
            super.setCollisionWithPlayer(false);
        }
    }

    /*
    private void exchangeItems() {
        // Obtenir l'inventaire du joueur
        ArrayList<gameObject> playerInventory = getPlayer().getInv();
        if (playerInventory.isEmpty() || inv.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun échange possible, un des inventaires est vide.", "Échange Impossible", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sélection aléatoire d'un objet dans chaque inventaire
        Random random = new Random();
        int playerItemIndex = random.nextInt(playerInventory.size());
        int pnjItemIndex = random.nextInt(inv.size());

        // Échange des objets
        gameObject temp = playerInventory.get(playerItemIndex);
        playerInventory.set(playerItemIndex, inv.get(pnjItemIndex));
        inv.set(pnjItemIndex, temp);

        // Affichage d'un message confirmant l'échange
        JOptionPane.showMessageDialog(null, "Échange effectué avec succès!\n " +
                                      "\nVous avez donné: " + temp.getClass().getSimpleName(), "Échange Réussi", JOptionPane.INFORMATION_MESSAGE);
       // System.out.println("Échange effectué : " + temp.getClass().getSimpleName() + " contre " + inv.get(pnjItemIndex).getClass().getSimpleName());
    }
*/
    /*
    private void showInventoryPopup() {
        if (getPlayer().getInv().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Reviens plus tard, tu n'as rien dans ton inventaire", "Inventaire Vide", JOptionPane.WARNING_MESSAGE);
            System.out.println("Inventaire vide ");
        } else {
            JDialog dialog = new JDialog();  // Crée un nouveau JDialog
            dialog.setTitle("Inventaire du PNJ");
            dialog.setModal(true);  // Assure que le dialogue bloque les autres fenêtres jusqu'à ce qu'il soit fermé

            // Contenu principal
            JPanel panel = new JPanel();
            panel.add(new JLabel("<html>Inventaire :<br/>"));
            for (gameObject item : inv) {
                panel.add(new JLabel(item.getClass().getSimpleName() + "<br/>"));
            }
            dialog.getContentPane().add(panel, BorderLayout.CENTER);
 
            // Boutons
            JPanel buttonPanel = new JPanel();
           
           /*
            JButton exchangeButton = new JButton("Échanger");
            exchangeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exchangeItems();
                    dialog.dispose();  // Ferme la boîte de dialogue après l'échange
                }
            });
            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();  // Ferme la boîte de dialogue
                }
            });
            
           // buttonPanel.add(exchangeButton);
            buttonPanel.add(cancelButton);
            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();  
            dialog.setLocationRelativeTo(null);  // Centre la boîte de dialogue sur l'écran
            dialog.setVisible(true);  // Rend la boîte de dialogue visible
        }
    }
    */

    
    private void showInventoryPopup() {
        // Vérifie si l'inventaire du PNJ est vide
        if (inv.isEmpty()) {
            // Affiche un message indiquant que le PNJ n'a rien
            JOptionPane.showMessageDialog(null, "Je n'ai rien", "Inventaire Vide", JOptionPane.WARNING_MESSAGE);
            System.out.println("Le PNJ n'a rien dans son inventaire.");
        } else {
            // Crée un nouveau JDialog pour montrer l'inventaire si le PNJ a des objets
            JDialog dialog = new JDialog();
            dialog.setTitle("Inventaire du PNJ");
            dialog.setModal(true);

            // Contenu principal affichant les items
            JPanel panel = new JPanel();
            panel.add(new JLabel("<html>Inventaire :<br/>"));
            for (gameObject item : inv) {
                panel.add(new JLabel(item.getClass().getSimpleName() + "<br/>"));
            }
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            // Boutons de contrôle
            JPanel buttonPanel = new JPanel();
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            buttonPanel.add(cancelButton);
            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    private Player getPlayer() {
        return gp.getPlayer();
    }
}
