package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.UnaryConstraint;
import modelling.Variable;

/**
 * Représente un monde des blocs où toutes les configurations sont contraintes
 * d'utiliser une seule pile.
 *
 */
public class ConfigOnePile extends BlocksWorldConstraints {

    /**
     * Construit un monde des blocs où une seule pile peut être utilisée.
     *
     * @param nbBlocks nombre total de blocs
     * @param nbPiles nombre total de piles disponibles (ignoré pour la contrainte)
     */
    public ConfigOnePile(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        addOnePileConstraints();
    }

    /**
     * Contrainte : utiliser une seule pile, la pile -1.
     * Cela supprime les valeurs -2, -3, ..., -nbPiles du domaine de chaque on_b.
     */
    private void addOnePileConstraints() {
         int n = onVariables.size();
         for (int i = 0; i < n; i++) {

            Variable onVar = onVariables.get(i); // Récupérer on_i

        // Domaine autorisé
            Set<Object> allowed = new HashSet<>();

        // Une seule pile : -1
            allowed.add(-1);

        // Tous les blocs
            for (int b = 0; b < i; b++) {
            allowed.add(b);
        }
            constraints.add(new UnaryConstraint(onVar, allowed));
        }
    }
}
