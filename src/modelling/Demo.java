package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {

        Set<Object> s1 = new HashSet<>();
        s1.add(2);
        s1.add(3);
        
        Set<Object> s2 = new HashSet<>();
        s2.add(1);
        s2.add(2);

        Set<Object> s3 = new HashSet<>();
        s3.add(1);
        s3.add(3);
       
        // Affichage des domaines de valeurs
        System.out.println(" Domaines ");
        System.out.println("s1 ==> : " + s1);
        System.out.println("s2 ==> : " + s2);
        System.out.println("s2 ==> : " + s3);
        System.out.println("\n");


        Variable v1 = new Variable("v1", Set.of(1, 2, 3));
        Variable v2 = new Variable("v2", Set.of(1, 2, 3));
        Variable v3 = new Variable("v3", Set.of(4, 5, 3));
        System.out.println("v1 = " + v1);
        System.out.println("v2 = " + v2);
        System.out.println("v3 = " + v3);
        BooleanVariable b1 = new BooleanVariable("b");
        BooleanVariable b2 = new BooleanVariable("c");
        System.out.println("b1 = " + b1);
        System.out.println("b2 = " + b2);


        System.out.println("v1 est la variable nommée \"" + v1.getName() + "\" qui a pour domaine = " + v1.getDomain());
        System.out.println("v2 est la variable nommée \"" + v2.getName() + "\" qui a pour domaine = " + v2.getDomain());
        System.out.println("v3 est la variable nommée \"" + v3.getName() + "\" qui a pour domaine = " + v3.getDomain());
        System.out.println("b1 est un BooleanVariable qui a pour nom \"" + b1.getName() + "\" et son domaine est = " + b1.getDomain());
        System.out.println("\n");
        
        System.out.println("Test de la méthode equals()");
        if(v1.equals(b1)){
            System.out.println("v1 et b1 sont égales ");
        }else{
            System.out.println("v1 et b1 ne sont pas égales ");
        }

        if(v1.equals(v2)){
            System.out.println("v1 et v2 sont égales ");
        }else{
            System.out.println("v1 et v2 ne sont pas égales ");
        }
        System.out.println("\n");


        DifferenceConstraint df = new DifferenceConstraint(v1, v2);
        Map<Variable, Object> inst1 = Map.of(v1, 1, v2, 2);
        boolean res = df.isSatisfiedBy(inst1);
        if (res == true){
            System.out.println("Contrainte Difference (v1 ≠ v2) satisfaite "+ res);   

        } else{
            System.out.println("Contrainte Difference (v1 = v2) satisfaite "+ res);

        }
        System.out.println("\n");

        Map<Variable, Object> instanciation = Map.of(v1, 1, v2, 2);
        Map<Variable, Object> instanciation2 = Map.of(v1, 3, v2, 2);
        

        Implication implication1 = new Implication(v1, s1, v2, s2);
        Implication implication2 = new Implication(v1, s1, v2, s3);

        System.out.println("implication1 représente la contrainte d'implication entre (v1) ∈ " + s1 + " ->  (v2) ∈ " + s2);
        System.out.println("Vérification de implication1 : " + (implication1.isSatisfiedBy(instanciation) ? "Satisfaite " : "Non satisfaite "));
        System.out.println("\n");
        System.out.println("Implication2 représente la contrainte d'implication entre (v1) ∈ " + s1 + " ->  (v2) ∈ " + s3);
        System.out.println("Vérification de implication2 : " + (implication2.isSatisfiedBy(instanciation2) ? "Satisfaite " : "Non satisfaite "));
        System.out.println("\n");


        
        UnaryConstraint unaryConstraint = new UnaryConstraint(v3, s3);
        Map<Variable, Object> inst2 = Map.of(v3, 2);
        System.out.println("unaryConstraint représente la contrainte unaire pour (v3)" + " avec le domaine " + s3 );
        System.out.println("Vérification de unaryConstranit v3=2 : " + unaryConstraint.isSatisfiedBy(inst2)); // true
        
        
        
        


    }
    
}
