import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;

public class ControleurJeu implements EventHandler<MouseEvent> {
    private Label lMines, lDrapeaux;
    private GridPane grille;
    private ModeleJeu mj;
    private String action;
    private Main main;
    private ViewJeu vj;

    public ControleurJeu(Label lMines, Label lDrapeaux, GridPane grille, ModeleJeu mj, Main main, ViewJeu vj){
        this.lMines = lMines;
        this.lDrapeaux = lDrapeaux;
        this.grille = grille;
        this.mj = mj;
        this.action = "Clic";
        this.main = main;
        this.vj = vj;
    }

//    @Override
//    public void handle(ActionEvent actionEvent){
//        Button but = (Button) actionEvent.getSource();
//
//        if (this.action.equals("Clic")){
//            this.actionClic(but);
//            this.verification();
//        }
//        else if (this.action.equals("Drapeau")){
//            this.actionDrapeau(but);
//            this.verification();
//        }
//        else if (this.action.equals("Fin")){ }
//        else{
//            this.actionErreur();
//        }
//    }

    @Override
    public void handle(MouseEvent actionEvent){
        Button but = (Button) actionEvent.getSource();

        if (actionEvent.getButton().equals(MouseButton.PRIMARY)){
            this.actionClic(but);
            this.verification();
        }
        else if (actionEvent.getButton().equals(MouseButton.SECONDARY)){
            this.actionDrapeau(but);
            this.verification();
        }
        else if (this.action.equals("Fin")){ }
        else{
            this.actionErreur();
        }
    }


