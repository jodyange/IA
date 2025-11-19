package planning;

import java.util.Map;

import modelling.Variable;

/**
 * Représente une heuristique utilisée pour estimer le coût
 * restant d'un état jusqu'à un but.
 */
public interface Heuristic {

    /**
     * Calcule une estimation du coût d'un plan optimal à partir de l'état donné.
     *
     * @param value état à évaluer
     * @return estimation du coût restant jusqu'au but
     */
    public float estimate(Map<Variable,Object> value);

    
}
