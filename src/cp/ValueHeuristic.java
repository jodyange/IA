package cp;

import java.util.*;


import modelling.Variable;

/**
 * Interface représentant une heuristique de sélection des valeurs pour une variable
 * 
 * Les heuristiques sonr des criteres utilisées pour guider la recherche
 */
public interface ValueHeuristic {

    /**
     * Détermine la prio des valeurs à explorer pour une variable donnée, en fonction de l'heuristique appliquée.
     * 
     * Cette méthode prend en entrée une variable et son domaine de valeurs possibles, et retourne une liste de ces valeurs ordonnées
     * selon l'heuristique spécifique.
     * 
     * @param var La variable pour laquelle les valeurs doivent être ordonnées.
     * @param domain Le domaine de valeurs possibles pour la variable.
     * @return Une liste des valeurs du domaine de la variable, ordonnées selon l'heuristique
     */
    public List<Object> ordering(Variable var, Set<Object> domain);
}
