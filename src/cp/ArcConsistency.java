package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Cette classe implémente l'algorithme de AC-1, utilisé pour maintenir la consistance locale
 */
public class ArcConsistency {

    private Set<Constraint> constraints;

    /**
     * Constructeur de ArcConsistency 
     * initialise l'ensemble des contraintes et vérifie que toutes les contraintes 
     * sont soit unary soit binary
     * 
     * @param constraints L'ensemble des contraintes
     * @throws IllegalArgumentException Si une contrainte n'est ni unaire ni binaire.
     */
    public ArcConsistency(Set<Constraint> constraints) {
        //Vérifie que toutes les contraintes sont unaires ou binaires
        for (Constraint c : constraints) {
            if (c.getScope().size() != 1 && c.getScope().size() != 2) {
                throw new IllegalArgumentException("ArcConsistency constraints doit être unaire ou binaire");
            }
        }
        this.constraints = constraints;
    }

    /**
     * Applique la consistance de nœud sur les domaines des variables, 
     * supprime les valeurs avec size() de domain != 1 (unaire)
     * Retourne false si un domaine devient vide.
     * 
     * @param domains Les domaines des variables.
     * @return true si tous les domaines sont cohérents, false si un domaine devient vide.
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains){
        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            Variable variable = entry.getKey();
            Set<Object> domain = entry.getValue();
            //copyDomain, toRemove
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

    /**
     * Révise les domaines des variables v1 et v2 pour s'assurer que chaque valeur de v1
     * a au moins une valeur correspondante dans v2, selon les contraintes entre les deux variables.
     * 
     * @param v1 La première variable à réviser
     * @param D1 Le domaine de v1
     * @param v2 La deuxième variable à réviser
     * @param D2 Le domaine de v2
     * @return true si des valeurs ont été supprimées du domaine de v1, sinon false.
     */
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

    /**
     * Applique l'algorithme de consistance d'arc AC-1 sur les domaines des variables. Révise les arcs
     * jusqu'à ce qu'aucun changement ne soit effectué, donc on est sur de la consistance d'arc.
     * 
     * @param domains Les domaines des variables à vérifier.
     * @return true si les domaines sont cohérents, false si un domaine devient vide.
     */
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
        while(change == true);
        for(Variable v : domaines.keySet()){
            if(domaines.get(v).isEmpty()){
                return false;
            }
        }
        return true;

    }
    
}
