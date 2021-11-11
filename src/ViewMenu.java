import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ViewMenu {
    private Main main;
    private Font font;

    public ViewMenu(Main main){
        this.main = main;
        this.font = new Font("Arial", 30);
    }

    public Scene creerMenuPrincipale(){
        ControleurMenu actionButMenu = new ControleurMenu(this.main, this);

        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(10);

        Label titre = new Label("Démineur");
        titre.setFont(new Font(this.font.getFamily(), this.font.getSize()*2));
        titre.setAlignment(Pos.TOP_CENTER);
        main.getChildren().add(titre);

        Button nouvPartBut = new Button("Nouvelle partie");
        nouvPartBut.setOnAction(actionButMenu);
        nouvPartBut.setFont(this.font);
        main.getChildren().add(nouvPartBut);

        Button quitBut = new Button("Quitter");
        quitBut.setOnAction(actionButMenu);
        quitBut.setFont(this.font);
        main.getChildren().add(quitBut);

        return new Scene(main, 1920, 1080);
    }

    public Scene creerMenuDifficultes(){
        ControleurMenu actionButMenu = new ControleurMenu(this.main, this);

        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(10);

        Label titre = new Label("Difficultés");
        titre.setAlignment(Pos.TOP_CENTER);
        titre.setFont(new Font(this.font.getFamily(), this.font.getSize()*2));
        main.getChildren().add(titre);

        // Boutons supérieurs
        HBox butsUp = new HBox();
        butsUp.setAlignment(Pos.CENTER);
        butsUp.setSpacing(10);

        Button facileBut = new Button("Facile");
        facileBut.setOnAction(actionButMenu);
        facileBut.setFont(this.font);
        Button moyenBut = new Button("Moyen");
        moyenBut.setOnAction(actionButMenu);
        moyenBut.setFont(this.font);
        butsUp.getChildren().addAll(facileBut, moyenBut);

        // Boutons inférieurs
        HBox butsDown = new HBox();
        butsDown.setAlignment(Pos.CENTER);
        butsDown.setSpacing(10);

        Button difficileBut = new Button("Difficile");
        difficileBut.setOnAction(actionButMenu);
        difficileBut.setFont(this.font);
        Button impossibleBut = new Button("Extrême");
        impossibleBut.setOnAction(actionButMenu);
        impossibleBut.setFont(this.font);
        butsDown.getChildren().addAll(difficileBut, impossibleBut);

        main.getChildren().addAll(butsUp, butsDown);

        Button quitBut = new Button("Quitter");
        quitBut.setOnAction(actionButMenu);
        quitBut.setFont(this.font);
        main.getChildren().add(quitBut);

        return new Scene(main, 1920, 1080);
    }

    public Font getFont() {
        return font;
    }
}
