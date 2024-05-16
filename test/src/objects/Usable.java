package objects;

import entity.Player;

public interface Usable {
	void use(Player player); //permet au joueur de l'utiliser
	boolean isConsumable(); //permet de savoir si l'objet doit rester dans l'inv apres utilisation $
	String getDescription();

}
