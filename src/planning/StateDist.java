package planning;

import java.util.Map;

import modelling.Variable;

/**
 * Couple un état et un coût associé.
 * <p>
 * Cette classe est principalement utilisée avec des files de priorité
 * dans les algorithmes de Dijkstra et A*.
 */
public class StateDist {

    private Map<Variable, Object> state;
    private Float cost;

    /**
     * Crée un objet représentant un état et son coût associé.
     *
     * @param state état considéré
     * @param cost coût associé à cet état
     */
    public StateDist(Map<Variable, Object> state, Float cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Renvoie l'état stocké.
     *
     * @return état
     */
    public Map<Variable, Object> getState() {
        return state;
    }

    /**
     * Renvoie le coût associé à l'état.
     *
     * @return coût
     */
    public Float getCost() {
        return cost;
    }
    
    
}
