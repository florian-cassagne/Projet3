import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MachineTuring {
    ArrayList<String> etats;
    ArrayList<String> langage;
    ArrayList<String[]> transitions;
    String etatInitial;
    String etatFinal;
    String motEntree;
    String motSortie;
    String etatCourant;
    int positionCurseur;
    boolean estMotLu;
    boolean estMotValide;


    public MachineTuring(String nomFichier, String motEntree) throws Exception {
        this.etats = new ArrayList<>();
        this.langage = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.motEntree = motEntree;
        this.motSortie = motEntree;
        this.positionCurseur = 0;
        this.estMotValide = true;

        BufferedReader lecteur = new BufferedReader(new FileReader(nomFichier));
        String ligneCourante;

        String[] etats = lecteur.readLine().split(" ");
        etats = Arrays.copyOfRange(etats, 1, etats.length);
        Collections.addAll(this.etats, etats);

        String[] langage = lecteur.readLine().split(" ");
        Collections.addAll(this.langage, langage);

        this.etatInitial = lecteur.readLine().split(" ")[1];
        this.etatFinal = lecteur.readLine().split(" ")[1];
        this.etatCourant = etatInitial;

        while((ligneCourante = lecteur.readLine()) != null){
            System.out.println(ligneCourante);
            String[] transitionCourante = ligneCourante.split(" ", 5);
            if(transitionCourante.length < 5){
                throw new Exception("Le programme de la machine de turing contient des transitions incomplètes." +
                    "Une transition est définie par 5 paramètres :" +
                    "<étatDébut> <étatFin> <valeurLecture> <valeurÉcriture> <sens>");
            }
            if(!(this.etats.contains(transitionCourante[0]) &&
                this.etats.contains(transitionCourante[1]) &&
                this.langage.contains(transitionCourante[2]) &&
                this.langage.contains(transitionCourante[3]) &&
                (transitionCourante[4].equals("R") || transitionCourante[4].equals("L"))
                )
            ) {
                throw new Exception("Le programme de la machine de Turing contient soit des états," +
                    "soit des transitions," +
                    "ou soit un sens de direction autre que 'R' et 'L'");
            }
            transitions.add(transitionCourante);
        }

    }

    public void reconnaitreMot() throws Exception {
        while(!estDansEtatFinal()){
            String[] transition = getTransitionAvecVerifPossibilite();
            System.out.println(Arrays.toString(transition));
            setValeurCurseur(transition[3]);
            setEtatCourant(transition[1]);
            if(transition[4].equals("R"))
                deplacementCurseurDroite();
            else if(transition[4].equals("L"))
                deplacementCurseurGauche();
            else
                throw new Exception("Il existe une transition auquel sa valeur de direction est autre que 'R' ou 'L', cela n'est pas possible." +
                    "Merci de mettre à jour le programme de la machine de Turing !");
            afficherPositionCurseur();
            if((motSortie.charAt(positionCurseur) == '■') && estDansEtatFinal()){
                System.out.println();
            }

            Thread.sleep(2500);
        }
    }


    public String[] getTransitionAvecVerifPossibilite(){
        if((positionCurseur >= 0) && (positionCurseur < motSortie.length())){
            if(getTransition() != null){
                return getTransition();
            }
        }

        return null;
    }

    public void validationMot(){
        if(estDansEtatFinal()){
            System.out.println("Le mot " + motEntree + " est VALIDE !!!");
        }
        else{
            System.err.println("Le mot " + motEntree + " n'est PAS VALIDE :(");
        }
    }

    public void deplacementCurseurDroite(){
        if(positionCurseur == motSortie.length() - 1){
            motSortie += "■";
        }
        positionCurseur++;
    }

    public void deplacementCurseurGauche(){
        if(positionCurseur == 0){
            motSortie = "■" + motSortie;
        }
    }

    public void setValeurCurseur(String nouvelleValeur){
        StringBuilder newMotSortie = new StringBuilder(motSortie);
        newMotSortie.setCharAt(positionCurseur, nouvelleValeur.charAt(0));
        motSortie = String.valueOf(newMotSortie);
    }

    public String[] getTransition(String etatDebut, String valeurLecture){
        for (String[] transition : transitions) {
            if (Objects.equals(transition[0], etatDebut) &&
                Objects.equals(transition[2], valeurLecture)) {
                return transition;
            }
        }

        estMotValide = false;

        return null;
    }

    public String[] getTransition(){
        for (String[] transition : transitions) {
            if (Objects.equals(transition[0], etatCourant) &&
                    Objects.equals(transition[2], getValeurDansPosition(positionCurseur))) {
                return transition;
            }
        }

        estMotValide = false;

        return null;
    }

    public String getEtatInitial(){
        return etatInitial;
    }

    public String getEtatFinal(){
        return etatFinal;
    }

    public String getEtatCourant(){
        return etatCourant;
    }

    public String getMotEntree(){
        return motEntree;
    }

    public String getMotSortie(){
        return motSortie;
    }

    public String getValeurActuelle(){
        return String.valueOf(motSortie.charAt(positionCurseur));
    }

    public String getValeurDansPosition(int positionCurseur){
        return String.valueOf(motSortie.charAt(positionCurseur));
    }

    public void setEtatCourant(String nouvelEtatCourant){
        if(etats.contains(nouvelEtatCourant)){
            etatCourant = nouvelEtatCourant;
        }
        else{
            System.err.println("Vous avez renseigné un état qui n'existe pas");
        }
    }

    public boolean estDansEtatFinal(){
        return Objects.equals(etatCourant, etatFinal);
    }

    public void afficherPositionCurseur(){
        System.out.println("curseur = " + motSortie.charAt(positionCurseur));
        System.out.println("motSortie = " + motSortie);
    }



}
