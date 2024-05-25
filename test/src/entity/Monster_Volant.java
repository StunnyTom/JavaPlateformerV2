	package entity;

	import test.GamePanel;

import javax.swing.JOptionPane;
	import objects.gameObject;

import java.awt.Graphics2D;
import java.util.Random;

	/* Ce monstre, vole tout simplement un objet au hasard dans l'inventaire de mon joueur */

		public class Monster_Volant extends Monster {
			private long lastCollisionTime = 0;
		    private boolean interactionInProgress = false; // Ajout d'un flag pour contrôler les interactions
		    public boolean collidable = true;
		    private static final long COLLISION_COOLDOWN = 5000; // 5 secondes entre les collisions

		    public Monster_Volant(GamePanel gp) {
		        super(gp, "/img_monster/monstre_volant.png", 5, 500);
		        initializePosition('V');
		        
		        
		    }
		    @Override
		    public void update() {
		    	 super.updateMovement(1);
		         long currentTime = System.currentTimeMillis();
		         if (currentTime - lastCollisionTime > COLLISION_COOLDOWN && interactionInProgress) {
		             this.collidable = true;
		             interactionInProgress = false; // Réinitialiser le flag après le cooldown
		             System.out.println("Collision est de nouveau possible");
		         }
		     }

		    //lorsqu'il y a la collision avec mon joueur, j'affiche la pop, up si il y a un objet alors il prends un objet au hasard
		    //sinon je laisse + j'enleve la collision pendant 5 secondes 
		    public void checkPlayerInteraction() {
		        long currentTime = System.currentTimeMillis();
		        if (this.collidable && !interactionInProgress && currentTime - lastCollisionTime > COLLISION_COOLDOWN) {
		            interactionInProgress = true; // Définir le flag lors de l'interaction
		            if (!((Entity) this.gp.player).getInv().isEmpty()) {
		                Random rand = new Random();
		                int randomIndex = rand.nextInt(((Entity) this.gp.player).getInv().size());
		                gameObject item = ((Entity) this.gp.player).getInv().remove(randomIndex);
		                JOptionPane.showMessageDialog(null, "Je te vole ton objet: " + item.getNom(), "Attention", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(null, "Je ne te vole rien ton inv est vide!", "Attention", JOptionPane.WARNING_MESSAGE);
		            }
		            this.setLastCollisionTime(currentTime);
		            this.collidable = false; // Désactiver la collision immédiatement après l'interaction
		        }
		    }
		    

		    public void setLastCollisionTime(long time) {
		        this.lastCollisionTime = time;
		    }

		    public boolean isCollidable() {
		        return this.collidable;
		    }
		

		
		}
		