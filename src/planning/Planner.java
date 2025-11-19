package planning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Représente un planificateur pour un problème de planification.
 * 
 * Un planificateur est associé à un état initial, un ensemble d'actions
 * et un but, et cherche un plan menant de l'état initial à un état but.
 */
public interface Planner {
    /**
     * Cherche un plan menant de l'état initial à un état satisfaisant le but.
     *
     * @return une liste d'actions formant un plan, ou null si aucun plan
     *         n'existe pour ce problème
     */
    public List<Action> plan();

    /**
     * Renvoie l'état initial du problème.
     *
     * @return état initial
     */
    public Map<Variable, Object> getInitialState();
    
    /**
     * Renvoie l'ensemble des actions disponibles.
     *
     * @return ensemble d'actions
     */
    public Set<Action> getActions();

    /**
     * Renvoie le but du problème.
     *
     * @return but du problème
     */
    public Goal getGoal();

    /**
     * Active ou désactive le comptage du nombre de nœuds explorés.
     *
     * @param activate vrai pour activer le comptage, faux pour le désactiver
     */
    public void activateNodeCount(boolean activate);
    


}