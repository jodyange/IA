package cp;

import java.util.Map;
import java.util.Set;
import java.util.Random;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Variable;

public class Demo {

    
     public static void main(String[] args) {

          Variable v1 = new Variable("x", Set.of(1, 2, 3));
          Variable v2 = new Variable("y", Set.of(1, 2, 3));
          Variable v3 = new Variable("z", Set.of(1, 2, 3));
          //Variable v4 = new Variable("n4", Set.of('A','B','C','D'));
          System.out.println("Les Listes des variables crées");
          System.out.println("v1 a pour nom " + v1.getName() + " et pour domaine " + v1.getDomain());
          System.out.println("v2 a pour nom " + v2.getName() + " et pour domaine " + v2.getDomain());
          System.out.println("v3 a pour nom " + v3.getName() + " et pour domaine " + v3.getDomain());
          System.out.println("\n");
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
          
          VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(true);  // true : préfère les domaines plus grands
          ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());  // Mélange aléatoire des valeurs

          // Étape 4 : Créer et résoudre avec le HeuristicMACSolver
          HeuristicMACSolver solver2 = new HeuristicMACSolver(variables, constraints, varHeuristic, valHeuristic);

          Map<Variable, Object> solution = solver2.solve();

          // Affichage de la solution
          if (solution != null) {
               System.out.println("\n --------- HEURISTIC ------- \n Solution trouvée :");
               solution.forEach((var, value) -> System.out.println(var.getName() + " = " + value));
          } else {
               System.out.println("HEURISTIC ------ \n Aucune solution trouvée.");
          }


     }
     
}
