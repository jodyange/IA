package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Heuristique de choix de variable basée sur la taille des domaines.
 * <p>
 * Permet par exemple d'implémenter l'heuristique "fail-first".
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic {

    private boolean preferDomain;

    /**
     * Crée une heuristique basée sur la taille des domaines.
     *
     * @param preferDomain true pour choisir les plus grands domaines,
     *                     false pour choisir les plus petits domaines
     */
    public DomainSizeVariableHeuristic(boolean preferDomain) {
        this.preferDomain = preferDomain;
    }

    /**
     * Choisit la variable en fonction de la taille de son domaine.
     *
     * @param variables l'ensemble des variables encore non instanciées
     * @param domaines les domaines courants des variables
     * @return la variable choisie selon le critère de taille de domaine
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {

        if (domaines == null || domaines.size() == 0) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;

        for (Variable var : variables) {
            //les variables dans variables ont toutes leur domaine dans domains
            int domainSize = domaines.get(var).size();
            if (domainSize > max) {
                max = domainSize;
                maxVariable = var;
            }
            if (domainSize < min) {
                min = domainSize;
                minVariable = var;
            }

        }
        if (this.preferDomain) {
            return maxVariable;
        }
        return minVariable;
    }
 
    
}
