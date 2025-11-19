package cp;

import java.util.List;
import java.util.Set;

import modelling.Variable;

/**
 * Interface pour les heuristiques d'ordre d'essai des valeurs
 * dans le domaine d'une variable.
 */
public interface ValueHeuristic {

    /**
     * Donne un ordre d'essai des valeurs du domaine d'une variable.
     *
     * @param variable la variable à instancier
     * @param domaine le domaine courant de la variable
     * @return une liste contenant toutes les valeurs du domaine, dans
     *         l'ordre proposé par l'heuristique
     */
    public List<Object> ordering(Variable variable, Set<Object> domaine);

    
}
