package cp;

import java.util.Map;

import modelling.Variable;

/**
 * L'nterface définit le contrat pour la résolution de problèmes 
 * Les classes qui l'implementent devront implementer la méthose solve() qu'elle declare
 */
public interface Solver {

    /**
     * trouve une solution au problemes de contraintes.
     * 
     * @return Une Map de Variables à Objet, représentant la solution 
     *         Retourne null si aucune solution n'est trouvée.
     */
    public Map<Variable, Object> solve();
    
}
