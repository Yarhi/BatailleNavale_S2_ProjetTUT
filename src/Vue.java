import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Vue extends JFrame {

    protected Model model;

    /* Menu */
    private JMenuBar barMenu;
    private JMenu menuOptions;
    private JMenu menuRegle;
    public JMenuItem menuBestScore;
    public JMenuItem menuNouveau;
    public JMenuItem sousaide1;
    public JMenuItem sousaide2;

    /* Vue 2 */
    public Panel global2;
    public Panel global3;
    public JPanel panel;
    public JPanel panel1;
    public JPanel panel2;
    public JButton sens_bateau;
    public BoutonPlacementCase type_bateau1;
    public BoutonPlacementCase type_bateau2;
    public BoutonPlacementCase type_bateau3;
    public BoutonPlacementCase type_bateau4;
    public JLabel AffichageNbBateaux;
    public JButton valider;
    public JButton annuler;


    public JButton suivant;

    public JButton[][] tabjoueur1 = new JButton[10][10];
    public JButton[][] tabjoueur2 = new JButton[10][10];

    public JLabel bateauRestantJoueur1;
    public JLabel bateauRestantJoueur2;
    public JLabel joueurEnCour;
    public JLabel fin_texte;

    public JLabel titre;
    public JLabel titrej1;
    public JLabel titrej2;
    public JTextField nom_j1;
    public JTextField nom_j2;

    public Font font;

    public JLabel titreNBR1;
    public JLabel titreNBR2;

    public JLabel flecheDroite;
    public JLabel flecheGauche;
    public JLabel partieCentre;


    public Vue(Model model) {
        this.model = model;
        setTitle("Bataille Navale");
        setIconImage(new ImageIcon("Images/icon.jpg").getImage());
        initAttribut();
        creerMenu();
        creerWidget1();
        pack();
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initAttribut() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Font/mario.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Font/mario.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        /******* Boutons de la vue numero 1 *******/

        suivant = new JButton("suivant");
        suivant.setBackground(new Color(52, 152, 219));
        suivant.setFont(font);

        titre = new JLabel("Bataille Navale");
        titre.setFont(font);
        titre.setHorizontalAlignment(SwingConstants.CENTER);

        titrej1 = new JLabel("nom du joueur 1");
        titrej1.setFont(font);
        titrej2 = new JLabel("nom du joueur 2");
        titrej2.setFont(font);
        nom_j1 = new JTextField(10);
        nom_j1.setBackground(Color.lightGray);
        nom_j2 = new JTextField(10);
        nom_j2.setBackground(Color.lightGray);

        /******* Boutons de la vue numero 2 *******/

        /* Creer le tableau de boutons pour le plateau */
        panel = new JPanel(new GridLayout(10, 10));
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                model.getCellule()[x][y] = new JButton();
                model.getCellule()[x][y].setPreferredSize(new Dimension(50, 50));
                model.getCellule()[x][y].setMaximumSize(new Dimension(50, 50));
                model.getCellule()[x][y].setActionCommand(x + "," + y);
                panel.add(model.getCellule()[x][y]);
                model.getCellule()[x][y].setBorderPainted(false);
                model.getCellule()[x][y].setIcon(model.eau);
                model.getCellule()[x][y].setFocusable(true);
            }
        }

        sens_bateau = new JButton("horizontal",model.flat_rotate);
        sens_bateau.setBackground(new Color(89, 171, 227));
        sens_bateau.setFont(font);
        sens_bateau.setBorderPainted(false);



        type_bateau1 = new BoutonPlacementCase("hermione",2);
        type_bateau1.setBackground(new Color(107, 185, 240));

        type_bateau2 = new BoutonPlacementCase("lusitania",3);
        type_bateau2.setBackground(new Color(129, 207, 224));

        type_bateau3 = new BoutonPlacementCase("concordia",4);
        type_bateau3.setBackground(new Color(129, 207, 224));

        type_bateau4 = new BoutonPlacementCase("titanic",5);
        type_bateau4.setBackground(new Color(107, 185, 240));

        valider = new JButton("valider les placements");
        valider.setEnabled(false);
        valider.setBorderPainted(false);
        valider.setFont(font);
        valider.setBackground(new Color(129, 207, 224));

        annuler = new JButton("annuler les placements");
        annuler.setFont(font);
        annuler.setBorderPainted(false);
        annuler.setBackground(new Color(107, 185, 240));

        AffichageNbBateaux = new JLabel("il  reste  " + model.getPlayer().getBateaux().size() + "  bateaux  a  placer.");
        AffichageNbBateaux.setFont(font);

        /* Permet d'utiliser les boutons dans ControlBoutonPlacement par leur "noms" */
        sens_bateau.setActionCommand("sens");
        type_bateau1.setActionCommand("Hermione");
        type_bateau2.setActionCommand("Lusitania");
        type_bateau3.setActionCommand("Concordia");
        type_bateau4.setActionCommand("Titanic");


        /******* Boutons de la vue numero 3 *******/

        flecheDroite = new JLabel(model.flecheVaDroite);
        flecheGauche = new JLabel(model.flecheVaGauche);

        /* Creer le tableau de boutons pour le plateau du joueur 1 */
        panel1 = new JPanel(new GridLayout(10, 10));
        panel1.setBackground(new Color(0,0,0,0));
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tabjoueur1[x][y]= new JButton();
                tabjoueur1[x][y].setActionCommand(x + "," + y);
                tabjoueur1[x][y].setIcon(model.eau);
                tabjoueur1[x][y].setPreferredSize(new Dimension(50, 50));
                tabjoueur1[x][y].setMaximumSize(new Dimension(50, 50));
                tabjoueur1[x][y].setBorderPainted(false);
                tabjoueur1[x][y].setBackground(new Color(0,0,0,0));
                panel1.add(tabjoueur1[x][y]);
            }
        }

        /* Creer le tableau de boutons pour le plateau du joueur 2 */
        panel2 = new JPanel(new GridLayout(10, 10));
        panel2.setBackground(new Color(0,0,0,0));
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tabjoueur2[x][y]= new JButton();
                tabjoueur2[x][y].setActionCommand(x + "," + y);
                tabjoueur2[x][y].setIcon(model.eau);
                tabjoueur2[x][y].setPreferredSize(new Dimension(50, 50));
                tabjoueur2[x][y].setMaximumSize(new Dimension(50, 50));
                tabjoueur2[x][y].setBorderPainted(false);
                tabjoueur2[x][y].setBackground(new Color(0,0,0,0));
                panel2.add(tabjoueur2[x][y]);
            }
        }
    }

    public void creerWidget1() {
        JPanel contenu_accueil = new JPanel();
        contenu_accueil.add(titrej1);
        contenu_accueil.add(nom_j1);
        contenu_accueil.add(titrej2);
        contenu_accueil.add(nom_j2);


        JPanel global = new JPanel();
        global.setLayout(new BorderLayout(30, 30));
        global.add(titre, BorderLayout.NORTH);
        global.add(contenu_accueil, BorderLayout.CENTER);
        global.add(suivant, BorderLayout.SOUTH);    

        setContentPane(global);
    }


    public void creerWidget2() {
        setLayout(new BorderLayout());
        JLabel global=new JLabel(model.background);
        add(global);
        global.setLayout(new FlowLayout());

        global2 = new Panel(new GridLayout(1, 1));
        global3 = new Panel(new GridLayout(2, 2));

        global3.add(type_bateau1);
        global3.add(type_bateau2);
        global3.add(type_bateau3);
        global3.add(type_bateau4);
        global2.add(global3);
        global2.add(sens_bateau);

        global.add(global2);
        global.add(AffichageNbBateaux);
        global.add(panel);
        global.add(annuler);
        global.add(valider);
        setContentPane(global);

        pack();
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    public void creerWidget3() {  //Creation de la fenetre pour jouer, ou il y a les deux plateaux côte à côte
        JLabel global = new JLabel(model.backgroundw3);

        setSize(1320, 560);
        setLocationRelativeTo(null);
        setResizable(true);

        partieCentre = new JLabel();
        partieCentre.setLayout(new BoxLayout(partieCentre, BoxLayout.PAGE_AXIS));
        partieCentre.add(titreNBR1);
        partieCentre.add(bateauRestantJoueur1);
        partieCentre.add(titreNBR2);
        bateauRestantJoueur1.setFont(font);
        partieCentre.add(bateauRestantJoueur2);
        bateauRestantJoueur2.setFont(font);
        partieCentre.add(flecheDroite);
        partieCentre.add(joueurEnCour);

        
        global.setLayout(new BorderLayout(10, 10));
        global.add(panel1, BorderLayout.WEST);
        global.add(panel2, BorderLayout.EAST);
        global.add(partieCentre, BorderLayout.CENTER);


        setContentPane(global);
    }

    public void creerWidgetFinPartie() {
        Panel fin = new Panel();
        fin.add(new JLabel(new ImageIcon("Images/naufrage.jpg")));
        fin.add(fin_texte);
        setContentPane(fin);


        pack();
        setSize(600, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    protected void creerMenu() {
        barMenu = new JMenuBar();
        menuOptions = new JMenu("Options");
        menuBestScore = new JMenuItem("Meilleurs Scores");
        menuNouveau = new JMenuItem("Nouvelle Partie");
        menuRegle = new JMenu("Regles");
        sousaide1 = new JMenuItem("Comment \347a marche ?");
        sousaide2 = new JMenuItem("A propos");
        barMenu.add(menuOptions);
        menuOptions.add(menuNouveau);
        menuOptions.addSeparator();
        menuOptions.add(menuBestScore);
        barMenu.add(menuRegle);
        menuRegle.add(sousaide1);
        menuRegle.add(sousaide2);
        setJMenuBar(barMenu);
    }

    public void setControlMenu(ActionListener listener){
        menuNouveau.addActionListener(listener);
        menuBestScore.addActionListener(listener);
        sousaide1.addActionListener(listener);
        sousaide2.addActionListener(listener);
    }

    public void setControlBoutonPlacement(ActionListener listener) {
        suivant.addActionListener(listener);
        sens_bateau.addActionListener(listener);
        type_bateau1.addActionListener(listener);
        type_bateau2.addActionListener(listener);
        type_bateau3.addActionListener(listener);
        type_bateau4.addActionListener(listener);
        for (int x = 0; x < model.getCellule().length; x++) {
            for (int y = 0; y < model.getCellule().length; y++) {
                model.getCellule()[x][y].addActionListener(listener);
            }
        }
        valider.addActionListener(listener);
        annuler.addActionListener(listener);
    }

    public void setControlBoutonCible(ActionListener listener) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tabjoueur1[x][y].addActionListener(listener);
                tabjoueur2[x][y].addActionListener(listener);
            }
        }
    }

    public void display() {
        setVisible(true);
    }

    public void messageBateauCoule(String nom_bateau) {
        JOptionPane.showMessageDialog(this, "Vous avez coulé le " + nom_bateau + " de " + model.getPlayerAdverse().getName(),
                "Bravo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void manqueNomJoueur(){
        JOptionPane.showMessageDialog(this, "Veuillez compléter les noms des joueurs.",
                "Attention",
                JOptionPane.WARNING_MESSAGE);
    }

    public void raffraichissementNomJoueur(){
        joueurEnCour = new JLabel(model.getPlayer().getName() + " tire sur  " + model.getPlayerAdverse().getName());
        joueurEnCour.setHorizontalTextPosition(SwingConstants.CENTER);
        joueurEnCour.setFont(font);
        fin_texte = new JLabel("Bravo " + model.getPlayer().getName() + " a gagné !");
        bateauRestantJoueur1 = new JLabel("" + model.joueur1.getBateauxCible().size());
        bateauRestantJoueur2 = new JLabel("" + model.joueur2.getBateauxCible().size());
        titreNBR1 = new JLabel("bateaux restant de " + model.joueur1.getName());
        titreNBR2 = new JLabel("bateaux restant de " + model.joueur2.getName());
        titreNBR1.setFont(font);
        titreNBR2.setFont(font);
        titreNBR1.setVerticalAlignment(SwingConstants.CENTER);
        titreNBR2.setVerticalAlignment(SwingConstants.CENTER);
    }

    public void changeFleche(Joueur player) {
        if (player==model.joueur2) {
            partieCentre.remove(flecheDroite);
            partieCentre.remove(joueurEnCour);
            partieCentre.add(flecheGauche);
            partieCentre.add(joueurEnCour);
            partieCentre.repaint();
        }
        if (player==model.joueur1){
            partieCentre.remove(flecheGauche);
            partieCentre.remove(joueurEnCour);
            partieCentre.add(flecheDroite);
            partieCentre.add(joueurEnCour);
            partieCentre.repaint();
        }
    }

    public class BoutonPlacementCase extends JButton implements MouseListener {
        private int size;
        private String name;
        public BoutonPlacementCase(String str,int size){
            super(str);
            this.name = str;
            this.size = size;

            this.setFont(font);
            this.setBorderPainted(false);
            this.addMouseListener(this);

        }


        //Méthode appelée lors du clic de souris
        public void mouseClicked(MouseEvent event) {}

        //Méthode appelée lors du survol de la souris
        public void mouseEntered(MouseEvent event) {
            if (this.isEnabled()) {
                this.setText(size + " cases");
            }
        }

        //Méthode appelée lorsque la souris sort de la zone du bouton
        public void mouseExited(MouseEvent event) {
        this.setText(name);
        }

        //Méthode appelée lorsque l'on presse le bouton gauche de la souris
        public void mousePressed(MouseEvent event) { }

        //Méthode appelée lorsque l'on relâche le clic de souris
        public void mouseReleased(MouseEvent event) { }
    }

}
