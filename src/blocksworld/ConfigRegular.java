package blocksworld;

import modelling.*;

import java.util.*;

/**
 * Représente un monde des blocs où les écarts sont réguliers.
 *
 * (1, 3, 5, 7) — écart constant +2
 * (8, 4, 0) — écart constant -4
 * 
 */
public class ConfigRegular extends BlocksWorldConstraints{
    
/**
     * Constructeur : crée les variables, les contraintes de base,
     * puis ajoute les contraintes de régularité.
     *
     * @param nbBlocks nombre de blocs
     * @param nbPiles nombre de piles
     */
    public ConfigRegular(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles); // variables + contraintes de base
        addRegularConstraints();            // on rajoute la régularité
    }

    /**
     * Ajoute des contraintes pour assurer la régularité :
     *
     * Pour chaque triple de blocs (bottom, middle, top) :
     *   si le triple (bottom, middle, top) NE respecte PAS la régularité
     *   (c'est-à-dire si (middle - bottom) != (top - middle)),
     *   alors on ajoute une contrainte qui interdit :
     *       on_middle = bottom ET on_top = middle
     *
     * En pratique :
     *   Implication( on_middle ∈ {bottom}  ->  on_top ∈ domaine(on_top) \ {middle} )
     */
    private void addRegularConstraints() {
       int nbBlocks = onVariables.size(); // un on_b par bloc b

        for (int bottom = 0; bottom < nbBlocks; bottom++) {
            for (int middle = 0; middle < nbBlocks; middle++) {
                for (int top = 0; top < nbBlocks; top++) {

                    if (bottom == middle || middle == top || bottom == top) {
                        continue;
                    }

                    // Triple NON régulier -> on jette
                    if (middle - bottom != top - middle) {

                        Variable onMiddle = onVariables.get(middle);
                        Variable onTop = onVariables.get(top);

                        // { bottom } : on_middle doit être posé sur "bottom"
                        Set<Object> onMiddleValues = new HashSet<>();
                        onMiddleValues.add(bottom);

                        // TOUTES les valeurs possibles SAUF "middle"
                        Set<Object> onTopAllowedValues = new HashSet<>();

                        for (int p = 1; p <= nbPiles; p++) {
                            onTopAllowedValues.add(-p);
                        }

                        // tous les blocs sauf "middle"
                        for (int b = 0; b < nbBlocks; b++) {
                            if (b != middle) {
                                onTopAllowedValues.add(b);
                            }
                        }

                        constraints.add(new Implication(onMiddle, onMiddleValues, onTop, onTopAllowedValues)
                        );
                    }
                }
            }
        }
    }

    /**
     * Retourne l’ensemble des contraintes du monde régulier 
     *
     * @return un ensemble contenant une copie de toutes les contraintes
     */
    public Set<Constraint> getRegularConstraints() {
        return new HashSet<>(constraints);
    }
}
