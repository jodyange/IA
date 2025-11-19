package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Heuristique de choix de variable basée sur le nombre de contraintes
 * dans lesquelles chaque variable apparaît.
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    private boolean favoriteVariable;

    /**
     * Crée une heuristique basée sur le nombre de contraintes par variable.
     *
     * @param constraints l'ensemble des contraintes du problème
     * @param favoriteVariable true pour choisir les variables
     *        apparaissant dans le plus de contraintes, false pour
     *        celles apparaissant dans le moins de contraintes
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean favoriteVariable) {
        this.constraints = constraints;
        this.favoriteVariable = favoriteVariable;
    }

    /**
     * Choisit la variable en fonction du nombre de contraintes dans lesquelles elle apparaît.
     *
     * @param variables l'ensemble des variables encore non instanciées
     * @param domaines les domaines courants des variables (potentiellement utilisés
     *        pour filtrer les variables considérées)
     * @return la variable jugée la meilleure selon le critère choisi
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {
        if(variables==null || variables.size()==0){
            return null;
        }
        int max  = Integer.MIN_VALUE;    // pour chercher le plus grand
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;
        for (Variable var : variables) {
             // Compter combien de contraintes concernent cette variable
            int count = 0;
            for (Constraint c : this.constraints) {
                if(c.getScope().contains(var)){
                    count++;
                }
            }
            if(count>max){
                max=count;
                maxVariable = var;  //Variable apparaissant dans le plus de contraintes
            }
            if(count<min){
                min = count;
                minVariable = var;  //Variable apparaissant dans le moins de contraintes
            }
        }

        if(this.favoriteVariable){
            return maxVariable;
        }
        return minVariable;
        
    }
    
    
}
