package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;

/**
 * Heuristique pour le monde des blocs :
 * compte le nombre de blocs situés dans la mauvaise pile 
 * par rapport à l’état d'arrivéé.
 *
 * Contrairement à HeuristicBadlyPlaced, cette heuristique ignore
 * la position exacte dans la pile : on regarde seulement le choix de la pile.
 *
 */
public class HeuristicWrongPile implements Heuristic {

    private Map<Variable, Object> goal;

    /**
     * Construit l’heuristique basée sur la comparaison des piles.
     *
     * @param goal l’état but
     */
    public HeuristicWrongPile(Map<Variable, Object> goal) {
        this.goal = goal;
    }


    /**
     * Estime le nombre de blocs se trouvant dans la mauvaise pile.
     *
     * @param etat état courant
     * @return un entier représentant le nombre de blocs mal placés
     *         au niveau du choix de la pile
     */
    @Override
    public float estimate(Map<Variable, Object> etat) {

        float count = 0;

        for (Map.Entry<Variable, Object> entry : etat.entrySet()) {
            Variable var = entry.getKey();
            Object valState = entry.getValue();

            // On ne considère que les variables qui existent dans le but
            if (!goal.containsKey(var)) {
                continue;
            }

            Object valGoal = goal.get(var);

            // On ne considère que les positions "en pile" dans le but : valeurs négatives
            if (valGoal instanceof Integer && ((Integer) valGoal) < 0) {

                // Si l'état courant n'est pas dans la bonne pile
                if (!(valState instanceof Integer) || !valGoal.equals(valState)) {
                    count += 1;
                }
            }
        }

        return count;
    }
}
