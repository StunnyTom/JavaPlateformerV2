package entity;

import test.GamePanel;

/*
 * //ESSAYER Dans METTRE PLUSIEURS SUR LA MEME MAPS 
 * Impossible de le tuer sauf avec l'etoile invincible
 * on perd juste de la vie si on le touche 
 */
public class Monster_Nuage extends Monster{
		public Monster_Nuage(GamePanel gp) {
		 super(gp, "/img_monster/monstre_nuage.png", 10,0); 
	     initializePosition('N');
		}
		
		@Override
		public void checkPlayerInteraction() {
			// TODO Auto-generated method stub
			
		}
	}

