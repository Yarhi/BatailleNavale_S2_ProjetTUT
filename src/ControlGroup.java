public class ControlGroup {

    private Model model;
    private Vue vue;
    private ControlBoutonPlacement controlboutonplacement;
    private ControlBoutonCible controlboutoncible;
    private ControlMenu controlmenu;

    public ControlGroup(Model model){
        this.model = model;
        vue = new Vue(model);
        controlboutonplacement = new ControlBoutonPlacement(model, vue);
        controlboutoncible = new ControlBoutonCible(model, vue);
        controlmenu = new ControlMenu(model, vue);
        vue.display();
    }
}
