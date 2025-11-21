package blocksworld.demo;

import java.util.*;
import blocksworld.*;

import modelling.Constraint;
import modelling.Variable;

public class DemoExo5 {

    public static void main(String[] args) {

        // ----------------- MONDE CROISSANT -----------------
        System.out.println("****************************************** MONDE DES BLOCS CROISSANT  ******************************************************\n");

        // 8 blocs, 2 piles
        ConfigIncreasing world1 = new ConfigIncreasing(8, 2);
        Set<Constraint> constraints1 = world1.getIncreasingConstraints();

        
        List<List<Integer>> piles1 = Arrays.asList(
                Arrays.asList(0, 1, 2, 5),
                Arrays.asList(3, 4, 6, 7)
        );
        Map<Variable, Object> worldIncreasing1 = world1.getState(piles1);

        boolean isIncreasing = false;
        isIncreasing = allSatisfied(worldIncreasing1, constraints1);
        System.out.println("Le monde\n\n" + worldIncreasing1 + "\n\nest-il croissant ? : " + isIncreasing + "\n");

        // Monde croissant INCORRECT
        List<List<Integer>> piles2 = Arrays.asList(
                Arrays.asList(7, 1, 2, 5),
                Arrays.asList(4, 3, 6, 0)
        );
        Map<Variable, Object> worldNotIncreasing = world1.getState(piles2);

        boolean isNotIncreasing = allSatisfied(worldNotIncreasing, constraints1);
        System.out.println("(False attendu) Le monde\n\n" + worldNotIncreasing + "\n\nest-il croissant ? : " + isNotIncreasing + "\n");


        // ----------------- MONDE REGULIER -----------------
        System.out.println("********************************************* MONDE DES BLOCS REGULIER  ******************************************************\n");

        ConfigRegular world2 = new ConfigRegular(8, 2);
        Set<Constraint> constraints2 = world2.getRegularConstraints();

        // Monde régulier correct : suites (0,2,4,6) et (1,3,5,7)
        List<List<Integer>> piles3 = Arrays.asList(
                Arrays.asList(0, 2, 4, 6),
                Arrays.asList(1, 3, 5, 7)
        );
        Map<Variable, Object> worldRegular1 = world2.getState(piles3);

        boolean isRegular = allSatisfied(worldRegular1, constraints2);
        System.out.println("Le monde\n\n" + worldRegular1 + "\n\nest-il regulier ? : " + isRegular + "\n");

        // Monde NON régulier
        List<List<Integer>> piles4 = Arrays.asList(
                Arrays.asList(0, 5, 1, 4, 6),
                Arrays.asList(3, 2, 7)
        );
        Map<Variable, Object> worldNotRegular = world2.getState(piles4);

        boolean isNotRegular = allSatisfied(worldNotRegular, constraints2);
        System.out.println("(False attendu) Le monde\n\n" + worldNotRegular + "\n\n est-il regulier ? : " + isNotRegular + "\n");


        // --------- MONDE ET CROISSANT ET REGULIER ----------
        System.out.println("*************************************** MONDE DES BLOCS CROISSANT ET REGULIER  *********************************************\n");

        // Candidat : chaque pile est une suite régulière
        List<List<Integer>> piles5 = Arrays.asList(
                Arrays.asList(1, 3, 5, 7),
                Arrays.asList(0, 2, 4, 6)
        );

        // ATTENTION : il faut construire l'état pour CHAQUE monde séparément
        Map<Variable, Object> worldIncreasing2 = world1.getState(piles5);
        Map<Variable, Object> worldRegular2 = world2.getState(piles5);

        boolean croissantEtRegulier_1 = allSatisfied(worldIncreasing2, constraints1); //increasing
        boolean croissantEtRegulier_2 = allSatisfied(worldRegular2, constraints2); //Regular

        System.out.println("Le monde croiesant \n\n" + worldIncreasing2+ "\n");
        System.out.println("Ce monde est croissant ? : " + croissantEtRegulier_1 + "\n");

        System.out.println("Le même monde regular\n\n" + worldRegular2 + "\n");
        System.out.println("Ce monde est regular ? : " + croissantEtRegulier_2 + "\n");
    

        // ----------------- MONDE DECROISSANT -----------------
        System.out.println("****************************************** MONDE DES BLOCS CROISSANT  ******************************************************\n");

        // 8 blocs, 2 piles
        ConfigDecreasing world3 = new ConfigDecreasing(8, 2);
        Set<Constraint> constraints3 = world3.getDecreasingConstraints();

        
        List<List<Integer>> piles6 = Arrays.asList(
                Arrays.asList(7, 5, 2, 1),
                Arrays.asList(6, 4, 3, 0)
        );
        Map<Variable, Object> worldDecreasing1 = world3.getState(piles6);

        boolean isDecreasing = false;
        isDecreasing = allSatisfied(worldDecreasing1, constraints3);
        System.out.println("Le monde\n\n" + worldDecreasing1 + "\n\nest-il décroissant ? : " + isDecreasing + "\n");

        // Monde décroissant INCORRECT
        List<List<Integer>> piles7 = Arrays.asList(
                Arrays.asList(0, 1, 2, 5),
                Arrays.asList(3, 4, 6, 7)
        );
        Map<Variable, Object> worldNotDecreasing = world3.getState(piles7);

        boolean isNotDecreasing = allSatisfied(worldNotDecreasing, constraints3);
        System.out.println("(False attendu) Le monde\n\n" + worldNotDecreasing + "\n\nest-il décroissant ? : " + isNotDecreasing + "\n");
    

        System.out.println("****************************** MONDE AVEC UNE SEULE PILE ******************************\n");

        // 4 blocs, 2 piles
        ConfigOnePile world4 = new ConfigOnePile(4, 2);
        Set<Constraint> constraints4 = world4.getAllConstraints();
        List<List<Integer>> piles8 = Arrays.asList(
                Arrays.asList(0, 1, 2, 3),
                Arrays.asList()
        );

        Map<Variable, Object> worldOnePile1 = world4.getState(piles8);
        boolean isOnePile = allSatisfied(worldOnePile1, constraints4);
        System.out.println("Le monde\n\n" + worldOnePile1 + "\n\n est il une seule pile? : " + isOnePile + "\n");


        // ===================== NE respecte PAS la contrainte =====================

        List<List<Integer>> piles9= Arrays.asList(
                Arrays.asList(0, 2),          // pile 0
                Arrays.asList(1, 3)           // pile 1
        );


        Map<Variable, Object> worldNotOnePile1 = world4.getState(piles9);
        boolean isNotOnePile = allSatisfied(worldNotOnePile1, constraints4);
        System.out.println("False attendu => Le monde\n\n" + worldNotOnePile1 + "\n\nest- est il une seule piles ? : " + isNotOnePile + "\n");    }
    

    /**
     * Fonction pour eviter davoir for + isSatisfied pour tester chaque contraintes :
     * retourne true si l'assignation "world" satisfait toutes les contraintes.
     */
    private static boolean allSatisfied(Map<Variable, Object> world, Set<Constraint> contraintes) {
        for (Constraint c : contraintes) {
            if (!c.isSatisfiedBy(world)) {
                return false;
            }
        }
        return true;
    }
}
