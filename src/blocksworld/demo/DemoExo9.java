package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import cp.*;
import modelling.*;

public class DemoExo9 {

    public static void main(String[] args) {

        ConfigRegular regularBlocks = new ConfigRegular(5, 3);
        Set<Constraint> constraints = regularBlocks.getRegularConstraints();

        // MACSolver
        MACSolver mac = new MACSolver(regularBlocks.getAllVariables(), constraints);
        long s1 = System.nanoTime();
        Object sol1 = mac.solve();
        long e1 = System.nanoTime();
        System.out.println("========== MACSolver ==========");
        System.out.println("Solution : " + sol1);
        System.out.println("Temps d'exécution : " + (e1 - s1) / 1_000_000 + " ms\n");

        // BacktrackSolver
        BacktrackSolver back = new BacktrackSolver(regularBlocks.getAllVariables(), constraints);
        long s2 = System.nanoTime();
        Object sol2 = back.solve();
        long e2 = System.nanoTime();
        System.out.println("========== BacktrackSolver ==========");
        System.out.println("Solution : " + sol2);
        System.out.println("Temps d'exécution : " + (e2 - s2) / 1_000_000 + " ms\n");

        // Heuristic MAC Solver en fonction du nombre de contraintes
        NbConstraintsVariableHeuristic varH1 = new NbConstraintsVariableHeuristic(constraints, false);
        RandomValueHeuristic valH1 = new RandomValueHeuristic(new Random());
        HeuristicMACSolver hmac1 = new HeuristicMACSolver(regularBlocks.getAllVariables(), constraints, varH1, valH1);

        long s3 = System.nanoTime();
        Object sol3 = hmac1.solve();
        long e3 = System.nanoTime();
        System.out.println("========== Heuristic MAC Solver NbConstraintsVariableHeuristic==========");
        System.out.println("Solution : " + sol3);
        System.out.println("Temps d'exécution : " + (e3 - s3) / 1_000_000 + " ms\n");


        // Heuristic MAC Solver en fonction de lataille des domaines
        DomainSizeVariableHeuristic varH2 = new DomainSizeVariableHeuristic(false);
        RandomValueHeuristic valH2 = new RandomValueHeuristic(new Random());
        HeuristicMACSolver hmac2 = new HeuristicMACSolver(regularBlocks.getAllVariables(), constraints, varH2, valH2);

        long s4 = System.nanoTime();
        Object sol4 = hmac2.solve();
        long e4 = System.nanoTime();
        System.out.println("========== Heuristic MAC Solver DomainSizeVariableHeuristic==========");
        System.out.println("Solution : " + sol4);
        System.out.println("Temps d'exécution : " + (e4 - s4) / 1_000_000 + " ms\n");
    }
}
