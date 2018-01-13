import java.awt.*;
import java.util.ArrayList;

public class Human_Player extends Joueur {


	public Human_Player(String name) {
		super(name);
	}

    @Override
	public int jouerCoup(Joueur playerAdverse, Point coord, Vue vue) {
	    for(int i = 0; i < playerAdverse.getBateauxCible().size(); i++){
            Point[] coordBateau = playerAdverse.getBateauxCible().get(i).getCoord();
            for( int j = 0; j < coordBateau.length; j++){
                if (coordBateau[j].getX() == coord.getX() && coordBateau[j].getY() == coord.getY()){
                    playerAdverse.getBateauxCible().get(i).setLife(playerAdverse.getBateauxCible().get(i).getLife()-1);
                    if (playerAdverse.getBateauxCible().get(i).getLife() == 0){
                        vue.messageBateauCoule(playerAdverse.getBateauxCible().get(i).getName());
                        playerAdverse.addBateauCoule(playerAdverse.getBateauxCible().get(i));
                        playerAdverse.getBateauxCible().remove(i);
                    }
                    return 2;
                }
            }
        }
        return 1;
	}
}
