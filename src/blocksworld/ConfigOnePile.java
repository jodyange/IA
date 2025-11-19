package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.UnaryConstraint;
import modelling.Variable;

public class ConfigOnePile extends BlocksWorldConstraints {

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
            for (int b = 0; b < n; b++) {
            allowed.add(b);
        }

            // ajouter contrainte unaire : onVar ∈ allowed
            constraints.add(new UnaryConstraint(onVar, allowed));
        }
    }

    @Override
    public String toString() {
        return "BlocksWorld une seule pile " + onVariables.size() + " blocks";
    }

    public static void main(String[] args) {
        BlocksWorldVariables world = new ConfigOnePile(4, 3);
        System.out.println(world);
    }
}
