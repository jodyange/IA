package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Classe abstraite qui implémente l'interface Solver
 * Elle définit des méthodes de base pour gérer les variables et les contraintes, ainsi que pour vérifier la cohérance des assignations.
 */
public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;
    protected Set<Constraint> constraints;
    
    /**
     * Constructeur de la classe 
     * @param variables un ensemble de variables
     * @param constraints  un ensemble de contraintes.
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints ){
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * Récupère les variables.
     * 
     * @return L'ensemble de variables
     */
    public Set<Variable> getVariables() {
        return variables;
    }

    /**
     * Définit les variables .
     * 
     * @param variables L'ensemble des variables à assigner.
     */
    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    /**
     * Récupère les contraintes.
     * 
     * @return L'ensemble de contraintes
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Définit les contraintes.
     * 
     * @param constraints L'ensemble des contraintes à assigner.
     */
    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    /**
     * qui prend en argument une affectation partielle des variables, et retourne un booléen indiquant
     * si cette affectation satisfait toutes les contraintes qui portent sur des variables instanciées dans l’affectation (cohérence locale)
     * 
     * @param instanciation La Map des variables et de leurs valeurs assignées.
     * 
     * @return true si l'instanciation est consistante avec toutes les contraintes, 
     *         false sinon.
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
