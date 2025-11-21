package planning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import modelling.Variable;

public class Demo2 {


     public static void main(String[] args) {
        //Les variables
        Variable v1 = new Variable("n1", Set.of('A','B','C','D'));
        Variable v2 = new Variable("n2", Set.of('A','B','C','D'));
        Variable v3 = new Variable("n3", Set.of('A','B','C','D'));
        Variable v4 = new Variable("n4", Set.of('A','B','C','D'));
        System.out.println("Les Listes des variables crées");
        System.out.println("v1 a pour nom " + v1.getName() + " et pour domaine " + v1.getDomain());
        System.out.println("v2 a pour nom " + v2.getName() + " et pour domaine " + v2.getDomain());
        System.out.println("v3 a pour nom " + v3.getName() + " et pour domaine " + v3.getDomain());
        System.out.println("v4 a pour nom " + v4.getName() + " et pour domaine " + v4.getDomain());
        System.out.println("\n");

        //Creation des instances
        Map<Variable,Object> p1 = new HashMap<>();
        p1.put(v1, 'A');

        Map<Variable,Object> p2 = new HashMap<>();
        p2.put(v1, 'B');

        Map<Variable,Object> p3 = new HashMap<>();
        p3.put(v3, 'A');
        p3.put(v1, 'B');


        // Map<Variable,Object> p4 = new HashMap<>();
        // p4.put(v4, 'D');

        //Des effets
        Map<Variable, Object> e1 = new HashMap<>();
        e1.put(v1, 'B');

        Map<Variable, Object> e2 = new HashMap<>();
        e2.put(v2, 'C');

        Map<Variable, Object> e3 = new HashMap<>();
        e3.put(v3,'D');

        // Map<Variable, Object> e4 = new HashMap<>();
        // e4.put(v4,'B');

        //Actions
        BasicAction act1 = new BasicAction(p1, e1, 1);
        BasicAction act2 = new BasicAction(p2, e2, 5);
        BasicAction act3 = new BasicAction(p3, e3, 50);
        Set<Action> actions = new HashSet<>();
        actions.add(act1);
        actions.add(act2);
        actions.add(act3);

        //état initial
        Map<Variable,Object> init = new HashMap<>();
        init.put(v1, 'A');
        init.put(v2, 'B');
        init.put(v3, 'A');
        init.put(v4, 'D');

        //Buts(goal)
        Map<Variable,Object> but = new HashMap<>();
        but.put(v1, 'B');
        but.put(v2, 'C');
        but.put(v3, 'D');
        but.put(v4, 'D');
        Goal goal = new BasicGoal(but);
        System.out.println("But : " + goal);


        System.out.println("Recherche avec DSF PLANNER");
        DFSPlanner dfsPlanner = new DFSPlanner(init, actions, goal);         
        dfsPlanner.activateNodeCount(true);
        Stack<Action> plan = dfsPlanner.plan();         //Planifier
        System.out.println("Plan ok : " + plan.size() + " étapes :");
        System.out.println(plan);
        System.out.println("Nombre de noeuds explorés : " + dfsPlanner.getNbNoeuds()+"\n");
        

        System.out.println("Recherche avec BSF PLANNER");
        BFSPlanner bfsPlanner = new BFSPlanner(init, actions, goal);
        List<Action> plan1 = bfsPlanner.plan();        //Planifier
        bfsPlanner.activateNodeCount(true);
        System.out.println("Plan ok");
        System.out.println(plan1);
        System.out.println("Nombre de noeuds explorés : " + dfsPlanner.getNbNoeuds()+"\n");


        System.out.println("Recherche avec Dijkstra PLANNER");
        DijkstraPlanner dijkstra = new DijkstraPlanner(init, actions, goal);
        dijkstra.activateNodeCount(true);
        List<Action> plan2 = dijkstra.plan();             //Planifier
        System.out.println("Plan ok");
        System.out.println(plan2);
        System.out.println("Nombre de noeuds explorés : " + dijkstra.getNbNoeuds()+"\n");


        






     }
    
    
    









    }