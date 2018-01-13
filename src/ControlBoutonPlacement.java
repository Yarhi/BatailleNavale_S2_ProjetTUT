import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlBoutonPlacement extends Control implements ActionListener {


    private int xPlateau;
    private int yPlateau;
    
    public ControlBoutonPlacement(Model model, Vue vue) {
        super(model, vue);
        vue.setControlBoutonPlacement(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        /* Bouton suivant qui passe de la vue en widget1 � widget2 */
        if (source == vue.suivant) {
            if (vue.nom_j1.getText().equals("") || vue.nom_j2.getText().equals("")) {
                vue.manqueNomJoueur();
            }
            else {
                model.joueur1.setName(vue.nom_j1.getText());
                model.joueur2.setName(vue.nom_j2.getText());
                vue.raffraichissementNomJoueur();
                vue.repaint();
                vue.creerWidget2();
                model.reinitialiserCouleurPlateau();
                vue.setVisible(true);
                vue.setTitle("Bataille Navale, " + model.getPlayer().getName());
            }
        }
        if (source == vue.valider) {
            if (model.joueur1.getPlay() && model.joueur1.getBateaux().size() == 0) {
                vue.repaint();
                vue.creerWidget2();
                vue.setVisible(true);
                model.reinitialiserCouleurPlateau();
                model.changerJoueur();
                afficherNombredeBateaux();
                vue.setTitle("Bataille Navale, " + model.getPlayer().getName());
                model.bateauChoisi = false;
                changerEtatBoutonSelection();
                vue.valider.setEnabled(false);
            } else if(model.joueur2.getBateaux().size() == 0) {
                vue.repaint();
                vue.creerWidget3();
                vue.setTitle("Bataille Navale, tour de " + model.joueur1.getName());
                vue.setVisible(true);
                model.reinitialiserCouleurPlateau();
                model.changerJoueur();

            }
        }
        if (source == vue.annuler) {
        /* Reinitialise tous les parametres du placement des bateaux */
            model.bateauChoisi = false;
            changerEtatBoutonSelection();
            model.reinitialiserCouleurPlateau();
            afficherNombredeBateaux();
            model.getPlayer().nouveauxBateau();
            afficherNombredeBateaux();
            afficherNombredeBateaux();
            model.getPlayer().nouveauxBateau();
            afficherNombredeBateaux();
            desactiverCasePlacement();
            vue.type_bateau1.setEnabled(true);
            vue.type_bateau2.setEnabled(true);
            vue.type_bateau3.setEnabled(true);
            vue.type_bateau4.setEnabled(true);
        }
        /*
        * ****************************************************************************
        * ******************* Placement des bateaux sur le plateau *******************
        * ****************************************************************************
        */
        if (source == vue.sens_bateau) {
            changerSensBateau();
        }
        if( source == vue.type_bateau1 || source == vue.type_bateau2 ||source == vue.type_bateau3 ||source == vue.type_bateau4)  {
            if (model.bateauChoisi == false) {
                model.bateau = new String(e.getActionCommand());
                /* Desactive les boutons qui permettent la selection de la taille d'un bateau */
                model.bateauChoisi = true;
                changerEtatBoutonSelection();
                activerCasePlacement();

            }
        }
        if (model.bateauChoisi == true && e.getActionCommand().contains(",")) {
            xPlateau = new Integer(e.getActionCommand().split(",")[0]);
            yPlateau = new Integer(e.getActionCommand().split(",")[1]);

            /* Placer les bateaux */
            model.placerBateau(xPlateau, yPlateau, getBateauAPlacer(model.bateau), model.getPlayer());
            afficherNombredeBateaux();
            model.bateauChoisi = false;
            /* Desactive les boutons du plateau */
            desactiverCasePlacement();
            /* Reactive les boutons qui permettent la selection de la taille d'un bateau */
            changerEtatBoutonSelection();
        }
        /* Regarde si 5 bateaux sont places, si c'est le cas, desactive les boutons de placement et active celui de la validation*/
        if (model.getPlayer().getBateaux().size() == 0) {
            vue.type_bateau1.setEnabled(false);
            vue.type_bateau2.setEnabled(false);
            vue.type_bateau3.setEnabled(false);
            vue.type_bateau4.setEnabled(false);
            vue.valider.setEnabled(true);
        }
    }
    
    public void activerCasePlacement(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (model.getCaseLibre()[x][y]) {
                    model.getCellule()[x][y].setEnabled(true);
                }
            }
        }
    }
    public void desactiverCasePlacement(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (model.getCaseLibre()[x][y]) {
                    model.getCellule()[x][y].setFocusable(false);
                }
            }
        }
    }
    public void changerEtatBoutonSelection(){
        if (!model.bateauChoisi){
            for (int i = 0; i < model.getPlayer().getBateaux().size(); i++){
                switch (model.getPlayer().getBateaux().get(i).getTaille()){
                    case 2 :
                        vue.type_bateau1.setEnabled(true);
                        break;
                    case 3 :
                        vue.type_bateau2.setEnabled(true);
                        break;
                    case 4:
                        vue.type_bateau3.setEnabled(true);
                        break;
                    case 5:
                        vue.type_bateau4.setEnabled(true);
                        break;
                }
            }
        }else{
            vue.type_bateau1.setEnabled(false);
            vue.type_bateau2.setEnabled(false);
            vue.type_bateau3.setEnabled(false);
            vue.type_bateau4.setEnabled(false);
        }
    }

    public void afficherNombredeBateaux(){
        vue.AffichageNbBateaux.setText("il reste " +  model.getPlayer().getBateaux().size() + " bateaux a placer.");
    }

    public void changerSensBateau(){
        if (model.BateauHorizontal) {
            model.BateauHorizontal = false;
            vue.sens_bateau.setText("vertical");
        } else {
            model.BateauHorizontal = true;
            vue.sens_bateau.setText("horizontal");
        }
    }

    public Bateau getBateauAPlacer(String name){
        for (int i = 0; i< model.getPlayer().getBateaux().size(); i++){
            if (model.getPlayer().getBateaux().get(i).getName().equals(name)){
                return model.getPlayer().getBateaux().get(i);
            }
        }
        return null;
    }

  }