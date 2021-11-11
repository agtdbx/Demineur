import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ViewJeu {
    private Main main;
    private Label lMines, lDrapeaux;
    private GridPane grille;
    private VBox core;
    private Font font;

    public ViewJeu(Main main, Font font){
        this.main = main;
        this.font = font;
    }

    public Scene creerJeu(int maxLignes){
        int nbMines = maxLignes;
        int nbDrapeau = maxLignes;


        ModeleJeu mj = new ModeleJeu(maxLignes, nbMines, nbDrapeau);

        // Main
        this.core = new VBox();
        this.core.setAlignment(Pos.CENTER);
        this.core.setSpacing(10);

        // Info
        HBox info = new HBox();
        info.setSpacing(20);
        info.setAlignment(Pos.TOP_CENTER);
        Label lTaille = new Label("Taille de la grille : " + maxLignes + "x" + maxLignes);
        lTaille.setFont(this.font);
        info.getChildren().add(lTaille);
        this.lMines = new Label("Nombre de mines : " + nbMines);
        this.lMines.setFont(this.font);
        info.getChildren().add(this.lMines);
        this.lDrapeaux = new Label("Drapeaux restants : " + nbDrapeau);
        this.lDrapeaux.setFont(this.font);
        info.getChildren().add(this.lDrapeaux);
        this.core.getChildren().add(info);

        // Début grille
        this.grille = new GridPane();
        ControleurJeu actionButJeu = new ControleurJeu(this.lMines, this.lDrapeaux, this.grille, mj, this.main, this);

        // Suite grille
        for (int x = 0; x < maxLignes; x++){
            for (int y = 0; y < maxLignes; y++){
                Button but = new Button("");
                but.setId("" + x + "," + y);
                but.setPrefWidth((this.main.getPrimaryStage().getWidth()-800)/maxLignes );
                but.setPrefHeight((this.main.getPrimaryStage().getHeight()-1200)/maxLignes);
                but.setOnMouseClicked(actionButJeu);
                but.setFont(new Font(this.font.getFamily(), this.font.getSize()*2-10-maxLignes));
                grille.add(but, x, y);
            }
        }
        grille.setAlignment(Pos.CENTER);
        grille.setGridLinesVisible(false);
        this.core.getChildren().add(grille);

        actionButJeu.debut();
        return new Scene(this.core, 1920, 1080);
    }

    public void butFin(boolean victoire){
        if (victoire){
            Label gg = new Label("Bravo pour votre victoire !");
            gg.setFont(this.font);
            this.core.getChildren().add(gg);
        }
        ControleurMenu actionButMenu = new ControleurMenu(this.main, new ViewMenu(this.main));
        Button nouvPartBut = new Button("Nouvelle partie");
        nouvPartBut.setAlignment(Pos.CENTER);
        nouvPartBut.setOnAction(actionButMenu);
        nouvPartBut.setFont(this.font);
        this.core.getChildren().add(nouvPartBut);

    }
}
