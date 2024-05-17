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
    
    // Méthode pour supprimer l'objet sélectionné
    public void removeSelectedObject() {
        ArrayList<gameObject> inv = entity.getInv();
        if (inv == null || inv.isEmpty()) { // le cas ou si l'inventaire est vide 
            System.out.println("L'inventaire est vide.");
            return;
        }

        //le cas si il selectionne un objet de l'inventaire
        if (selectedObjectIndex >= 0 && selectedObjectIndex < inv.size()) {
            gameObject obj = inv.get(selectedObjectIndex);
            if (obj != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (obj instanceof Apple && player.getLives() >= player.getMaxLives()) {
                        System.out.println("Tu ne peux pas utiliser cet objet");
                        return;
                    }
                    player.useItem(obj.getID());

                    if (obj instanceof Usable && ((Usable) obj).isConsumable()) {
                        inv.remove(selectedObjectIndex);

                        if (inv.size() == 0) {
                            selectedObjectIndex = -1;
                        } else if (selectedObjectIndex >= inv.size()) {
                            selectedObjectIndex = inv.size() - 1;
                        }
                    }
                }

                repaint();
            } else {
                System.out.println("L'objet à l'index sélectionné est null.");
            }
        } else {
            System.out.println("Index sélectionné invalide: " + selectedObjectIndex);
        }
    }
}