package blocksworld;

import modelling.*;
import java.util.*;


/**
 * Contraintes "croissantes" pour le monde des blocs :
 * chaque bloc ne peut être posé que sur un bloc de numéro plus petit
 * (ou sur une pile).
 */
public class ConfigIncreasing extends BlocksWorldConstraints {

    /**
     * Construit un monde des blocs croissant.
     * 
     * Les variables et contraintes de base sont d’abord générées par le constructeur parent,
     * puis les contraintes de croissance sont ajoutées automatiquement.
     *
     * @param nbBlocks nombre total de blocs
     * @param nbPiles nombre total de piles
     */
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
        int n = onVariables.size(); // nombre de blocs

        for (int b = 0; b < n; b++) {

            Variable onB = onVariables.get(b);

            Set<Object> allowed = new HashSet<>();

            // Autoriser toutes les piles : -1, -2, ..., -numStacks
            for (int p = 1; p <= nbPiles; p++) {
                allowed.add(-p);
            }

            // Autoriser tous les blocs plus petits que b : 0, 1, ..., b-1
            for (int k = 0; k < b; k++) {
                allowed.add(k);
            }

            // Ajouter la contrainte unaire : on_b ∈ allowed
            constraints.add(new UnaryConstraint(onB, allowed));
        }
    }

    /**
     * Retourne l’ensemble de toutes les contraintes du monde des blocs
     * incluant celles de base et celles imposant la croissance.
     *
     * @return une copie de l’ensemble des contraintes
     */
    public Set<Constraint> getIncreasingConstraints() {
        return new HashSet<>(constraints);
    }
}
