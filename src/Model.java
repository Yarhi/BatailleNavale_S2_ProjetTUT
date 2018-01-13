import javax.swing.*;
import java.awt.*;
import java.io.*;


public class Model {

    private int score1, score2, score3;

    public int score = 0;

    public boolean bateauChoisi = false;
    public int scoreJoueur1 = 0;
    public int scoreJoueur2 = 0;

    public String bateau;

    public ImageIcon flecheVaDroite = new ImageIcon("Images/FLAT/fleche.png");
    public ImageIcon flecheVaGauche = new ImageIcon("Images/FLAT/fleche_gauche.png");

    public ImageIcon backgroundAcceuil = new ImageIcon("Images/FLAT/background_acc.png");
    public ImageIcon backgroundw3 = new ImageIcon("Images/FLAT/background_w3.png");
    public ImageIcon flat_rotate = new ImageIcon("Images/FLAT/rotate.png");
    public ImageIcon background = new ImageIcon("Images/FLAT/background2.png");
    public ImageIcon explosion = new ImageIcon("Images/explosion.jpg");
    public ImageIcon cercle = new ImageIcon("Images/FLAT/cercle.png");
    public ImageIcon eau = new ImageIcon("Images/FLAT/eau.png");
    public ImageIcon information = new ImageIcon("Images/information.jpg");
    public ImageIcon iutbm = new ImageIcon("Images/iutbm.jpg");
    public ImageIcon gameOver = new ImageIcon("Images/game_over.jpg");

    /* Images des bateaux */
    /* taille 2 */
    public ImageIcon bateau_bottom = new ImageIcon("Images/FLAT/bateau_bottom.png");
    public ImageIcon bateau_center = new ImageIcon("Images/FLAT/bateau_center.png");
    public ImageIcon bateau_head = new ImageIcon("Images/FLAT/bateau_head.png");
    public ImageIcon Vbateau_bottom = new ImageIcon("Images/FLAT/Vbateau_bottom.png");
    public ImageIcon Vbateau_center = new ImageIcon("Images/FLAT/Vbateau_center.png");
    public ImageIcon Vbateau_head = new ImageIcon("Images/FLAT/Vbateau_head.png");

    public ImageIcon bateau_bottomDeath = new ImageIcon("Images/FLAT/bateau_bottom_dead.png");
    public ImageIcon bateau_centerDeath = new ImageIcon("Images/FLAT/bateau_center_dead.png");
    public ImageIcon bateau_headDeath = new ImageIcon("Images/FLAT/bateau_head_dead.png");
    public ImageIcon Vbateau_bottomDeath = new ImageIcon("Images/FLAT/Vbateau_bottom_dead.png");
    public ImageIcon Vbateau_centerDeath = new ImageIcon("Images/FLAT/Vbateau_center_dead.png");
    public ImageIcon Vbateau_headDeath = new ImageIcon("Images/FLAT/Vbateau_head_dead.png");

    public Human_Player joueur1 = new Human_Player("Joueur 1");
    public Human_Player joueur2 = new Human_Player("Joueur 2");

    public boolean BateauHorizontal = true;
    private JButton[][] cellule = new JButton[10][10];

    private boolean[][] caseLibre = new boolean[10][10];

    public Model(){
        joueur1.setPlay(true);
        joueur2.setPlay(false);
    }
    public void placerBateau(int x, int y, Bateau bateauChoisi, Joueur player) {
        Point coordClick = new Point(x, y);
        if (bateauChoisi != null) {
            if (bateauChoisi.placerBateau(coordClick, BateauHorizontal, player)) {
                for (int i = 0; i < bateauChoisi.getCoord().length; i++) {
                    int coordX = (int) bateauChoisi.getCoord()[i].getX();
                    int coordY = (int) bateauChoisi.getCoord()[i].getY();
                    /* Affichages des bateaux en fonction de leur taille et de leur sens */

                    if(BateauHorizontal){
                        if (i == 0){
                            getCellule()[coordX][coordY].setIcon(bateau_bottom);
                        }
                        else if (i == bateauChoisi.getTaille()-1){
                            getCellule()[coordX][coordY].setIcon(bateau_head);
                        }
                        else{
                            getCellule()[coordX][coordY].setIcon(bateau_center);
                        }
                    }
                    else{
                        if (i == 0){
                            getCellule()[coordX][coordY].setIcon(Vbateau_head);
                        }
                        else if (i == bateauChoisi.getTaille()-1){
                            getCellule()[coordX][coordY].setIcon(Vbateau_bottom);
                        }
                        else{
                            getCellule()[coordX][coordY].setIcon(Vbateau_center);
                        }
                    }
                }
                player.getBateaux().remove(bateauChoisi);

            }
        }
    }

    public String lireFichier() {
        String donnee=null;
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("Scores.txt"))));
            try {
                donnee=((Score)ois.readObject()).toString();
                return donnee;
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return donnee;
    }

    public void ecrireFichier(int score,int score1,int score2,int score3) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("Scores.txt"))));
            oos.writeObject(new Score(score,score1,score2,score3));
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bestScore() {
        if (lireFichier()==null) {
            ecrireFichier(getScore(), getScore(), 0, 0);
        } else if (lireFichier()!=null) {
            setScore1(new Integer(lireFichier().split("-")[1]));
            setScore2(new Integer(lireFichier().split("-")[3]));
            setScore3(new Integer(lireFichier().split("-")[5]));
            if (getScore()>getScore1()) {
                ecrireFichier(getScore(),getScore(),getScore1(),getScore2());
            } else if (getScore()<getScore1() && getScore()>getScore2()) {
                ecrireFichier(getScore(),getScore1(),getScore(),getScore2());
            } else if (getScore()<getScore2() && getScore()>getScore3()) {
                ecrireFichier(getScore(),getScore1(),getScore2(),getScore());
            } else {
                ecrireFichier(getScore(),getScore1(),getScore2(),getScore3());
            }
        }
    }

    public Joueur getPlayer(){
        if (joueur1.getPlay()){
            return joueur1;
        }
        else{
            return joueur2;
        }
    }

    public Joueur getPlayerAdverse(){
        if (joueur1.getPlay()){
            return joueur2;
        }
        else{
            return joueur1;
        }
    }

    public void reinitialiserCouleurPlateau(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                    getCellule()[x][y].setIcon(eau);
            }
        }
    }

    public void changerJoueur(){
        if (joueur1.getPlay()){
            joueur1.setPlay(false);
            joueur2.setPlay(true);
        }
        else{
            joueur1.setPlay(true);
            joueur2.setPlay(false);
        }
    }

    public JButton[][] getCellule() {
        return cellule;
    }


    public boolean[][] getCaseLibre() {
        return caseLibre;
    }

    public int getScore() {
        return score;
    }

    public int getScore1() {
        return score1;
    }
    public int getScore2() {
        return score2;
    }
    public int getScore3() {
        return score3;
    }
    public void setScore1(int score) {
        score1 = score;
    }
    public void setScore2(int score) {
        score2 = score;
    }
    public void setScore3(int score) {
        score3 = score;
    }


}