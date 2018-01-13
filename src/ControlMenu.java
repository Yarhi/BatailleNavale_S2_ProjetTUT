import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu extends Control implements ActionListener {

    public ControlMenu(Model model, Vue vue) {
        super(model, vue);
        vue.setControlMenu(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        /* Recommencer */
        if (source == vue.menuNouveau) {
            vue.repaint();
            vue.creerWidget1();
            vue.setVisible(true);
            vue.setTitle("Bataille Navale");
            vue.setSize(400, 250);
            vue.setLocationRelativeTo(null);
            model.joueur1.reinitialiseBateaux();
            model.joueur2.reinitialiseBateaux();
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    model.joueur1.setCaseJouee(0,x,y);
                    model.joueur2.setCaseJouee(0,x,y);
                    vue.tabjoueur1[x][y].setIcon(model.eau);
                    vue.tabjoueur2[x][y].setIcon(model.eau);
                }
            }
            afficherNombredeBateaux();
            desactiverCasePlacement();
            model.joueur1.nouveauxBateau();
            model.joueur2.nouveauxBateau();
            model.joueur1.getBateauxCoule().clear();
            model.joueur2.getBateauxCoule().clear();
            vue.type_bateau1.setEnabled(true);
            vue.type_bateau2.setEnabled(true);
            vue.type_bateau3.setEnabled(true);
            vue.type_bateau4.setEnabled(true);
            model.bateauChoisi = false;
            model.scoreJoueur1 = 0;
            model.scoreJoueur2 = 0;
            model.joueur1.setPlay(true);
            model.joueur2.setPlay(false);
            vue.AffichageNbBateaux.setText("Il reste 5 bateaux a placer.");
        /* Meilleurs Scores */
        } else if (source == vue.menuBestScore) {
            model.bestScore();
            JOptionPane.showMessageDialog(vue, model.lireFichier(), "Meilleurs scores",  JOptionPane.INFORMATION_MESSAGE);
        } else if (source == vue.sousaide1) {
            /* Affichage des règles du jeu */
            JOptionPane.showMessageDialog(vue, "La bataille navale oppose deux joueurs qui s'affrontent.\n" +
                    "Chacun a une flotte composee de 5 bateaux, qui sont les suivants : 1 Titanic (5 cases), 1 Concordia (4 cases), 2 Lusitania (3 cases), 1 Hermione (2 cases).\n" +
                    "Au debut du jeu, chaque joueur place ses bateaux sur sa grille.\n" +
                    "Un a un, les joueurs vont 'tirer' sur une case de l'adversaire.\n" +
                    "Le but est donc de couler les bateaux adverses.\n" +
                    "Lorsqu'un bateau ennemi est touche par un tir, une explosion s'affichera sur la case ciblee avec un message 'Touche'.\n" +
                    "De plus, lorsque le bateau touche a entierement ete detruit, le message 'Vous avez coule le XXX de YYY' s'affichera a la place.\n" +
                    "Par contre, si vous ne parvenez pas a atteindre un bateau, un cercle blanc s'affichera sur la case ciblee'.\n" +
                    "Le premier joueur qui reussi a couler tous les bateaux adverses gagne la partie et son score,\n" +
                    "etant de 100 moins le nombre de coups joues pour gagner, sera enregistre si il fait parti des 3 meilleurs.", "Les regles",  JOptionPane.INFORMATION_MESSAGE, model.information);
        } else if (source == vue.sousaide2) {
            JOptionPane.showMessageDialog(vue, "Ce projet de Bataille Navale a ete realise par le groupe de S2B2 de l'IUTBM dans le cadre du projet tutore de fin de semestre.\n" +
                    "Groupe etant compose de :\n" +
                    "BOLARD Clement,        DUCROZ Jules,         GROSJEAN Yann,\n" +
                    "HUOT Gael,                     PASTEUR Loic,         RUNSER Jonathan.\n", "Projet Tutore, Bataille Navale, S2B2",  JOptionPane.INFORMATION_MESSAGE, model.iutbm);
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

    public void afficherNombredeBateaux(){
        vue.AffichageNbBateaux.setText("Il reste " +  model.getPlayer().getBateaux().size() + " bateaux à placer.");
    }
}