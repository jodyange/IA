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
        int n = onVariables.size(); 
        
        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);
            Set<Object> allowed = new HashSet<>();

            for (int p = 1; p <= nbPiles; p++) {
                allowed.add(-p);
            }
            for (int k = b +1; k < n; k++) {
                allowed.add(k);
            }
            constraints.add(new UnaryConstraint(onB, allowed));
        }
    }

    /**
     * Retourne l’ensemble de toutes les contraintes du monde des blocs
     * incluant celles de base et celles imposant la croissance.
     *
     * @return une copie de l’ensemble des contraintes
     */
    public Set<Constraint> getDecreasingConstraints() {
        return new HashSet<>(constraints);
    }

}
