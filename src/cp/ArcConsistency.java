package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class ArcConsistency {

    private Set<Constraint> constraints;

    public ArcConsistency(Set<Constraint> constraints) {
        //Vérifie que toutes les contraintes sont unaires ou binaires
        for (Constraint c : constraints) {
            if (c.getScope().size() != 1 && c.getScope().size() != 2) {
                throw new IllegalArgumentException("ArcConsistency constraints doit être unaire ou binaire");
            }
        }
        this.constraints = constraints;
    }


    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains){
        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            Variable variable = entry.getKey();
            Set<Object> domain = entry.getValue();
            Set<Object> copyDomain = new HashSet<>(domain);
            for (Object valeur : domain) {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(variable, valeur);
                for (Constraint constraint : this.constraints) {
                    // Vérifie si la contrainte est unaire
                    if (constraint.getScope().size() == 1) {
                        Set<Variable> scope = constraint.getScope();
                        Variable var = scope.iterator().next();
                        if (var.equals(variable)) {
                            if (!constraint.isSatisfiedBy(instanciation)) {
                                copyDomain.remove(valeur);
                            }
                        }

                    }

                }
            }
            domains.put(variable, copyDomain);
        }

        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            if (entry.getValue().size() == 0) {
                return false;
            }

        }
        return true;

    }


    public boolean revise(Variable v1, Set<Object> D1, Variable v2, Set<Object> D2){
        boolean delete = false;

        Set<Variable> vars = new HashSet<>();
        vars.add(v1);
        vars.add(v2);

        Set<Object> domains = new HashSet<>();
        domains.addAll(D1);

        //pour chaque valeur possible pour v1
        for(Object value1 : domains){
            boolean viable = false;
            
            for(Object value2 : D2){
                boolean c = true;

                //pour vérifie toutes les contraintes entre v1 et v2
                for (Constraint constraint : this.constraints) {
                    Set<Variable> scope = constraint.getScope();
                    if (scope.equals(vars)) {
                        Map<Variable, Object> instanciation = new HashMap<>();
                        instanciation.put(v1, value1);
                        instanciation.put(v2, value2);

                        if (!constraint.isSatisfiedBy(instanciation)){
                            c = false;
                            break;
                        }
                    }
                }
                if (c) {
                    viable = true;
                    break;    
                }
            }
            if (!viable){
                D1.remove(value1);
                delete = true;
            }
        }
        return delete;
    }

    public boolean ac1(Map<Variable, Set<Object>> domaines){
        if(!enforceNodeConsistency(domaines)){
            return false;
        }
        boolean change = false;
        do {
            change = false;
            for(Variable v1: domaines.keySet()){
                
                for(Variable v2: domaines.keySet()){
                    if(!v1.equals(v2)){
                        if(this.revise(v1, domaines.get(v1), v2, domaines.get(v2))){
                            change = true;
                        }
                    }
                }
            }
        }
        while(change = true);
        for(Variable v : domaines.keySet()){
            if(domaines.get(v).isEmpty()){
                return false;
            }
        }
        return true;

    }




    
    
}
