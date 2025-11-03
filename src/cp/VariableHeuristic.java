package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Interface représentant une heuristique de sélection de variables
 * 
 * Cette interface définit la méthode best permettant de sélectionner la meilleure variable 
 * parmi un ensemble de variables, en fonction d'une heuristique précisé (nb de contraites ou taaille du domaine)
 * Les heuristiques guide la recherche pour se focaliser sur l'essentiel
 */
public interface VariableHeuristic {

    /**
     * Sélectionne la meilleure variable parmi un ensemble de variables en fonction de l'heuristique appliquée.
     * 
     * Cette méthode prend en entrée un ensemble de variables et les domaines qui vont avec et retourne la variable 
     * "meilleure" selon l'heuristique choisie
     * 
     * @param variables L'ensemble des variables dispo pour l'affectation.
     * @param domains Une Map associant chaque variable à son domaine de valeurs possibles.
     * @return La variable heureuse élue
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
}
