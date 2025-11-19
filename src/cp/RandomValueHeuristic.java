package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;

/**
 * Heuristique de valeurs qui renvoie les valeurs d'un domaine
 * dans un ordre aléatoire.
 */
public class RandomValueHeuristic implements ValueHeuristic{

    private Random generator;

    /**
     * Crée une heuristique de valeurs aléatoire.
     *
     * @param generator le générateur aléatoire à utiliser
     */
    public RandomValueHeuristic(Random generator){
        this.generator = generator;
    }

    /**
     * Renvoie une permutation aléatoire des valeurs du domaine.
     *
     * @param variable la variable considérée (non utilisée ici mais conforme à l'interface)
     * @param domaine le domaine courant de la variable
     * @return la liste des valeurs du domaine dans un ordre aléatoire
     */
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> value = new ArrayList<>(domaine);
        Collections.shuffle(value, generator);
        return value;
        
        
    }
    
}
