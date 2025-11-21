package blocksworld.demo;

import java.util.Random;
import java.util.Set;

import blocksworld.*;
import cp.BacktrackSolver;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.NbConstraintsVariableHeuristic;
import cp.RandomValueHeuristic;
import modelling.Constraint;

public class DemoExo10 {

    public static void main(String[] args) {

        ConfigIncreasing increasingBlocks = new ConfigIncreasing(5, 3);
        Set<Constraint> constraints = increasingBlocks.getIncreasingConstraints();

        // MACSolver
        MACSolver mac = new MACSolver(increasingBlocks.getAllVariables(), constraints);
        long s1 = System.nanoTime();
        Object sol1 = mac.solve();
        long e1 = System.nanoTime();
        System.out.println("========== MACSolver ==========");
        System.out.println("Solution : " + sol1);
        System.out.println("Temps d'exécution : " + (e1 - s1) / 1_000_000 + " ms\n");

        // BacktrackSolver
        BacktrackSolver back = new BacktrackSolver(increasingBlocks.getAllVariables(), constraints);
        long s2 = System.nanoTime();
        Object sol2 = back.solve();
        long e2 = System.nanoTime();
        System.out.println("========== BacktrackSolver ==========");
        System.out.println("Solution : " + sol2);
        System.out.println("Temps d'exécution : " + (e2 - s2) / 1_000_000 + " ms\n");

        // Heuristic MAC Solver en fonction du nombre de contraintes
        NbConstraintsVariableHeuristic varH1 = new NbConstraintsVariableHeuristic(constraints, false);
        RandomValueHeuristic valH1 = new RandomValueHeuristic(new Random());
        HeuristicMACSolver hmac1 = new HeuristicMACSolver(increasingBlocks.getAllVariables(), constraints, varH1, valH1);

        long s3 = System.nanoTime();
        Object sol3 = hmac1.solve();
        long e3 = System.nanoTime();
        System.out.println("========== Heuristic MAC Solver NbConstraintsVariableHeuristic==========");
        System.out.println("Solution : " + sol3);
        System.out.println("Temps d'exécution : " + (e3 - s3) / 1_000_000 + " ms\n");


        System.out.println("\n*****************************************MONDE DE BLOCS CROISSANT ET REGULIER************************************************\n");
        
        ConfigRegular regularBlocks = new ConfigRegular(5, 3);
        constraints.addAll(regularBlocks.getRegularConstraints());
        constraints.addAll(regularBlocks.getAllConstraints());

        // MACSolver
        MACSolver mac2 = new MACSolver(increasingBlocks.getAllVariables(), constraints);
        long s4 = System.nanoTime();
        Object sol4 = mac2.solve();
        long e4 = System.nanoTime();
        System.out.println("========== MACSolver ==========");
        System.out.println("Solution : " + sol4);
        System.out.println("Temps d'exécution : " + (e4 - s4) / 1_000_000 + " ms\n");

        // BacktrackSolver
        BacktrackSolver back2 = new BacktrackSolver(increasingBlocks.getAllVariables(), constraints);
        long s5 = System.nanoTime();
        Object sol5 = back2.solve();
        long e5 = System.nanoTime();
        System.out.println("========== BacktrackSolver ==========");
        System.out.println("Solution : " + sol5);
        System.out.println("Temps d'exécution : " + (e5 - s5) / 1_000_000 + " ms\n");

        // Heuristic MAC Solver en fonction du nombre de contraintes
        NbConstraintsVariableHeuristic varH2 = new NbConstraintsVariableHeuristic(constraints, false);
        RandomValueHeuristic valH2= new RandomValueHeuristic(new Random());
        HeuristicMACSolver hmac2 = new HeuristicMACSolver(increasingBlocks.getAllVariables(), constraints, varH2, valH2);

        long s6 = System.nanoTime();
        Object sol6 = hmac2.solve();
        long e6 = System.nanoTime();
        System.out.println("========== Heuristic MAC Solver NbConstraintsVariableHeuristic==========");
        System.out.println("Solution : " + sol6);
        System.out.println("Temps d'exécution : " + (e6 - s6) / 1_000_000 + " ms\n");
    
        System.out.println("\n*****************************************MONDE DE BLOCS UNE SEULE PILE************************************************\n");

        ConfigOnePile onePile = new ConfigOnePile(5, 3);
        Set<Constraint> constraints2 = increasingBlocks.getIncreasingConstraints();

        // MACSolver
        MACSolver mac3 = new MACSolver(onePile.getAllVariables(), constraints2);
        long s7 = System.nanoTime();
        Object sol7 = mac3.solve();
        long e7 = System.nanoTime();
        System.out.println("========== MACSolver ==========");
        System.out.println("Solution : " + sol7);
        System.out.println("Temps d'exécution : " + (e7 - s7) / 1_000_000 + " ms\n");

        // BacktrackSolver
        BacktrackSolver back3 = new BacktrackSolver(onePile.getAllVariables(), constraints2);
        long s8 = System.nanoTime();
        Object sol8 = back3.solve();
        long e8 = System.nanoTime();
        System.out.println("========== BacktrackSolver ==========");
        System.out.println("Solution : " + sol8);
        System.out.println("Temps d'exécution : " + (e8 - s8) / 1_000_000 + " ms\n");

        // Heuristic MAC Solver en fonction du nombre de contraintes
        NbConstraintsVariableHeuristic varH3 = new NbConstraintsVariableHeuristic(constraints2, false);
        RandomValueHeuristic valH3 = new RandomValueHeuristic(new Random());
        HeuristicMACSolver hmac3 = new HeuristicMACSolver(onePile.getAllVariables(), constraints2, varH3, valH3);

        long s9 = System.nanoTime();
        Object sol9 = hmac3.solve();
        long e9 = System.nanoTime();
        System.out.println("========== Heuristic MAC Solver NbConstraintsVariableHeuristic==========");
        System.out.println("Solution : " + sol9);
        System.out.println("Temps d'exécution : " + (e9 - s9) / 1_000_000 + " ms\n");
    }
}
