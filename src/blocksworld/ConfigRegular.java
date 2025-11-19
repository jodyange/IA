package blocksworld;

import modelling.*;

import java.util.*;

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

        // On parcourt tous les triples de blocs distincts : bottom, middle, top
        for (int bottom = 0; bottom < nbBlocks; bottom++) {
            for (int middle = 0; middle < nbBlocks; middle++) {

                for (int top = 0; top < nbBlocks; top++) {

                    if (bottom == middle || middle == top || bottom == top) {
                        continue;
                    }

                    // Si les écarts NE sont PAS égaux -> triple NON régulier -> on doit l'interdire
                    if (middle - bottom != top - middle) {

                        // on_middle (variable qui dit sur quoi est posé le bloc "middle")
                        Variable onMiddle = onVariables.get(middle);

                        // on_top (variable qui dit sur quoi est posé le bloc "top")
                        Variable onTop = onVariables.get(top);

                        // S1 = { bottom } : on_middle doit être posé sur "bottom"
                        Set<Object> onMiddleValues = new HashSet<>();
                        onMiddleValues.add(bottom);

                        // S2 = domaine de on_top SANS la valeur "middle"
                        Set<Object> onTopAllowedValues = new HashSet<>(onTop.getDomain());
                        onTopAllowedValues.remove(middle);

                        // On ajoute l'implication :
                        // si on_middle = bottom alors on_top != middle
                        constraints.add(new Implication(onMiddle, onMiddleValues, onTop, onTopAllowedValues)
                        );
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
       
        String res = super.toString();
        res += "\n BlocksWorld regular Constraints: \n" + "\n";
                       
        for (Constraint constraint : constraints) {
            res += constraint + "\n";

        }
        return res;

    }

    public static void main(String[] args) {
        //BlocksWorldRegularConfig myWorld = new BlocksWorldRegularConfig(5, 3);
        BlocksWorldVariables myWorld = new ConfigRegular(3, 2);
        System.out.println(myWorld);
    }
}
