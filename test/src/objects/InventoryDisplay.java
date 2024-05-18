package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import entity.Entity;
import entity.Player;

import java.awt.Graphics2D;

public class InventoryDisplay extends JPanel {

    private static final long serialVersionUID = 1L;
    protected static final int NUM_SQUARES = 10;
    protected static final int SQUARE_SIZE = 40;

    private Entity entity; // Référence à l'entité pour obtenir l'inventaire
    private int selectedObjectIndex = -1; // Index de l'objet sélectionné dans l'inventaire (-1 signifie aucun objet sélectionné)

    public InventoryDisplay(Entity entity) {
        this.entity = entity; // Initialisez la référence à l'entité
        setPreferredSize(new Dimension(NUM_SQUARES * SQUARE_SIZE, SQUARE_SIZE));
        setBackground(new Color(255, 255, 255, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine les carrés représentant les objets de l'inventaire
        ArrayList<gameObject> inv = entity.getInv();
        for (int i = 0; i < inv.size(); i++) {
            int x = i * SQUARE_SIZE;
            g.setColor(new Color(255, 255, 255, 0));
            g.fillRect(x, 0, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, 0, SQUARE_SIZE, SQUARE_SIZE);
            // Dessine l'image de l'objet s'il existe dans cette case de l'inventaire
            gameObject obj = inv.get(i);
            if (obj != null && obj.getImage() != null) {
                BufferedImage img = obj.getImage();
                g.drawImage(img, x, 0, SQUARE_SIZE, SQUARE_SIZE, null);
            }
        }

        // Dessine une bordure autour de la case si elle est sélectionnée
        if (selectedObjectIndex >= 0 && selectedObjectIndex < inv.size()) {
            int x = selectedObjectIndex * SQUARE_SIZE;
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g;
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(3)); // Épaisseur de ligne de 3 pixels
            g2d.drawRect(x, 0, SQUARE_SIZE - 1, SQUARE_SIZE - 1); // Dessine une bordure autour de la case
            g2d.setStroke(oldStroke); // Restaure l'épaisseur de ligne précédente
        }
    }

    // Méthode pour définir l'index de l'objet sélectionné
    public void setSelectedObjectIndex(int index) {
        selectedObjectIndex = index;
        repaint(); // Redessinez le composant pour refléter le changement de sélection
    }
    
    
    
    public void removeSelectedObject() {
        ArrayList<gameObject> inv = entity.getInv();
        if (inv == null || inv.isEmpty()) {
            System.out.println("L'inventaire est vide.");
            return;
        }

        if (selectedObjectIndex >= 0 && selectedObjectIndex < inv.size()) {
            gameObject obj = inv.get(selectedObjectIndex);
            if (obj != null && entity instanceof Player) {
                Player player = (Player) entity;

                try {
                    player.useItem(obj.getID());

                    if (obj instanceof Usable && ((Usable) obj).isConsumable()) {
                        inv.remove(selectedObjectIndex);
                    }

                    // Ajuster l'index sélectionné
                    if (inv.size() == 0) {
                        selectedObjectIndex = -1;
                    } else if (selectedObjectIndex >= inv.size()) {
                        selectedObjectIndex = inv.size() - 1;
                    }
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage()); // Affiche le message d'erreur si l'utilisation est impossible
                }

                repaint(); // Redessinez pour refléter les changements
            } else {
                System.out.println("L'objet à l'index sélectionné est null ou n'est pas utilisable.");
            }
        } else {
            System.out.println("Index sélectionné invalide: " + selectedObjectIndex);
        }
    
    }
    
    
    
    
    
    
    /*
    // Méthode pour supprimer l'objet sélectionné
    public void removeSelectedObject() {
        ArrayList<gameObject> inv = entity.getInv();
        // Vérification initiale pour s'assurer que l'inventaire n'est pas vide et que l'index est valide
        if (inv == null || inv.isEmpty()) {
            System.out.println(" l'inventaire est vide.");
            //selectedObjectIndex = -1; // Réinitialiser l'index si nécessaire
            return;
        }
        
        //le cas si il selectionne un objet 
        if (selectedObjectIndex >= 0 && selectedObjectIndex < inv.size()) {
            gameObject obj = inv.get(selectedObjectIndex);
          
       // gameObject obj = inv.get(selectedObjectIndex);
        // Vérifie que l'objet à l'index sélectionné n'est pas nul et que l'entité est une instance de Player
        if (obj != null && entity instanceof Player) {
            Player player = (Player) entity;
            // Conditions spécifiques avant d'utiliser un objet, par exemple vérifier le type d'objet et les conditions de vie
            if (obj instanceof Apple && player.getLives() >= player.getMaxLives()) {
                System.out.println("Tu ne peux pas utiliser cet objet car tes vies sont pleines.");
                return;
            }
            // Utiliser l'objet
            player.useItem(obj.getID());
            // Vérifier si l'objet est consommable et alors le supprimer de l'inventaire
            if (obj instanceof Usable && ((Usable) obj).isConsumable()) {
                inv.remove(selectedObjectIndex); // Supprimer l'objet de manière sécurisée

             // Ajuster l'index sélectionné
                if (inv.size() == 0) {
                    selectedObjectIndex = -1;
                } else if (selectedObjectIndex >= inv.size()) {
                    selectedObjectIndex = inv.size() - 1;
                }
            }
        }

repaint();
        } else {
            System.out.println("Aucun objet trouvé à l'index sélectionné ou l'objet est null.");
        }
        repaint(); // Redessinez après modifications pour refléter les changements dans l'interface utilisateur
    }
*/


}