import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControleurMenu implements EventHandler<ActionEvent> {
    private Main main;
    private ViewMenu vm;

    public ControleurMenu(Main main, ViewMenu vm){
        this.main = main;
        this.vm = vm;
    }

    @Override
    public void handle(ActionEvent actionEvent){
        Button but = (Button) actionEvent.getSource();

        if (but.getText().equals("Nouvelle partie")){
            main.getPrimaryStage().setScene(vm.creerMenuDifficultes());
        }
        else if (but.getText().equals("Quitter")){
            main.End();
        }
        else if (but.getText().equals("Facile")){
            main.getPrimaryStage().setScene(new ViewJeu(main, this.vm.getFont()).creerJeu(10));
        }
        else if (but.getText().equals("Moyen")){
            main.getPrimaryStage().setScene(new ViewJeu(main, this.vm.getFont()).creerJeu(20));
        }
        else if (but.getText().equals("Difficile")){
            main.getPrimaryStage().setScene(new ViewJeu(main, this.vm.getFont()).creerJeu(30));
        }
        else if (but.getText().equals("Extrême")){
            main.getPrimaryStage().setScene(new ViewJeu(main, this.vm.getFont()).creerJeu(40));
        }
    }
}
