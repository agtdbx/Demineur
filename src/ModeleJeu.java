import java.awt.*;
import java.util.*;
import java.util.List;

public class ModeleJeu {
    private List<List<Integer>> grille;
    private int nbDrapeau, taille;

    public ModeleJeu(int taille, int nbMine, int nbDrapeau){
        this.nbDrapeau = nbDrapeau;
        this.grille = new ArrayList<>();
        this.taille = taille;

        for (int x = 0; x < taille; x++){
            this.grille.add(new ArrayList<>());
            for (int y = 0; y < taille; y++){
                this.grille.get(x).add(0);
            }
        }

        Random r = new Random();
        while (nbMine > 0){
            int x = r.nextInt(taille-1);
            int y = r.nextInt(taille-1);

            if (this.grille.get(y).get(x) == 0){
                this.grille.get(y).set(x, -1);
                nbMine -= 1;
            }
        }

        for (int x = 0; x < taille; x++){
            for (int y = 0; y < taille; y++){
                if (this.grille.get(y).get(x) != -1) {
                    int count = 0;
                    if (x > 0 && this.grille.get(y).get(x-1) == -1) {
                        count += 1;
                    }
                    if (x < this.taille-1 && this.grille.get(y).get(x+1) == -1) {
                        count += 1;
                    }
                    if (y > 0 && this.grille.get(y-1).get(x) == -1) {
                        count += 1;
                    }
                    if (y <  this.taille-1 && this.grille.get(y+1).get(x) == -1) {
                        count += 1;
                    }
                    if ((x > 0 && y > 0) && this.grille.get(y-1).get(x-1) == -1) {
                        count += 1;
                    }
                    if ((x <  this.taille-1 && y > 0) && this.grille.get(y-1).get(x+1) == -1) {
                        count += 1;
                    }
                    if ((x > 0 && y <  this.taille-1) && this.grille.get(y+1).get(x-1) == -1) {
                        count += 1;
                    }
                    if ((x <  this.taille-1 && y <  this.taille-1) && this.grille.get(y+1).get(x+1) == -1) {
                        count += 1;
                    }
                    this.grille.get(y).set(x, count);
                }
            }
        }
    }

    public List<List<Integer>> getGrille() {
        return grille;
    }

    public int getTaille() {
        return taille;
    }

    public int getNbDrapeau() {
        return nbDrapeau;
    }

    public boolean poserDrapeau(){
        if (nbDrapeau -1 >= 0){
            nbDrapeau -=1;
            return true;
        }
        else{
            return false;
        }
    }

    public void enleverDrapeau(){ nbDrapeau += 1; }

    public List<List<Integer>> decouverte(List<Integer> coordonneeInitial){
        HashSet<List<Integer>> fait = new HashSet();
        HashSet<List<Integer>> afaire = new HashSet();
        afaire.add(coordonneeInitial);

        while (afaire.size()>0){
            HashSet<List<Integer>> nouveaux = new HashSet();

            for (List<Integer> coordonnee : afaire){
                int x = coordonnee.get(0);
                int y = coordonnee.get(1);
                if (x > 0 && this.grille.get(y).get(x-1) == 0 && !fait.contains(Arrays.asList(x-1, y))) {
                    nouveaux.add(Arrays.asList(x-1, y));
                }
                if (x < taille-1 && this.grille.get(y).get(x+1) == 0 && !fait.contains(Arrays.asList(x+1, y))) {
                    nouveaux.add(Arrays.asList(x+1, y));
                }
                if (y > 0 && this.grille.get(y-1).get(x) == 0 && !fait.contains(Arrays.asList(x, y-1))) {
                    nouveaux.add(Arrays.asList(x, y-1));
                }
                if (y < taille-1 && this.grille.get(y+1).get(x) == 0 && !fait.contains(Arrays.asList(x, y+1))) {
                    nouveaux.add(Arrays.asList(x, y+1));
                }
                fait.add(coordonnee);
            }
            afaire = nouveaux;
        }
        List<List<Integer>> rep = new ArrayList<>();
        for (List<Integer> coordonnee : fait){
            rep.add(coordonnee);
        }
        return rep;
    }
}
