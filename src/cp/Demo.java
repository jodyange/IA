package cp;

// import java.util.HashMap;
// import java.util.HashSet;
import java.util.Map;
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

          // Constraint df = new DifferenceConstraint(v1, v2);
          // Set<Constraint> constraints1 = Set.of(df);
          // constraints1.add(df);

          // ArcConsistency ac = new ArcConsistency(constraints1);
          // Map<Variable,Set<Object>> domains = new HashMap<>();
          // domains.put(v1, new HashSet<>(Set.of(1,2,3)));
          // domains.put(v2, new HashSet<>(Set.of(1,2)));
          // System.out.println(ac.ac1(domains));
          

     }
     
}
