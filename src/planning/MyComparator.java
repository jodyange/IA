package planning;

import java.util.Comparator;

/**
 * Comparateur d'objets StateDist basé sur leur coût.
 * <p>
 * Permet d'ordonner les états par coût croissant dans une file de priorité.
 */
public class MyComparator implements Comparator<StateDist> {

    /**
     * Compare deux objets StateDist en fonction de leur coût.
     *
     * @param s0 premier état
     * @param s1 second état
     * @return un entier négatif si le coût de s0 est inférieur à celui de s1,
     *         zéro s'ils sont égaux, un entier positif sinon
     */
    @Override
    public int compare(StateDist s0, StateDist s1) {
        if (s0.getCost() < s1.getCost()) {
            return -1;
        } else if (s0.getCost() == s1.getCost()) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
