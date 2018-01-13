import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {
	
    private String name;
    private int score;
    private ArrayList<Bateau> bateaux = new ArrayList<Bateau>();
    private ArrayList<Bateau> bateauxCible = new ArrayList<Bateau>();
    private boolean play;
    private Bateau porteavion = new Bateau(5);
    private Bateau croiseur = new Bateau(4);
    private Bateau sousmarin = new Bateau(3);
    private Bateau sousmarin2 = new Bateau(3);
    private Bateau torpilleur = new Bateau(2);

    private int[][] caseJouee = new int[10][10]; //Qui prend 0 pour pas tirer, 1 pour tirer mais pas touché et 2 pour tirer et touché
    private ArrayList<Bateau> bateauxCoule = new ArrayList<Bateau>();

    /**
     * Le constructeur initialise le nom du joueur, son score et ajoute les 5  bateaux à la liste.
     * @param name
     */
    public Joueur(String name){
    		this.setName(name);
    		this.setScore(0);
            this.setPlay(false);

    		this.bateaux.add(porteavion);
            this.bateaux.add(croiseur);
            this.bateaux.add(sousmarin);
            this.bateaux.add(sousmarin2);
            this.bateaux.add(torpilleur);

            this.bateauxCible.add(porteavion);
            this.bateauxCible.add(croiseur);
            this.bateauxCible.add(sousmarin);
            this.bateauxCible.add(sousmarin2);
            this.bateauxCible.add(torpilleur);

        for (int x=0; x<10; x++) {
            for (int y=0; y<10;y++){
                caseJouee[x][y]=0;
            }
        }

    }
    
    /**
     * Methode abstraite permettant au différents types de joueurs de jouer un coup.
     * @param player
     * @param coord
     */
    public abstract int jouerCoup(Joueur player, Point coord, Vue vue);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

    public boolean getPlay() {return play;}

    public void setPlay(boolean play) {this.play = play;}

    public void setBateaux(ArrayList<Bateau> bateaux) {
        this.bateaux = bateaux;
    }

    public List<Bateau> getBateaux() {
       return this.bateaux;
    }

    public List<Bateau> getBateauxCible() {
        return this.bateauxCible;
    }

    public List<Bateau> getBateauxCoule() {
        return this.bateauxCoule;
    }


    public void nouveauxBateau(){
        this.bateaux.clear();
        this.bateauxCible.clear();
        this.porteavion.setCoord(new Point[this.porteavion.getTaille()]);
        this.croiseur.setCoord(new Point[this.croiseur.getTaille()]);
        this.sousmarin.setCoord(new Point[this.sousmarin.getTaille()]);
        this.sousmarin2.setCoord(new Point[this.sousmarin2.getTaille()]);
        this.torpilleur.setCoord(new Point[this.torpilleur.getTaille()]);

        this.bateaux.add(porteavion);
        this.bateaux.add(croiseur);
        this.bateaux.add(sousmarin);
        this.bateaux.add(sousmarin2);
        this.bateaux.add(torpilleur);

        this.bateauxCible.add(porteavion);
        this.bateauxCible.add(croiseur);
        this.bateauxCible.add(sousmarin);
        this.bateauxCible.add(sousmarin2);
        this.bateauxCible.add(torpilleur);
    }

    public int[][] getCaseJouee(){
        return this.caseJouee;
    }

    public void reinitialiseBateaux(){
        this.bateaux.clear();
    }

    public void setCaseJouee(int etat, int x, int y){
        this.caseJouee[x][y]=etat;
    }

    public void addBateauCoule(Bateau bateau){
        this.bateauxCoule.add(bateau);
    }
}
