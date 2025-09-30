package planning;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class Demo {


     public static void main(String[] args) {


        //Creation des variables
        Variable x = new Variable("x", Set.of(1, 2, 3));
        Variable y = new Variable("y", Set.of(1, 2, 3));
        Variable z = new Variable("z", Set.of(2, 3, 4));
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("z = " + z);
        System.out.println("\n");



        System.out.println("Test de la méthode isApplicable()");
        Map<Variable,Object> p1 = new HashMap<>();
        p1.put(x,1);
        p1.put(z,2);
        Map<Variable, Object> e1 = new HashMap<>();
        e1.put(x,3);
        e1.put(y,3);

        BasicAction act1 = new BasicAction(p1, e1, 1 );
        Map<Variable, Object> s = new HashMap<>(); //état courant
        s.put(x,1);
        s.put(y, 2);
        s.put(z,2);
        //System.out.println("Liste des actions possible");
        System.out.println("état initial s : "+ s);
        System.out.println("Action : " + act1);
        System.out.println("Is Applicable : "+ act1.isApplicable(s));
        Map<Variable, Object> su = act1.successor(s);
        System.out.println("état successeur: " + su);
        System.out.println("\n");



        System.out.println("Test de la méthode isSatisfiedBy()");
        Variable a = new Variable("a", Set.of("a","b"));
        Variable b = new Variable("b", Set.of("c","d"));
        Variable t = new Variable("t", Set.of("d","f"));
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("t = " + t);
        System.out.println("\n");

        Map<Variable, Object> goal = Map.of(a, "a", b, "c", t, "d");
        BasicGoal g = new BasicGoal(goal);

        Map<Variable, Object> st1 = Map.of(a, "a", b, "c", t, "d", new Variable("x", Set.of("a","b")), "a");
        Map<Variable, Object> st2 = Map.of(a, "a", b, "a", t, "a");
        System.out.println("But est satisfait pour st1 : "+ g.isSatisfiedBy(st1));
        System.out.println("But est satisfait pour st2 : "+ g.isSatisfiedBy(st2));




      


        

        





     }
    
}
