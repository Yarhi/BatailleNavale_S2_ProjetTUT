import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlBoutonCible extends Control implements ActionListener {
    

    public ControlBoutonCible(Model model, Vue vue) {
        super(model, vue);
        vue.setControlBoutonCible(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();


            /* recupere la position du clic */
        int x = new Integer(e.getActionCommand().split(",")[0]);
        int y = new Integer(e.getActionCommand().split(",")[1]);


        /* Fonction qui joue le coup, affiche une explosion si touche, sinon affiche un cercle */
        /* Check si le joueur clique bien dans son propre plateau et pas dans celui de l'autre joueur */

        if ((model.joueur1.getPlay() && source == vue.tabjoueur2[x][y]) || (model.joueur2.getPlay() && source == vue.tabjoueur1[x][y])) {

            /* Check si la case qui vient d'être cliqué n'a pas déja été cliquée auparavant */

            if ((model.getPlayerAdverse()==model.joueur1 && vue.tabjoueur1[x][y].getIcon().equals(model.eau)) || (model.getPlayerAdverse()==model.joueur2 && vue.tabjoueur2[x][y].getIcon().equals(model.eau))) {
                Point coord = new Point(x, y);
                model.getPlayerAdverse().setCaseJouee(model.getPlayer().jouerCoup(model.getPlayerAdverse(), coord, vue), x, y);
                raffraichirPlateau();

                vue.bateauRestantJoueur1.setText("" + model.joueur1.getBateauxCible().size());
                vue.bateauRestantJoueur1.setFont(vue.font);
                vue.bateauRestantJoueur2.setText("" + model.joueur2.getBateauxCible().size());
                vue.bateauRestantJoueur2.setFont(vue.font);
                testPartieFini();
                model.changerJoueur();
                vue.changeFleche(model.getPlayer());
                vue.joueurEnCour.setText(model.getPlayer().getName() + " tire sur  " + model.getPlayerAdverse().getName());
                vue.setTitle("Bataille Navale, tour de " + model.getPlayer().getName());

                if (model.getPlayerAdverse() == model.joueur1) {
                    model.scoreJoueur1+=1;

                } else {
                    model.scoreJoueur2+=1;

                }

                if (model.joueur1.getBateauxCible().size() == 0) {

                    model.joueur1.setPlay(false);
                    model.joueur2.setPlay(false);
                    model.score = 100 - model.scoreJoueur2;
                    model.bestScore();
                    JOptionPane.showMessageDialog(vue, model.joueur1.getName() + " a perdu, votre score est de " + model.getScore() + "\n\n" + model.lireFichier(), "Victoire de " +  model.joueur2.getName(), JOptionPane.INFORMATION_MESSAGE, model.gameOver);
                } else if (model.joueur2.getBateauxCible().size() == 0) {

                    model.joueur1.setPlay(false);
                    model.joueur2.setPlay(false);
                    model.score = 100 - model.scoreJoueur1;
                    model.bestScore();
                    JOptionPane.showMessageDialog(vue, model.joueur2.getName() + " a perdu, votre score est de " + model.getScore() + "\n\n" + model.lireFichier(), "Victoire de " +  model.joueur1.getName(), JOptionPane.INFORMATION_MESSAGE, model.gameOver);
                }
            }
        }
    }


    public void raffraichirPlateau(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                switch (model.joueur1.getCaseJouee()[x][y]) {
                    case 0 :
                        vue.tabjoueur1[x][y].setIcon(model.eau);
                        break;
                    case 1 :
                        vue.tabjoueur1[x][y].setIcon(model.cercle);
                        break;
                    case 2 :
                        vue.tabjoueur1[x][y].setIcon(model.explosion);
                        break;
                }

                switch (model.joueur2.getCaseJouee()[x][y]) {
                    case 0 :
                        vue.tabjoueur2[x][y].setIcon(model.eau);
                        break;
                    case 1 :
                        vue.tabjoueur2[x][y].setIcon(model.cercle);
                        break;
                    case 2 :
                        vue.tabjoueur2[x][y].setIcon(model.explosion);
                        break;
                }
            }
        }

        for (int i = 0; i < model.joueur1.getBateauxCoule().size(); i++){
            Bateau bateauCoulee = model.joueur1.getBateauxCoule().get(i);
            boolean horizontal = bateauCoulee.getCoord()[0].getX() + 1 == bateauCoulee.getCoord()[1].getX();
            for (int j = 0; j < bateauCoulee.getCoord().length; j++){
                int coordX = (int) bateauCoulee.getCoord()[j].getX();
                int coordY = (int) bateauCoulee.getCoord()[j].getY();

                if(!horizontal){
                    if(j == 0){
                        vue.tabjoueur1[coordX][coordY].setIcon(model.bateau_bottomDeath);
                    }
                    else if(j == bateauCoulee.getTaille()-1){
                        vue.tabjoueur1[coordX][coordY].setIcon(model.bateau_headDeath);
                    }
                    else{
                        vue.tabjoueur1[coordX][coordY].setIcon(model.bateau_centerDeath);
                    }
                }
                else if(horizontal){
                    if(j == 0){
                        vue.tabjoueur1[coordX][coordY].setIcon(model.Vbateau_headDeath);
                    }
                    else if(j == bateauCoulee.getTaille()-1){
                        vue.tabjoueur1[coordX][coordY].setIcon(model.Vbateau_bottomDeath);
                    }
                    else{
                        vue.tabjoueur1[coordX][coordY].setIcon(model.Vbateau_centerDeath);
                    }
                }
            }

        }
        for (int i = 0; i < model.joueur2.getBateauxCoule().size(); i++){
            Bateau bateauCoulee = model.joueur2.getBateauxCoule().get(i);
            boolean horizontal = bateauCoulee.getCoord()[0].getX() + 1 == bateauCoulee.getCoord()[1].getX();
            for (int j = 0; j < bateauCoulee.getCoord().length; j++){
                int coordX = (int) bateauCoulee.getCoord()[j].getX();
                int coordY = (int) bateauCoulee.getCoord()[j].getY();

                if(!horizontal){
                    if(j == 0){
                        vue.tabjoueur2[coordX][coordY].setIcon(model.bateau_bottomDeath);
                    }
                    else if(j == bateauCoulee.getTaille()-1){
                        vue.tabjoueur2[coordX][coordY].setIcon(model.bateau_headDeath);
                    }
                    else{
                        vue.tabjoueur2[coordX][coordY].setIcon(model.bateau_centerDeath);
                    }
                }
                else if(horizontal){
                    if(j == 0){
                        vue.tabjoueur2[coordX][coordY].setIcon(model.Vbateau_headDeath);
                    }
                    else if(j == bateauCoulee.getTaille()-1){
                        vue.tabjoueur2[coordX][coordY].setIcon(model.Vbateau_bottomDeath);
                    }
                    else{
                        vue.tabjoueur2[coordX][coordY].setIcon(model.Vbateau_centerDeath);
                    }
                }
            }

        }
    }
    public void testPartieFini() {
        if (model.joueur1.getBateauxCible().size()==0 || model.joueur2.getBateauxCible().size()==0) {vue.creerWidgetFinPartie();}

    }
}
