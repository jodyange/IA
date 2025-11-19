package cp;

import java.util.Map;
import modelling.Variable;

/**
 * Représente un solveur de problèmes de satisfaction de contraintes (CSP).
 * <p>
 * Une instance de est associée à un problème (variables + contraintes)
 * et fournit une méthode permettant de chercher une solution.
 */
public interface Solver {

    /**
     * Cherche une solution au problème de satisfaction de contraintes associé.
     *
     * @return une affectation totale des variables satisfaisant toutes les contraintes,
     *         ou null si le problème n'a pas de solution.
     */
    public Map<Variable, Object> solve();
}
