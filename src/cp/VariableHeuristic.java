package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Interface pour les heuristiques de choix de la prochaine variable
 * à instancier dans un solveur de CSP.
 */
public interface VariableHeuristic {

    /**
     * Choisit la variable à instancier en priorité.
     *
     * @param variables l'ensemble des variables encore non instanciées
     * @param domaines un dictionnaire associant à chaque variable son domaine courant
     * @return la variable jugée la plus prometteuse par l'heuristique
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) ;
    
}
