package planning;

import java.util.Map;

import modelling.Variable;

/**
 * Représente un but dans un problème de planification.
 * <p>
 * Un but est une condition logique portant sur un état.
 */
public interface Goal{

    /**
     * Indique si le but est satisfait par l'état donné.
     *
     * @param state état à tester
     * @return vrai si le but est satisfait par cet état, faux sinon
     */
    public boolean isSatisfiedBy(Map<Variable, Object> state); 
    

    
}