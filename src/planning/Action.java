package planning;

import java.util.Map;

import modelling.Variable;

/**
 * Représente une action dans un problème de planification.
 * <p>
 * Une action est caractérisée par une précondition, un effet
 * et un coût d'application.
 */
public interface Action {

    /**
     * Indique si l'action est applicable dans un état donné.
     *
     * @param state état courant, représenté par une affectation des variables
     * @return vrai si la précondition de l'action est satisfaite dans cet état,
     *         faux sinon
     */
    public boolean isApplicable(Map<Variable, Object> state); 
    
    /**
     * Renvoie l'état successeur obtenu en appliquant l'action dans l'état donné.
     *
     * @param state état courant
     * @return nouvel état après application de l'action
     */
    public Map<Variable, Object> successor(Map<Variable, Object> state);
    
    /**
     * Renvoie le coût de l'action.
     *
     * @return coût de l'application de cette action
     */
    public int getCost();

    
}
