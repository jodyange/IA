package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.Constraint;
import modelling.UnaryConstraint;
import modelling.Variable;

/**
 * Monde des blocs avec contrainte de décroissance :
 * chaque bloc ne peut être posé que sur un bloc de numéro plus grand
 * ou directement sur une pile (valeur négative).
 *
 * On part des contraintes de base (différences, fixed/free)
 * définies dans BlocksWorldWithConstraints, puis on ajoute
 * une contrainte unaire sur chaque variable on_b.
 */
public class ConfigDecreasing extends BlocksWorldConstraints {

    /**
     * Construit un monde décroissant :
     * variables + contraintes de base + contraintes de décroissance.
     *
     * @param numBlocks nombre de blocs
     * @param numStacks nombre de piles
     */
    public ConfigDecreasing(int numBlocks, int numStacks) {
        super(numBlocks, numStacks);
        addDecreasingConstraints();
    }

    /**
     * Ajoute, pour chaque bloc b, une contrainte unaire :
     * on_b ne peut être que :
     *  - sur une pile (valeur négative),
     *  - ou sur un bloc de numéro strictement plus grand (> b).
     */
    private void addDecreasingConstraints() {
        // un on_b par bloc b
        for (int b = 0; b < onVariables.size(); b++) {
            Variable onB = onVariables.get(b);

            Set<Object> allowed = new HashSet<>();
            for (Object v : onB.getDomain()) {
                int val = (Integer) v;

                // Piles (valeurs négatives) autorisées
                // ou blocs strictement plus grands
                if (val < 0 || val > b) {
                    allowed.add(val);
                }
            }

            // on_b ∈ allowed
            constraints.add(new UnaryConstraint(onB, allowed));
        }
    }

    /**
     * Retourne toutes les contraintes : base + décroissance.
     */
    public Set<Constraint> getDecreasingConstraints() {
        return new HashSet<>(constraints);
    }

    @Override
    public String toString() {
        String res = "\n ConfigDecreasing : \n" + "\n";
                       
        for (Constraint constraint : getDecreasingConstraints()) {
            res += constraint + " \n";
        }
        return res;
    }

    public static void main(String[] args) {
        BlocksWorldVariables myWorld = new ConfigDecreasing(2, 2);
        System.out.println(myWorld);
    }
}
