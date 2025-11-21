package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;

/**
 * Heuristique pour le monde des blocs :
 * compte le nombre de blocs mal placés par rapport à l’état final.
 *
 * Un bloc est considéré comme « mal placé » si :
 * sa valeur on_b dans l’état courant est différente de celle dans l’état but
 * OU s’il devrait être posé sur un bloc spécifique mais ne l’est pas
 */
public class HeuristicBadlyPlaced implements Heuristic{
    private Map<Variable,Object> goal;

    public HeuristicBadlyPlaced(Map<Variable,Object> goal){
        this.goal = goal;
    }

/**
     * Estime la distance entre l’état acturl et l’état but.
     *
     * La valeur retournée est le nombre de blocs dont la position
     * ne correspond pas à celle de l’état final.
     *
     * @param etat état courant
     * @return un float qui est le nombre de blocs mal placés
     */
    @Override
    public float estimate(Map<Variable, Object> etat) {
        float count = 0;

        for (Variable v : etat.keySet()) {
            if(goal.containsKey(v) && (etat.get(v) instanceof Integer)){
                if (!goal.get(v).equals(etat.get(v))) {
                    count++;
                }
            }
        }

        return count;
    }

    
}