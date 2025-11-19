package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Classe abstraite factorisant les éléments communs des solveurs de CSP.
 * <p>
 * Elle stocke l'ensemble des variables et des contraintes du problème, et
 * fournit une méthode utilitaire pour tester la cohérence locale d'une
 * instanciation partielle.
 */
public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;
    protected Set<Constraint> constraints;
    
    /**
     * Crée un nouveau solveur abstrait pour un problème donné.
     *
     * @param variables l'ensemble des variables du problème
     * @param constraints l'ensemble des contraintes du problème
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints ){
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * Renvoie l'ensemble des variables du problème.
     *
     * @return l'ensemble des variables
     */
    public Set<Variable> getVariables() {
        return variables;
    }

    /**
     * Modifie l'ensemble des variables du problème.
     *
     * @param variables le nouvel ensemble de variables
     */
    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    /**
     * Renvoie l'ensemble des contraintes du problème.
     *
     * @return l'ensemble des contraintes
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Modifie l'ensemble des contraintes du problème.
     *
     * @param constraints le nouvel ensemble de contraintes
     */
    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    /**
     * Teste la cohérence locale d'une instanciation partielle.
     * <p>
     * L'affectation est dite cohérente si toutes les contraintes dont
     * le scope est entièrement instancié sont satisfaites.
     *
     * @param instanciation une instanciation (potentiellement partielle) des variables
     * @return true si l'instanciation est localement cohérente,
     *         false sinon
     */
    public boolean isConsistent(Map<Variable, Object> instanciation){
        for (Constraint c : this.constraints) {
            if(instanciation.keySet().containsAll(c.getScope())){
                if(!c.isSatisfiedBy(instanciation)){
                    return false;
                }
            }  
        }
        return true;
    }


    
}
