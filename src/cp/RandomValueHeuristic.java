package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;

/**
 * Implémentation de l'heuristique dqui réorganise de manière aléatoire les valeurs possibles d'une variable
 */
public class RandomValueHeuristic implements ValueHeuristic{
    
    private Random random;

    /**
     * Constructeur de l'heuristique RandomValueHeuristic.
     * 
     * @param random Le générateur de nombres aléatoires utilisé pour mélanger les valeurs du domaine.
     */
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

     /**
     * Réorganise de manière aléatoire les valeurs du domaine
     * 
     * @param var La variable dont le domaine de valeurs doit être whippin.
     * @param domain Le domaine de valeurs possibles de la variable.
     * @return Une liste contenant les valeurs du domaine, mélangées de manière aléatoire.
     */
    @Override
    public List<Object> ordering(Variable var, Set<Object> domain) {
        List<Object> values = new ArrayList<>(domain);
        Collections.shuffle(values, random);
        return values;
    }

    


}
