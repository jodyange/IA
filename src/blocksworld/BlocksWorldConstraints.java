package blocksworld;

import modelling.*;

import java.util.*;

/**
 * Monde des blocs avec contraintes de base.
 * Hérite des variables définies dans BlocksWorldVariables.
 */
public class BlocksWorldConstraints extends BlocksWorldVariables {

    /** Toutes les contraintes du monde des blocs. */
    protected Set<Constraint> constraints;

    /**
     * Construit un monde des blocs avec variables + contraintes.
     * 
     * @param nbBlocks nombre de blocs
     * @param nbPiles nombre de piles
     */
    public BlocksWorldConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.constraints = new HashSet<>();

        buildConstraints();
    }

    /**
     * Construit toutes les contraintes du monde des blocs.
     * (différences + implications fixed + implications free)
     */
    private void buildConstraints() {
        addOnDifferenceConstraints();
        addFixedConstraints();
        addFreeConstraints();
    }

    /**
     * Ajoute les contraintes de type :
     * on_i != on_j pour chaque paire de blocs.
     * 'j' ici c'est en fiat => i + 1
     */
    private void addOnDifferenceConstraints() {
        for (int i = 0; i < onVariables.size(); i++) {
            for (int j = i + 1; j < onVariables.size(); j++) {
                constraints.add(
                    new DifferenceConstraint(onVariables.get(i), onVariables.get(j))
                );
            }
        }
    }

    /**
     * Ajoute les contraintes :
     * si on_b = b' alors fixed_b' = true.
     * contrainte Implication(on_b ∈ {b'} → fixed_b' ∈ {true})
     */
    private void addFixedConstraints() {
        int n = onVariables.size();

        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);

            for (int bPrime = 0; bPrime < n; bPrime++) {
                if (b == bPrime) continue;

                Set<Object> onValues = new HashSet<>();
                onValues.add(bPrime);
                Set<Object> fixedValues = new HashSet<>();
                fixedValues.add(true);
                constraints.add(new Implication(onB, onValues, fixedVariables.get(bPrime), fixedValues)
                );
            }
        }
    }

    /**
     * Ajoute les contraintes :
     * si on_b = -(p+1) alors free_p = false.
     */
    private void addFreeConstraints() {

        for (int b = 0; b < onVariables.size(); b++) {
            Variable onB = onVariables.get(b);

            for (int p = 0; p < freeVariables.size(); p++) {

                Set<Object> onValues = new HashSet<>();
                onValues.add(-(p + 1)); // code de la pile p

                Set<Object> freeValues = new HashSet<>();
                freeValues.add(false);

                constraints.add(new Implication(onB, onValues, freeVariables.get(p), freeValues)
                );
            }
        }
    }

    /**
     * Retourne toutes les contraintes générées.
     * 
     * @return ensemble des contraintes
     */
    public Set<Constraint> getAllConstraints() {
        return constraints;
    }
}
