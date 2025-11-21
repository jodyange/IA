package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import modelling.Variable;
import planning.*;

public class DemoExo8 {
    public static void main(String[] args) {
        BlocksWorldAction blocks = new BlocksWorldAction(3, 3);

        List<List<Integer>> goal = Arrays.asList(Arrays.asList(0,1),Arrays.asList(2),Arrays.asList());
        List<List<Integer>> initial = Arrays.asList(Arrays.asList(2,0),Arrays.asList(1),Arrays.asList());

        Map<Variable,Object> etatInitial = blocks.getState(initial);// totalement specifiée, état initial
        Map<Variable,Object> etatGoalComplet = blocks.getState(goal);

        Set<Action> actions = blocks.getAllActions();

        Map<Variable,Object> etatGoal = new HashMap<>();
        for (Map.Entry<Variable,Object> e : etatGoalComplet.entrySet()) {
            Variable v = e.getKey();
            Object val = e.getValue();

            if (v.getName().startsWith("on")) {
                etatGoal.put(v, val);
            }
        }

        Goal basicGoal = new BasicGoal(etatGoal);

        System.out.println("\n <***********CALCUL TEMPS HEURISTICS*****************> \n ");

        Heuristic heuristic1 = new HeuristicBadlyPlaced(etatGoal);
        System.out.println("Heuristic 1 : " + heuristic1.estimate(etatInitial) + " deplacement(s)");

        Heuristic heuristic2 = new HeuristicWrongPile(etatGoal);
        System.out.println("Heuristic 2 : " + heuristic2.estimate(etatInitial) + " deplacement(s)");
        

        Map<Variable,Object> goalPartiel = new HashMap<>();
        goalPartiel.put(blocks.onVariables.get(1), 0); // juste 1 sur 0
        Goal basicGoal2 = new BasicGoal(goalPartiel);

        System.out.println("==== PLAN POUR BUT PARTIEL ====");

        System.out.println("[   PLANIFICATEUR BFS   ]" ); 
        BFSPlanner bfs =  new BFSPlanner(etatInitial, actions, basicGoal2);
        bfs.activateNodeCount(true);
        long InitialTimeBfs = System.nanoTime();
        List<Action> bfsPlan = bfs.plan();
        System.out.println("Plan trouvé pour BFSPlanner  : " + bfsPlan + "\n");
        long endTimeBfs =  System.nanoTime();

        System.out.println("Nombre de noeuds exploré (BFS) : " + bfs.getNbNoeuds() + "\n");
        System.out.println("Temps de calcul : " + (endTimeBfs- InitialTimeBfs) / 1_000_000 + " milliseconds \n");


        System.out.println("[   PLANIFICATEUR DFS   ]" ); 
        DFSPlanner dfsPlanner =  new DFSPlanner(etatInitial, actions, basicGoal);
        dfsPlanner.activateNodeCount(true);
        long InitialTimeDfs = System.nanoTime();
        List<Action> dfsPlan = dfsPlanner.plan();
        System.out.println("Plan trouvé pour DFSPlanner  : " + dfsPlan + "\n");
        long endTimeDfs =  System.nanoTime();

        System.out.println("Nombre de noeuds exploré (DFS) : " + dfsPlanner.getNbNoeuds() + "\n");
        System.out.println("Temps de calcul  est : " + (endTimeDfs - InitialTimeDfs) / 1_000_000 + " milliseconds \n");
        

        System.out.println("[   PLANIFICATEUR DIJKSTRA   ]"); 
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInitial, actions, basicGoal);
        dijkstraPlanner.activateNodeCount(true);
        long InitialTimeDijkstra = System.nanoTime();
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        System.out.println("Plan trouvé pour  dijkstra : " + dijkstraPlan + "\n");
        long endTimeDijkstra =  System.nanoTime();

        System.out.println("Nombre de noeuds exploré (DIJKSTRA) : " + dijkstraPlanner.getNbNoeuds() + "\n");
        System.out.println("Temps de calcul  est : " + (endTimeDijkstra - InitialTimeDijkstra) / 1_000_000 + " milliseconds \n");


        System.out.println("[   PLANIFICATEUR A*   ]" ); 

        AStarPlanner aStarPlanner = new AStarPlanner(etatInitial, actions, basicGoal, heuristic1);
        aStarPlanner.activateNodeCount(true);
        long InitialTimeAstar = System.nanoTime();
        List<Action> aStarPlan = aStarPlanner.plan();
        System.out.println("Plan AStarPlanner  : " + aStarPlan + "\n");
        long endTimeAstar =  System.nanoTime();

        System.out.println("Nombre de noeuds exploré (AStarPlanner) : " + aStarPlanner.getNbNoeuds() + "\n");
        System.out.println("Temps de calcul : " + (endTimeAstar - InitialTimeAstar) / 1_000_000 + " milliseconds \n");


        // BlocksWorldDisplay bd2 = new BlocksWorldDisplay(4, blocks,"ASTAR","topRight");
        // bd2.showPlan(etatGoalComplet, aStarPlan);

        // BlocksWorldDisplay bd3 = new BlocksWorldDisplay(4, blocks,"DIJKSTRA","bottomLeft");
        // bd3.showPlan(etatInitial, dijkstraPlan);

        // BlocksWorldDisplay bd4 = new BlocksWorldDisplay(4, blocks,"DFS","bottomRight");
        // bd4.showPlan(etatInitial, dfsPlan);

        // BlocksWorldDisplay bd = new BlocksWorldDisplay(4, blocks,"BFS","topLeft");
        // bd.showPlan(etatInitial, bfsPlan);
    }
}
