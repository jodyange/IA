package blocksworld;

import modelling.*;
import java.util.*;


/**
 * Contraintes "croissantes" pour le monde des blocs :
 * chaque bloc ne peut être posé que sur un bloc de numéro plus petit
 * (ou sur une pile).
 */
public class ConfigIncreasing extends BlocksWorldConstraints {


    public ConfigIncreasing(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        addIncreasingConstraints();
    }

    /**
     * Ajoute, pour chaque bloc b, une contrainte unaire :
     * on_b ne peut être que :
     *  - sur une pile (valeur négative),
     *  - ou sur un bloc de numéro strictement plus petit (< b).
     */
    private void addIncreasingConstraints() {
        // un on_b par bloc b
        for (int b = 0; b < onVariables.size(); b++) {
            Variable onB = onVariables.get(b);

            Set<Object> allowed = new HashSet<>();
            for (Object v : onB.getDomain()) {
                int val = (Integer) v;

                // Piles (valeurs négatives) autorisées
                // ou blocs strictement plus petits
                if (val < 0 || val < b) {
                    allowed.add(val);
                }
            }

            // on_b ∈ allowed
            constraints.add(new UnaryConstraint(onB, allowed));
        }
    }

    /**
     * Retourne toutes les contraintes : base + croissance.
     */
    public Set<Constraint> getIncreasingConstraints() {
        return new HashSet<>(constraints);
    }

    @Override
    public String toString() {
         String res = "\n ConfigIncreasing : \n" + "\n";
                       
        for (Constraint constraint : getIncreasingConstraints()) {
            res += constraint + "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        BlocksWorldVariables myWorld = new ConfigIncreasing(2, 2);
        System.out.println(myWorld);
    }
}