    public void actionClic(Button button){
        if (button.getText().equals("")) {
            List<Integer> coordonnees = this.getCoordonnees(button.getId());
            int X = coordonnees.get(1);
            int Y = coordonnees.get(0);
            if (this.mj.getGrille().get(Y).get(X) == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Perdu");
                al.setHeaderText("Vous avez perdu");
                al.setContentText("Il faut redémarrer le jeu pour faire une autre partie");
                al.showAndWait();
                this.vj.butFin(false);
                this.affichageFin();
            }

            else if (this.mj.getGrille().get(Y).get(X) == 0) {
                HashSet<List<Integer>> casesDecouverte = new HashSet<>();
                List<List<Integer>> casesSur = mj.decouverte(Arrays.asList(X, Y));
                int taille = grille.getColumnCount();
                for (List<Integer> coordonnee : casesSur) {
                    Button but2 = (Button) grille.getChildren().get(coordonnee.get(0) + coordonnee.get(1) * taille);
                    but2.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    but2.setText(" ");
                    casesDecouverte.add(coordonnee);
                }
                for (List<Integer> coordonnee : casesDecouverte){
                    int x = coordonnee.get(0);
                    int y = coordonnee.get(1);
                    if (x > 0){
                        Button but = (Button)this.grille.getChildren().get((x-1) + taille * y);
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y).get(x-1));
                        }
                    }
                    if (x < taille-1){
                        Button but = (Button)this.grille.getChildren().get((x+1) + taille * y);
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y).get(x+1));
                        }
                    }
                    if (y > 0){
                        Button but = (Button)this.grille.getChildren().get(x + taille * (y-1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y-1).get(x));
                        }
                    }
                    if (y < taille-1){
                        Button but = (Button)this.grille.getChildren().get(x + taille * (y+1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y+1).get(x));
                        }
                    }

                    if (x > 0 && y > 0){
                        Button but = (Button)this.grille.getChildren().get((x-1) + taille * (y-1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y-1).get(x-1));
                        }
                    }
                    if (x < taille-1 && y > 0){
                        Button but = (Button)this.grille.getChildren().get((x+1) + taille * (y-1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y-1).get(x+1));
                        }
                    }
                    if (x > 0 && y < taille-1){
                        Button but = (Button)this.grille.getChildren().get((x-1) + taille * (y+1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y+1).get(x-1));
                        }
                    }
                    if (x < taille-1 && y < taille-1){
                        Button but = (Button)this.grille.getChildren().get((x+1) + taille * (y+1));
                        if (!but.getText().equals(" ")){
                            but.setText("" + this.mj.getGrille().get(y+1).get(x+1));
                        }
                    }
                }
            }

            else {
                button.setText("" + this.mj.getGrille().get(Y).get(X));
            }
        }
    }

    public void actionDrapeau(Button but){
        if (but.getText().equals("")){
            boolean reussite = this.mj.poserDrapeau();
            if (reussite){
                but.setText("D");
                lDrapeaux.setText("Drapeaux restants : " + this.mj.getNbDrapeau());
            }
        }
        else if (but.getText() == "D"){
            this.mj.enleverDrapeau();
            but.setText("");
            lDrapeaux.setText("Drapeaux restants : " + this.mj.getNbDrapeau());
        }
    }

    public void actionErreur(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Erreur");
        al.setHeaderText("ActionError");
        al.setContentText("Il faut sélectionner une action pour que le jeu fonctionne correctement");
        al.showAndWait();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Integer> getCoordonnees(String id){
        String nb1 = "";
        String nb2 = "";
        String etat = "nb1";
        for (int i = 0; i < id.length(); i++){
            Character c = id.charAt(i);
            if (!c.equals(',')){
                if (etat.equals("nb1")){
                    nb1 += c;
                }
                else{
                    nb2 += c;
                }
            }
            else{
                etat = "nb2";
            }
        }
        return Arrays.asList(new Integer(nb1), new Integer(nb2));
    }

    public void affichageFin(){
        int taille = grille.getColumnCount();
        for (int i = 0; i < taille*taille-1; i++){
            Button but = (Button) this.grille.getChildren().get(i);
            if (but.getText().equals("D")){
                int y = i / taille;
                int x = i - y*taille;
                if (this.mj.getGrille().get(y).get(x) == -1){
                    but.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else{
                    but.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
            int y = i / taille;
            int x = i - y*taille;
            if (this.mj.getGrille().get(y).get(x) == -1){
                but.setText("M");
            }
        }
        this.action = "Fin";
    }

    public void verification(){
        boolean test = false;
        int taille = grille.getColumnCount();
        for (int i = 0; i < taille*taille-1; i++){
            Button but = (Button) this.grille.getChildren().get(i);
            if (but.getText().equals("")){
                test = true;
            }
        }
        if (!test){
            this.vj.butFin(true);
            affichageFin();
            System.out.println("Réussite !");
        }

    }

    public void debut(){
        HashSet<List<Integer>> casesDecouverte = new HashSet<>();
        Random r = new Random();
        int taille = this.mj.getTaille();
        boolean test = true;
        while (test){
            int y = r.nextInt(taille-1);
            int x = r.nextInt(taille-1);
            if (this.mj.getGrille().get(y).get(x) == 0){
                List<List<Integer>> casesSur = mj.decouverte(Arrays.asList(x, y));
                for (List<Integer> coordonnee : casesSur) {
                    Button but2 = (Button) grille.getChildren().get(coordonnee.get(0) + coordonnee.get(1) * taille);
                    but2.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    but2.setText(" ");
                    casesDecouverte.add(coordonnee);
                }
                test = false;
            }
        }

        for (List<Integer> coordonnee : casesDecouverte){
            int x = coordonnee.get(0);
            int y = coordonnee.get(1);
            if (x > 0){
                Button but = (Button)this.grille.getChildren().get((x-1) + taille * y);
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y).get(x-1));
                }
            }
            if (x < taille-1){
                Button but = (Button)this.grille.getChildren().get((x+1) + taille * y);
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y).get(x+1));
                }
            }
            if (y > 0){
                Button but = (Button)this.grille.getChildren().get(x + taille * (y-1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y-1).get(x));
                }
            }
            if (y < taille-1){
                Button but = (Button)this.grille.getChildren().get(x + taille * (y+1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y+1).get(x));
                }
            }

            if (x > 0 && y > 0){
                Button but = (Button)this.grille.getChildren().get((x-1) + taille * (y-1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y-1).get(x-1));
                }
            }
            if (x < taille-1 && y > 0){
                Button but = (Button)this.grille.getChildren().get((x+1) + taille * (y-1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y-1).get(x+1));
                }
            }
            if (x > 0 && y < taille-1){
                Button but = (Button)this.grille.getChildren().get((x-1) + taille * (y+1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y+1).get(x-1));
                }
            }
            if (x < taille-1 && y < taille-1){
                Button but = (Button)this.grille.getChildren().get((x+1) + taille * (y+1));
                if (!but.getText().equals(" ")){
                    but.setText("" + this.mj.getGrille().get(y+1).get(x+1));
                }
            }
        }
    }
}
