package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Implémentation de l'heuristique de sélection des variables apparaissant dans le plus de contraintes ou dans le moins de contraintes
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic{

    private Set<Constraint> constraints;
    private boolean preferredVariableNb;

    /**
     * Constructeur de l'heuristique NbConstraintsVariableHeuristic.
     * 
     * @param constraints L'ensemble des contraintes
     * @param preferredVariableNb Si vrai, l'heuristique c'est la variable ayant le max de contraintes. 
     *                            Si faux, l'heuristique c'est la variable ayant le min de contraintes.
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean preferredVariableNb) {
        this.constraints = constraints;
        this.preferredVariableNb = preferredVariableNb;
    }

    /**
     * Sélectionne la meilleure variable parmi l'ensemble des variables, en fonction du nombre de contraintes
     * 
     * @param variables L'ensemble des variables disponibles
     * @param domains Une Map associant chaque variable à son domaine 
     * @return La variable sélectionnée en fonction de l'heuristique du nombre de contraintes.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Map<Variable,Integer> size = new HashMap<>();

        for (Constraint constraint : constraints) {
            for (Variable var : variables) {

                size.putIfAbsent(var, 0);

                if(constraint.getScope().contains(var)){
                    int res = size.get(var);
                    size.put(var, res+1);
                }
            }
        }

        Variable max = getMaxVariable(size);
        Variable min = getMinVariable(size); 

        if(preferredVariableNb){return max;}

        return min;
    }


    /**
     * Trouve la variable associée au max de contraintes.
     * 
     * @param map Une Map associant des variables à leur nombre de contraintes.
     * @return La variable ayant le plus grand nombre de contraintes.
     */
    public static Variable getMaxVariable(Map<Variable, Integer> map) {
        return map.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null); 
    }

    /**
     * Trouve la variable associée au min de contraintes.
     * 
     * @param map Une Map associant des variables à leur nombre de contraintes.
     * @return La variable ayant le moins grand nombre de contraintes.
     */
    public static Variable getMinVariable(Map<Variable, Integer> map) {
        return map.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null); 
    }
    
    
}
