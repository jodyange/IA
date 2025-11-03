package cp;

import modelling.Variable;
import modelling.Constraint;
import java.util.*;

/**
 * Implémentation de l'heuristique de sélection des variables sur la taille de son domaine.
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic{
    
    private boolean preferredDomainSize;

    /**
     * Constructeur de l'heuristique DomainSizeVariableHeuristic.
     * 
     * @param preferredDomainSize Si vrai, l'heuristique privilégie la variable ayant le plus grand domaine. 
     *                            Si faux, l'heuristique privilégie la variable ayant le plus petit domaine.
     */
    public DomainSizeVariableHeuristic(boolean preferredDomainSize) {
        this.preferredDomainSize = preferredDomainSize;
    }

    /**
     * Sélectionne la meilleure variable parmi l'ensemble des variables, en fonction de la taille de son domaine
     * 
     * @param variables L'ensemble des variables disponibles
     * @param domaines Une Map associant chaque variable à son domaine 
     * @return La variable sélectionnée en fonction de l'heuristique du nombre de contraintes.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {
       

         Map<Variable,Integer> size = new HashMap<>();

        for (Variable v : variables) {

            if(domaines.keySet().contains(v)){
                int tmp = domaines.get(v).size();

                size.put(v, tmp);

                
            }
            
        }

        Variable max = getMaxVariable(size);
        Variable min = getMinVariable(size); 

        if(preferredDomainSize){
            return max;}

        return min;
        }

    /**
     * Trouve la variable associée au max dde la taille de domaine
     * 
     * @param map Une Map associant des variables à la taille de leur domaine.
     * @return La variable ayant le plus grand domaine.
     */
    public static Variable getMaxVariable(Map<Variable, Integer> map) {
        return map.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null); 
        }

    /**
     * Trouve la variable associée au minimum de la taille de domaine.
     * 
     * @param map Une Map associant des variables à la taille de leur domaine.
     * @return La variable ayant le plus petit domaine.
     */
    public static Variable getMinVariable(Map<Variable, Integer> map) {
        return map.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null); 
    }
}
