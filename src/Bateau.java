import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Bateau {
    private int taille;
    private int life;
    private String name;
    private Point[] coord;
    
    /**
     * Le constructeur prend la taille en paramètre et attribue automatiquement le nom et la vie au bateau.
     * @param taille
     */
    public Bateau(int taille) {
    	
    	if (taille >= 2 && taille <= 5) {
    		this.setTaille(taille);
    		this.setLife(taille);
            this.coord = new Point[taille];
    		
    		switch (taille) {
    		case 5:
    			this.setName("Titanic");
    			break;
    		case 4:
    			this.setName("Concordia");
    			break;
    		case 3:
    			this.setName("Lusitania");
    			break;
    		case 2:
    			this.setName("Hermione");
    			break;
    		default:
    			break;
    		}			
		}
	}
    

	/**
	 * La méthode admet un tableau de point 2d en paramètre ( coordonnée à sur laquelle l'utilisateur souhaite placer son bateau)
	 * et la stock dans l'attribue de classe coord[].
	 * @param coord
	 */
	public void setCoord(Point[] coord){
		this.coord = coord;
	}

    public Point[] getCoord(){
        return this.coord;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

    public boolean placerBateau(Point coordClick, boolean horizontal, Joueur player) {
        int x = (int) coordClick.getX();
        int y = (int) coordClick.getY();
        if (!horizontal){
            if (x + this.taille <= 10 ){
                for (int i = 0; i < this.taille; i++ ){
                    Point point = new Point(x+i,y);
                    if (verifierCoordonnee(point, player)){
                        this.coord[i] = point;
                    }
                    else{
                        for(int j = 0; j < this.taille; j++){
                            this.coord[j] = null;
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        else{
            if (y + this.taille <= 10 ){
                for (int i = 0; i < this.taille; i++ ){
                    Point point = new Point(x,y+i);
                    if (verifierCoordonnee(point, player)){
                        this.coord[i] = point;
                    }
                    else{
                        for(int j = 0; j < this.taille; j++){
                            Arrays.fill(this.coord,null);
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean verifierCoordonnee(Point coord, Joueur player) {
        for (int i = 0 ; i < player.getBateauxCible().size(); i++){
            Point[] tabCoord = player.getBateauxCible().get(i).getCoord();
            for(int j = 0; j < tabCoord.length; j++){
                Point testCoord = tabCoord[j];
                if (testCoord != null && coord.getX() == testCoord.getX() && coord.getY() == testCoord.getY()){
                    return false;
                }
            }
        }
        return true;
    }
}
