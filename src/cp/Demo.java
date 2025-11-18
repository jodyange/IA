package cp;


import java.util.Map;
import java.util.Random;
import java.util.Set;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Variable;

public class Demo {

    
     public static void main(String[] args) {

          Variable v1 = new Variable("x", Set.of(1, 2, 3));
          Variable v2 = new Variable("y", Set.of(1, 2, 3));
          Variable v3 = new Variable("z", Set.of(1, 2, 3));
          Set<Variable> variables = Set.of(v1,v2,v3);

          Constraint c1 = new DifferenceConstraint(v1, v2);
          Constraint c2 = new DifferenceConstraint(v2, v3);
          Constraint c3 = new DifferenceConstraint(v1, v3);

          Set<Constraint> constraints = Set.of(c1,c2,c3);
          //System.out.println("Cohérence initiale: " + solver.isConsistent(Map.of(v1,1,v2,2)));

          BacktrackSolver solver = new BacktrackSolver(variables, constraints);
          Map<Variable, Object> res = solver.solve();

          if (res != null){
               System.out.println("Solution trouvée !");
               for(Map.Entry<Variable, Object> e : res.entrySet()){
                    System.out.println(e.getKey().getName() + " = " + e.getValue());
               }
          }else{
               System.out.println("Aucune solution trouvée !");
          }

          

           // Heuristiques
          VariableHeuristic varH = new DomainSizeVariableHeuristic(false); // plus petit domaine
          ValueHeuristic valH = new RandomValueHeuristic(new Random());

          // Solveur
          HeuristicMACSolver solver2 = new HeuristicMACSolver(variables, constraints, varH, valH);

          Map<Variable, Object> solution = solver2.solve();

          if (solution != null) {
               System.out.println("\n --------- HEURISTIC ------- \n Solution trouvée :");
               solution.forEach((var, value) -> System.out.println(var.getName() + " = " + value));
          } else {
               System.out.println("HEURISTIC ------ \n Aucune solution trouvée.");
          }
          

     }
     
}
